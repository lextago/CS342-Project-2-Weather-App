import hourlyWeather.HourlyPeriod;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Pair;
import weather.Period;
import weather.WeatherAPI;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DailyForecast extends SceneBuilder{
	private static ArrayList<HourlyPeriod> hourlyPeriods;
	private static ArrayList<Period> periods;
	private static ArrayList<Pair<String, double[]>> minAndMaxTemps;

	private static int hourlyIndex;
	private static ScrollPane hourlyScrollPane;
	private static VBox rootVBox;
	private static DropShadow dropShadow = new DropShadow();
	private static Stage hourlyDialog;
	private static String stylesheet;

	public static Scene getScene(){
		BorderPane root = getRoot();
		BorderPane rootPane = new BorderPane(root);
		rootPane.setBottom(NavigationBar.getNavigationBar());

		Scene scene = new Scene(rootPane, 360, 640);
		stylesheet = "style.css";
		switch(theme){
			case "Matcha":
				stylesheet = "/css/daily/daily_matcha.css";
				break;
			case "Cocoa":
				stylesheet = "/css/daily/daily_cocoa.css";
				break;
			case "Milk":
				stylesheet = "/css/daily/daily_milk.css";
				break;
			case "Ube":
				stylesheet = "/css/daily/daily_ube.css";
				break;
		}
		scene.getStylesheets().add(SceneBuilder.class.getResource(stylesheet).toExternalForm());

		return scene;
	}

	public static BorderPane getRoot(){
		minAndMaxTemps = MyWeatherAPI.getMinAndMaxTemperatures(region, gridX, gridY);
		periods = WeatherAPI.getForecast(region, gridX, gridY);
		hourlyPeriods = MyWeatherAPI.getHourlyForecast(region, gridX, gridY);

		rootVBox = new VBox();

		ComboBox<String> numDaysChoices = new ComboBox<>(); // Dropdown of all day choices
		numDaysChoices.setPromptText("Select Days");
		numDaysChoices.setId("comboBox");
		numDaysChoices.getItems().addAll("3 Day", "5 Day", "7 Day");

		hourlyScrollPane = new ScrollPane();
		hourlyScrollPane.setPrefHeight(600);

		numDaysChoices.setOnAction(event -> {
			// Gets number of days desired
			String dayChoice = numDaysChoices.getValue();
			int dayChoiceNumber = Integer.parseInt(dayChoice.split("")[0]);

			rootVBox.getChildren().remove(hourlyScrollPane);

			hourlyScrollPane = getHourlyScroll(dayChoiceNumber);

			rootVBox.getChildren().add(hourlyScrollPane);
		});

		Label dailyForecast = new Label("Daily Forecast");
		dailyForecast.setTextFill(Color.rgb(255,255,255));
		dailyForecast.setFont(new Font("Inter", 40));
		dailyForecast.setEffect(dropShadow);

		VBox rootTop = new VBox(5, dailyForecast, numDaysChoices);
		rootTop.setAlignment(Pos.CENTER);

		rootVBox = new VBox(5, rootTop, hourlyScrollPane);
		rootVBox.setBackground(new Background(backgroundImage));

		return new BorderPane(rootVBox);
	}

	//Creating the ScrollPane used to display the forecasts of the number of days the user inputs
	private static ScrollPane getHourlyScroll(int numDays){
		VBox daysVBox = new VBox(5);
		daysVBox.setStyle("-fx-background-color: transparent");

		ArrayList<BorderPane> dayPanes = new ArrayList<>();
		int forecastIndex = 0; //used to find the forecast for the current 12-hour period
		hourlyIndex = 0; //used to find the forecast for the current hour of the week

		//converting date into day of month
		SimpleDateFormat dayFormat = new SimpleDateFormat("d");

		//iterates through the list until the next day is reached
		while(dayFormat.format(hourlyPeriods.get(0).startTime).equals(dayFormat.format(hourlyPeriods.get(hourlyIndex).startTime))){
			hourlyIndex++;
		}

		for (int i = 0; i < numDays; i++) {
			String dateString = minAndMaxTemps.get(i).getKey();
			double[] minAndMax = minAndMaxTemps.get(i).getValue();
			int tonightIndex = forecastIndex + 1;

			//only one 12-hour period during the night, thus today and tonight's periods are the same
			if(periods.get(0).name.equals("Tonight") && i == 0){
				tonightIndex = 0;
			}

			Period todayPeriod = periods.get(forecastIndex);
			Period tonightPeriod = periods.get(tonightIndex);

			//increments forecastIndex
			if(periods.get(0).name.equals("Tonight") && i == 0){
				forecastIndex++; //incrementing by one if the first forecast only had one period
			}
			else{
				forecastIndex+=2; //increments by two because each day holds the forecast for the day and night
			}

			//Converting from ISO string to date object
			String dateTimeString = dateString.split("/")[0]; // Makes the date ISO format
			DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
			OffsetDateTime dateTime = OffsetDateTime.parse(dateTimeString, formatter);

			int month = dateTime.getMonthValue();
			int monthDay = dateTime.getDayOfMonth();
			DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
			String dayString = dayOfWeek.toString().substring(0,3); //getting day abbreviation

			Label dateLabel = new Label(dayString + " " + month + "/" + monthDay);
			dateLabel.setId("dailyLabel");
			int min = (int) minAndMax[0];
			int max = (int) minAndMax[1];
			if(temperatureUnit.equals("Fahrenheit")){
				min = convertCelsiusToFahrenheit(min);
				max = convertCelsiusToFahrenheit(max);
			}
			Label minMaxLabel = new Label("L: " + min +"°" + " H: " + max + "°");
			minMaxLabel.setId("dailyLabel");

			//creating the top box to display the day, min, and max temps
			BorderPane dayPaneTop = new BorderPane();
			dayPaneTop.setLeft(dateLabel);
			dayPaneTop.setRight(minMaxLabel);
			dayPaneTop.setPadding(new Insets(10));
			dayPaneTop.setPrefWidth(345);
			dayPaneTop.setPrefHeight(40);
			dayPaneTop.setId("dailyBox");
			dayPanes.add(dayPaneTop);

			//displays the hourly forecast for the day the user clicks
			dayPaneTop.setOnMouseClicked(e -> {
				if(hourlyDialog != null && hourlyDialog.isShowing()){
					hourlyDialog.close();
				}

				int paneIndex = dayPanes.indexOf(dayPaneTop);

				hourlyDialog = new Stage();
				hourlyDialog.setTitle("Hourly Forecast for " + dayString + " " + month + "/" + monthDay);
				BorderPane hourlyRoot = getHourlyForecastRoot(hourlyIndex, paneIndex);
				hourlyRoot.setBackground(new Background(backgroundImage));

				Scene nextScene = new Scene(hourlyRoot, 360, 640);
				nextScene.getStylesheets().add(SceneBuilder.class.getResource(stylesheet).toExternalForm());
				hourlyDialog.setScene(nextScene);

				hourlyDialog.show();
			});

			//displaying the data for the current period
			Label descriptionLabel = new Label(todayPeriod.shortForecast);
			descriptionLabel.setId("dailyBottomLabel");
			descriptionLabel.setWrapText(true);

			BorderPane descriptionPane = new BorderPane();
			descriptionPane.setLeft(descriptionLabel);
			descriptionPane.setId("dailyBottomBox");
			descriptionPane.setPadding(new Insets(5));

			BorderPane todayPane = get12HourForecastPane(todayPeriod);

			VBox dayVBox;
			if(todayPeriod.name.equals("Tonight")) { //if there is only one period, no need to make a pane for the night
				dayVBox = new VBox(dayPaneTop, descriptionPane, todayPane);
			}
			else {
				//displaying the data for the night period
				BorderPane tonightPane = get12HourForecastPane(tonightPeriod);

				dayVBox = new VBox(dayPaneTop, descriptionPane, todayPane, tonightPane);
			}

			daysVBox.getChildren().add(dayVBox);
		}

		return new ScrollPane(daysVBox);
	}

	//displaying the period, temperature, and probability of precipitation
	private static BorderPane get12HourForecastPane(Period period){
		BorderPane periodPane = new BorderPane();
		int temperature = period.temperature;
		if(temperatureUnit.equals("Celsius")){
			temperature = convertFahrenheitToCelsius(temperature);
		}
		Label periodText = new Label(period.name + ": " + temperature + "°");
		periodText.setId("dailyBottomLabel");

		Label periodRain = new Label(period.probabilityOfPrecipitation.value + "%");
		periodRain.setFont(Font.font("Verdana", 14));
		periodRain.setTextFill(Color.web("#36454f"));

		periodPane.setLeft(periodText);
		periodPane.setRight(periodRain);
		periodPane.setId("dailyBottomBox");
		periodPane.setPrefHeight(20);
		periodPane.setPadding(new Insets(0,5,0,5));

		return periodPane;
	}


	//used for the hourlyDialog stage to create the root of the scene
	private static BorderPane getHourlyForecastRoot(int hourlyIndex, int paneIndex){
		SimpleDateFormat dayFormat = new SimpleDateFormat("E M/d");
		SimpleDateFormat hourFormat = new SimpleDateFormat("hh a");
		if(timeFormat.equals("24hr")){
			hourFormat = new SimpleDateFormat("HH:mm");
		}

		VBox hourlyVBox = new VBox(5);

		int startIndex, endIndex;
		if(paneIndex == 0){ //if the pane clicked is the first
			startIndex = 0; //start at zero
			endIndex = hourlyIndex; //end at the index of the next day
		}
		else{
			startIndex = hourlyIndex + (paneIndex - 1) * 24; //start at the index of the next day + 24 hours for each day after the first
			endIndex = Math.min(startIndex + 24, 155); //the api only gives us the forecast for the next 155 hours, ensures that the end is not out of bounds
		}

		Period currPeriod = periods.get(paneIndex * 2);

		Label day = new Label("Hourly Forecast for " + dayFormat.format(currPeriod.startTime));
		day.setFont(Font.font("Verdana", FontWeight.BOLD,15));
		day.setTextFill(Color.rgb(255,255,255));
		day.setEffect(dropShadow);

		TextArea description = new TextArea(currPeriod.detailedForecast);
		description.setEditable(false);
		description.setWrapText(true);
		description.setMaxWidth(220);
		description.setMaxHeight(150);
		description.setEffect(dropShadow);

		Image forecastImage = new Image(currPeriod.icon);
		ImageView forecastView = new ImageView(forecastImage);
		forecastView.setFitHeight(110);
		forecastView.setFitWidth(110);
		forecastView.setEffect(dropShadow);

		for(int i = startIndex; i < endIndex; i++){
			HourlyPeriod currHour = hourlyPeriods.get(i);

			Label hour = new Label( hourFormat.format(currHour.startTime));
			hour.setTextFill(Color.WHITE);
			hour.setFont(Font.font("Verdana", 14));

			if(!currHour.isDaytime){
				hour.setTextFill(Color.web("#5a5a5a"));
			}

			int tempValue = currHour.temperature;
			if(temperatureUnit.equals("Celsius")){
				tempValue = convertFahrenheitToCelsius(tempValue);
			}
			Label temperature = new Label(tempValue + "°");
			temperature.setId("dailyLabel");

			Label rainChance = new Label(currHour.probabilityOfPrecipitation.value + "%");
			rainChance.setFont(Font.font("Verdana", 14));
			if(currHour.probabilityOfPrecipitation.value == 0){
				rainChance.setTextFill(Color.web("#36454f"));
			}
			else{
				rainChance.setTextFill(Color.web("#89CFF0"));
			}

			Label wind = new Label(currHour.windSpeed + " " + currHour.windDirection);
			wind.setId("dailyBottomLabel");
			Label humidity = new Label("Humidity: " + currHour.relativeHumidity.value + "%");
			humidity.setId("dailyBottomLabel");

			BorderPane hourPaneTop = new BorderPane();
			hourPaneTop.setPrefSize(300, 25);
			hourPaneTop.setLeft(hour);
			hourPaneTop.setCenter(temperature);
			hourPaneTop.setRight(rainChance);
			hourPaneTop.setPadding(new Insets(5));

			BorderPane hourPaneBottom = new BorderPane();
			hourPaneBottom.setPrefSize(300, 25);
			hourPaneBottom.setLeft(wind);
			hourPaneBottom.setRight(humidity);
			hourPaneBottom.setPadding(new Insets(5));

			VBox hourPane = new VBox(hourPaneTop, hourPaneBottom);
			hourPane.setAlignment(Pos.CENTER);

			if(i % 2 == 0){ //alternating styles for the boxes
				hourPane.setId("hourlyBoxOne");
			}
			else{
				hourPane.setId("hourlyBoxTwo");
			}

			hourPane.setPadding(new Insets(5));
			hourlyVBox.getChildren().add(hourPane);
		}

		ScrollPane scrollPane = new ScrollPane(hourlyVBox);
		scrollPane.setMaxHeight(500);

		Button back = new Button("Back");
		back.setOnAction(e -> {
			hourlyDialog.close();
		});
		back.setId("back");

		HBox dayBox = new HBox(day);
		dayBox.setAlignment(Pos.CENTER);

		BorderPane rootTop = new BorderPane();
		rootTop.setLeft(back);
		rootTop.setCenter(dayBox);

		BorderPane rootCenter = new BorderPane();
		rootCenter.setLeft(forecastView);
		rootCenter.setRight(description);

		VBox rootVBox = new VBox(10, rootTop, rootCenter, scrollPane);

		BorderPane pane = new BorderPane();
		pane.setCenter(rootVBox);
		pane.setPadding(new Insets(10));
		return pane;
	}
}
