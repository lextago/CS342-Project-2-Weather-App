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
import javafx.stage.Modality;
import javafx.stage.Stage;
import weather.Period;
import weather.WeatherAPI;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HomeScreen extends SceneBuilder{
	private static ArrayList<HourlyPeriod> hourlyForecast;

	private static BorderPane prevHourBox; //static global variable used for lambda method which requires 'final' variables
	static DropShadow dropShadow = new DropShadow();
	static Stage alertsDialog;

	public static Scene getScene(){
		BorderPane root = getRoot();
		BorderPane rootPane = new BorderPane(root);
		rootPane.setBottom(NavigationBar.getNavigationBar());
		rootPane.setPrefSize(360,640);

		Pane pane = new Pane();
		pane.getChildren().add(rootPane);

		Image plantImage = new Image("/images/indoor_plant.png", 100, 100, false, true);
		ImageView plantView = new ImageView(plantImage);

		Image catImage = new Image("/images/cat_lamp.png", 150, 150, false, true);
		ImageView catView = new ImageView(catImage);

		Image pingu = new Image("/images/pingu_orange.png", 80, 80, false, true);
		ImageView pinguView = new ImageView(pingu);

		Image matcha = new Image("/images/matcha.png", 100, 100, false, true);
		ImageView matchaView = new ImageView(matcha);

		pane.getChildren().add(plantView);
		plantView.setLayoutX(-20);
		plantView.setLayoutY(200);

		pane.getChildren().add(catView);
		catView.setLayoutX(260);
		catView.setLayoutY(125);

		pane.getChildren().add(pinguView);
		pinguView.setLayoutX(300);
		pinguView.setLayoutY(520);

		pane.getChildren().add(matchaView);
		matchaView.setLayoutX(260);
		matchaView.setLayoutY(-25);

		Label quoteLabel = new Label();
		quoteLabel.setFont(Font.font("Lucida Calligraphy", 12));
		quoteLabel.setTextFill(Color.WHITE);
		pane.getChildren().add(quoteLabel);
		quoteLabel.setLayoutX(15);
		quoteLabel.setLayoutY(15);


		Scene homeScene = new Scene(pane, 360, 640);
		String stylesheet = "/style.css";
		switch(theme){
			case "Matcha":
				stylesheet = "/css/home/home_matcha.css";
				quoteLabel.setText("\"♡ I love you\nso matcha ♡\"");
				break;
			case "Cocoa":
				stylesheet = "/css/home/home_cocoa.css";
				quoteLabel.setText("\"♡ I love you\na choco-lot ♡\"");
				break;
			case "Milk":
				stylesheet = "/css/home/home_milk.css";
				quoteLabel.setText("\"♡ I love you\na latte ♡\"");
				quoteLabel.setTextFill(Color.BLACK);
				break;
			case "Ube":
				stylesheet = "/css/home/home_ube.css";
				quoteLabel.setText("\"♡ Will 'u-be'\n     mine? ♡\"");
				break;
		}
		homeScene.getStylesheets().add(SceneBuilder.class.getResource(stylesheet).toExternalForm());

		return homeScene;
	}

	public static BorderPane getRoot(){
		Period currentForecast = WeatherAPI.getForecast(region, gridX, gridY).get(0);
		hourlyForecast = MyWeatherAPI.getHourlyForecast(region,gridX,gridY);
		double[] minAndMax = MyWeatherAPI.getMinAndMaxTemperatures(region, gridX, gridY).get(0).getValue();
		if (hourlyForecast == null){
			System.out.println("No forecast found");
		}

		//Home screen is split into two main components: the top half and bottom half.
		//---elements for homeBoxOne (top portion of screen)
		Button locationButton = getLocationButton();

		int temperature = hourlyForecast.get(0).temperature;
		if(temperatureUnit.equals("Celsius")) {
			temperature = convertFahrenheitToCelsius(temperature);
		}

		Label temperatureLabel = new Label(temperature + "°");
		temperatureLabel.setFont(Font.font("Verdana", FontWeight.BOLD,50));
		temperatureLabel.setTextFill(Color.rgb(255,255,255));
		temperatureLabel.setEffect(dropShadow);

		Label weatherLabel = new Label(hourlyForecast.get(0).shortForecast);
		weatherLabel.setFont(Font.font("Verdana", 14));
		weatherLabel.setId("label");

		Label minMaxText = new Label();
		int min = (int) minAndMax[0];
		int max = (int) minAndMax[1];

		if(temperatureUnit.equals("Fahrenheit")){
			min = convertCelsiusToFahrenheit(min);
			max = convertCelsiusToFahrenheit(max);
		}

		minMaxText.setText("L: " + min + "°" + " H: " + max + "°");
		minMaxText.setFont(Font.font("Verdana", 13));
		minMaxText.setId("label");

		HBox homeBoxOneTop = new HBox(locationButton); //Placed at the very top of the screen
		homeBoxOneTop.setAlignment(Pos.CENTER);

		VBox homeBoxOneCenter = new VBox(temperatureLabel, weatherLabel, minMaxText); //Root for the top portion of the screen
		homeBoxOneCenter.setAlignment(Pos.CENTER);

		//--putting all the elements together for homeBoxOne (top portion of the screen)
		BorderPane homeBoxOne = new BorderPane(homeBoxOneCenter);
		homeBoxOne.setPrefHeight(150);
		homeBoxOne.setTop(homeBoxOneTop);

		//middle of homeboxone and two
		TextArea descriptionText = new TextArea(currentForecast.detailedForecast);
		descriptionText.setWrapText(true);
		descriptionText.setFont(Font.font("Verdana", FontWeight.MEDIUM,12));
		descriptionText.setEffect(dropShadow);
		descriptionText.setEditable(false);
		descriptionText.setPrefSize(300,70);

		//---elements for homeBoxTwo (lower portion of screen)
		//The bottom half of the screen is split into a left and right component

		ScrollPane hourlyForecastScroll = getHourlyScroll();

		//---elements in homeBoxTwoLeft (left side of homeBoxTwo)
		Label todayWeatherText = new Label("      Today's Weather");
		todayWeatherText.setFont(Font.font("Verdana",FontWeight.BOLD, 16));
		todayWeatherText.setTextFill(Color.rgb(255,255,255));
		todayWeatherText.setPrefSize(220,18);
		todayWeatherText.setEffect(dropShadow);

		//--putting all the elements together for the left part of the bottom of the screen
		VBox homeBoxTwoLeft = new VBox(todayWeatherText, hourlyForecastScroll);

		//---elements in homeBoxTwoRight (right side of homeBoxTwo)
		TextArea alertsText = getAlertsText();

		Image forecastImage = new Image(currentForecast.icon);
		ImageView forecastView = new ImageView(forecastImage);
		forecastView.setFitHeight(100);
		forecastView.setFitWidth(100);
		forecastView.setEffect(dropShadow);

		VBox homeBoxTwoRight = new VBox(10, forecastView, alertsText);

		//--putting all elements together for homeBoxTwo (bottom portion of screen)
		HBox homeBoxTwo = new HBox(10, homeBoxTwoLeft, homeBoxTwoRight);
		homeBoxTwo.setAlignment(Pos.CENTER);

		//--putting all elements together in BorderPane root (whole screen)
		VBox centerRoot = new VBox(10, homeBoxOne, descriptionText);

		BorderPane root = new BorderPane(centerRoot);
		root.setPadding(new Insets(10, 10, 0, 10));
		root.setBottom(homeBoxTwo); //aligns homeBoxTwo to the bottom of the screen

		root.setBackground(new Background(backgroundImage));

		return root;
	}

	private static TextArea getAlertsText(){
		TextArea alertsText = new TextArea("Alerts: None");
		alertsText.setWrapText(true);
		alertsText.setFont(Font.font("Verdana", FontWeight.MEDIUM,12));
		alertsText.setEffect(dropShadow);
		alertsText.setEditable(false);
		alertsText.setPrefSize(100, 200);
		alertsText.setId("alertsText");

		ArrayList<Alert> currAlerts = MyWeatherAPI.getActiveAlerts(latitude, longitude);
		if(!currAlerts.isEmpty()){
			Alert currAlert = currAlerts.get(0);
			alertsText.setText("Alerts: " + currAlert.headline);

			TextArea headlineText = new TextArea(currAlert.headline);
			headlineText.setPrefSize(400,75);
			headlineText.setEditable(false);
			headlineText.setId("headlineText");

			TextArea descriptionText = new TextArea(currAlert.description);
			descriptionText.setPrefSize(400,250);
			descriptionText.setEditable(false);

			TextArea instructionText = new TextArea(currAlert.instruction);
			instructionText.setPrefSize(400,250);
			instructionText.setEditable(false);

			VBox alertsBox = new VBox(headlineText, descriptionText, instructionText);

			Scene nextScene = new Scene(alertsBox);
			nextScene.getStylesheets().add(SceneBuilder.class.getResource("/css/alerts.css").toExternalForm());

			alertsDialog = new Stage();
			alertsDialog.setScene(nextScene);
			alertsDialog.setTitle("Active Alert");
			alertsDialog.setResizable(false);
			alertsDialog.initOwner(stage);

			alertsText.setOnMouseClicked(e-> {
				if(alertsDialog.isShowing()) {
					alertsDialog.close();
				}
				else{
					alertsDialog.show();
				}
			});
		}

		return alertsText;
	}

	//Creates dialog window to prompt user for location
	private static Button getLocationButton(){
		Button locationButton = new Button(getLocation());
		locationButton.setPrefSize(150,40);
		locationButton.setId("locationButton");

		locationButton.setOnAction(e -> {
			if(alertsDialog != null && alertsDialog.isShowing()){
				alertsDialog.close();
			}

			Stage locationDialog = new Stage();

			LocationDetails locationScreen = new LocationDetails(locationDialog);
			BorderPane root = LocationDetails.getScreen();

			Scene nextScene = new Scene(root, 315, 560);

			locationDialog.setScene(nextScene);
			locationDialog.setTitle("LocationDetails");
			locationDialog.setResizable(false);

			locationDialog.initOwner(stage);
			locationDialog.initModality(Modality.WINDOW_MODAL); //prevents user interaction with main stage until the dialog window has closed
			locationDialog.showAndWait();
		});

		return locationButton;
	}

	private static ScrollPane getHourlyScroll(){
		VBox hourlyForecastBox = new VBox();
		ScrollPane hourlyForecastScroll = new ScrollPane(hourlyForecastBox); //This ScrollPane allows the user to scroll through the VBox
		hourlyForecastScroll.setPrefSize(220, 300);
		hourlyForecastScroll.setId("shelfScroll");

		ArrayList<HBox> moreInfoBoxes = new ArrayList<>();
		ArrayList<BorderPane> hourBoxes = new ArrayList<>();

		for(int i = 0; i <= 24; i++){
			HourlyPeriod currentPeriod = hourlyForecast.get(i);

			Date currentHour = currentPeriod.startTime;
			SimpleDateFormat localDateFormat;
			if(timeFormat.equals("24hr")){
				localDateFormat = new SimpleDateFormat("HH:mm");
			}
			else{
				localDateFormat = new SimpleDateFormat("hh a");
			}
			String hourTime = localDateFormat.format(currentHour);

			Label time = new Label(hourTime);
			time.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 15));
			if(currentPeriod.isDaytime){
				time.setTextFill(Color.web("#FFFFFF"));
			}
			else{
				time.setTextFill(Color.web("#B3B3B3"));
			}
			time.setEffect(dropShadow);

			int temperature = currentPeriod.temperature;
			if(temperatureUnit.equals("Celsius")){
				temperature = convertFahrenheitToCelsius(temperature);
			}

			Label temp = new Label(temperature + "°");
			temp.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
			temp.setTextFill(Color.rgb(255,255,255));
			temp.setEffect(dropShadow);

			Label rainChance = new Label(currentPeriod.probabilityOfPrecipitation.value + "%");
			rainChance.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
			rainChance.setTextFill(Color.web("#7393B3"));
			rainChance.setEffect(dropShadow);

			HBox statusBox = new HBox(30, time, temp,rainChance);
			statusBox.setAlignment(Pos.CENTER);

			Label wind = new Label("Wind: " + currentPeriod.windSpeed + " " + currentPeriod.windDirection);
			wind.setFont(Font.font("Verdana", 9));
			wind.setTextFill(Color.rgb(255,255,255));

			Label humidity = new Label("Humidity: " + currentPeriod.relativeHumidity.value + "%");
			humidity.setFont(Font.font("Verdana", 9));
			humidity.setTextFill(Color.rgb(255,255,255));

			HBox expandedStatusBox = new HBox(20, wind, humidity);
			expandedStatusBox.setAlignment(Pos.CENTER);
			expandedStatusBox.setVisible(false);
			moreInfoBoxes.add(expandedStatusBox);

			BorderPane currHourBox = new BorderPane();
			currHourBox.setCenter(statusBox);
			currHourBox.setBottom(expandedStatusBox);
			currHourBox.setPadding(new Insets(6));
			currHourBox.setPrefSize(204,50);

			if(i == 0){
				currHourBox.setId("shelfTop");
			}
			else if(i == 24){
				currHourBox.setId("shelfBottom");
			}
			else{
				currHourBox.setId("shelfMiddle");
			}

			hourBoxes.add(currHourBox);
			hourlyForecastBox.getChildren().add(currHourBox);

			prevHourBox = currHourBox;
			currHourBox.setOnMouseClicked(e -> {
				int index = hourBoxes.indexOf(currHourBox);
				int prevIndex = hourBoxes.indexOf(prevHourBox);

				if(!moreInfoBoxes.get(index).isVisible()){
					moreInfoBoxes.get(index).setVisible(true);

					if(currHourBox != prevHourBox){
						moreInfoBoxes.get(prevIndex).setVisible(false);
					}

					prevHourBox = currHourBox;
				}
				else{
					moreInfoBoxes.get(index).setVisible(false);
				}
			});
		}

		hourlyForecastScroll.setStyle("-fx-background-color: transparent");

		return hourlyForecastScroll;
	}
}
