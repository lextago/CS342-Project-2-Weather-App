import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import weather.Period;
import weather.WeatherAPI;

import java.util.ArrayList;

//This class builds each screen of the weather app inside a function. Each function
//returns the screen as a BorderPane object to be used as the root for a Scene.
public class SceneBuilder {
	static Button textButtonExpanded; //change name

	public static BorderPane homeScreen(){
		//int temp = WeatherAPI.getTodaysTemperature(77,70);
		ArrayList<Period> forecast = WeatherAPI.getForecast("LOT",77,70);
		if (forecast == null){
			System.out.println("No forecast found");
		}

		//---elements for homeBoxOne (top portion of screen)

		Label temperatureLabel = new Label();
		Label weatherLabel = new Label();
		temperatureLabel.setText("Today's weather is: "+String.valueOf(forecast.get(0).temperature));
		weatherLabel.setText(forecast.get(0).shortForecast);

//		MyWeatherAPI api = new MyWeatherAPI();
//		System.out.println(Arrays.toString(api.getCoords()));

		VBox homeBoxOneCenter = new VBox(temperatureLabel, weatherLabel);

		BorderPane homeBoxOne = new BorderPane(homeBoxOneCenter);
		homeBoxOne.setPrefHeight(300);
		homeBoxOneCenter.setAlignment(Pos.CENTER);

//		homeBoxOne.setBorder(new Border(new BorderStroke(Color.BLACK,
//				BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

		//---elements for homeBoxTwo (lower portion of screen)

		VBox homeBoxTwoLeft, homeBoxTwoRight;
		ScrollPane hourlyForecastScroll = new ScrollPane();
		VBox hourlyForecastBox = new VBox();

		for(int i = 1; i <= 24; i++){
			String textBody = "Text " + i;
			Button textButton = new Button(textBody);
			textButton.setPrefSize(200, 50);
			textButton.setAlignment(Pos.CENTER_LEFT);
			hourlyForecastBox.getChildren().add(textButton);

			textButtonExpanded = textButton;
			textButton.setOnAction(e-> {
				if(textButton.getHeight() == 50){
					textButton.setPrefHeight(100);
					if(textButton != textButtonExpanded){
						textButtonExpanded.setPrefHeight(50);
					}
					textButtonExpanded = (Button) e.getSource();
				}else{
					textButton.setPrefHeight(50);
				}
			});

		}
		hourlyForecastScroll.setContent(hourlyForecastBox);
		hourlyForecastScroll.setPrefSize(220, 300);

		TextField alertsText = new TextField("Alerts");
		alertsText.setPrefSize(100, 200);
		alertsText.setEditable(false);
		alertsText.setAlignment(Pos.TOP_LEFT);

		TextField image = new TextField("image");
		image.setPrefSize(100, 100);

		TextField descriptionText = new TextField("Description");
		descriptionText.setPrefHeight(80);

		homeBoxTwoLeft = new VBox(descriptionText, hourlyForecastScroll);
		homeBoxTwoRight = new VBox(10, alertsText, image);
		HBox homeBoxTwo = new HBox(10, homeBoxTwoLeft, homeBoxTwoRight);
		homeBoxTwo.setAlignment(Pos.CENTER);

		//---elements in BorderPane root (whole screen)

		VBox centerRoot = new VBox(homeBoxOne);

		BorderPane root = new BorderPane(centerRoot);
		root.setPadding(new Insets(10));
		root.setBottom(homeBoxTwo);

		return new BorderPane(root);
	}

	public static BorderPane dailyForecastScreen(){
		VBox root = new VBox();
		VBox days = new VBox();
		TextField dailyForecast = new TextField("Daily Forecast");

		TextField sevenDays = new TextField("7 Days");
		sevenDays.setPrefSize(25,25);

        TextField dayOne = new TextField("Day One");
		dayOne.setPrefSize(60,60);

		TextField dayTwo = new TextField("Day Two");
		dayTwo.setPrefSize(60,60);

		TextField dayThree = new TextField("Day Three");
		dayThree.setPrefSize(60,60);

		TextField dayFour = new TextField("Day Four");
		dayFour.setPrefSize(60,60);

		TextField dayFive = new TextField("Day Five");
		dayFive.setPrefSize(60,60);

		TextField daySix = new TextField("Day Six");
		daySix.setPrefSize(60,60);

		TextField daySeven = new TextField("Day Seven");
		daySeven.setPrefSize(60,60);

		days = new VBox(sevenDays, dayOne, dayTwo, dayThree, dayFour, dayFive, daySix, daySeven);
		root = new VBox(dailyForecast, days);
		root.setSpacing(60);

		return new BorderPane(root);
	}

	public static BorderPane weeklyTrendsScreen(){
		TextField weeklyTrends = new TextField("Weekly Trends");
		VBox root = new VBox(weeklyTrends);

		return new BorderPane(root);
	}

	public static BorderPane settingsScreen(){
		TextField settingsText = new TextField("Settings");
		settingsText.setEditable(false);
		TextField searchText = new TextField("Search");
		TextField textSizeText = new TextField("Text Size");
		TextField themesText = new TextField("Themes");
		TextField temperatureText = new TextField("Temperature");
		TextField timeZoneText = new TextField("Time Zone");
		TextField chooseHourText = new TextField("24hr/12hr");

		VBox settingBoxOne = new VBox(settingsText);
		VBox settingBoxTwo = new VBox(4, searchText, textSizeText, themesText, temperatureText);
		VBox settingBoxThree = new VBox(4, timeZoneText, chooseHourText);

		VBox root = new VBox(20, settingBoxOne, settingBoxTwo, settingBoxThree);

		return new BorderPane(root);
	}

	public static BorderPane locationDetailsScreen(){
		TextField location = new TextField("Location");

		VBox root = new VBox(location);

		return new BorderPane(root);
	}

	public static BorderPane hourlyForecastScreen(){
		TextField hourlyForecast = new TextField("Hourly Forecast");

		VBox root = new VBox(hourlyForecast);

		return new BorderPane(root);
	}
}
