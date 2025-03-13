import javafx.collections.FXCollections;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import weather.Period;
import weather.WeatherAPI;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import java.awt.*;
import java.io.FileInputStream;
import java.util.ArrayList;

//This class builds each screen of the weather app inside a function. Each function
//returns the screen as a BorderPane object to be used as the root for a Scene.
public class SceneBuilder {

	public static BorderPane homeScreen(){
		TextField temperature, weather;
		//int temp = WeatherAPI.getTodaysTemperature(77,70);
		ArrayList<Period> forecast = WeatherAPI.getForecast("LOT",77,70);
		if (forecast == null){
			System.out.println("No forecast found");
		}
		temperature = new TextField();
		weather = new TextField();
		temperature.setText("Today's weather is: "+String.valueOf(forecast.get(0).temperature));
		weather.setText(forecast.get(0).shortForecast);

//		MyWeatherAPI api = new MyWeatherAPI();
//		System.out.println(Arrays.toString(api.getCoords()));

		VBox root = new VBox(temperature,weather);

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
		textSizeText.setEditable(false);

		TextField themesText = new TextField("Themes");
		themesText.setEditable(false);

		TextField temperatureText = new TextField("Temperature");
		temperatureText.setEditable(false);

		TextField timeZoneText = new TextField("Time Zone");
		timeZoneText.setEditable(false);

		TextField chooseHourText = new TextField("24hr/12hr");
		chooseHourText.setEditable(false);

		String weekDays [] = {"4px", "8px", "12px", "16px", "20px"};
		ComboBox weekDaysBox = new ComboBox(FXCollections.observableArrayList(weekDays));
		weekDaysBox.setPrefWidth(300);

		String allThemes [] = {"Matcha", "Cocoa", "Milk", "Coffee", "Ube"};
		ComboBox themesBox = new ComboBox(FXCollections.observableArrayList(allThemes));
		themesBox.setPrefWidth(300);

		String temperatureTypes [] = {"Fahrenheit", "Celsius"};
		ComboBox temperatureBox = new ComboBox(FXCollections.observableArrayList(temperatureTypes));
		temperatureBox.setPrefWidth(300);

		String timeZoneTypes [] = {"CST", "EST", "WST"};
		ComboBox timeZoneBox = new ComboBox(FXCollections.observableArrayList(timeZoneTypes));
		timeZoneBox.setPrefWidth(300);

		String hourTypes [] = {"24hr", "12hr", "6hr"};
		ComboBox hourBox = new ComboBox(FXCollections.observableArrayList(hourTypes));
		hourBox.setPrefWidth(300);

		HBox settingBoxOneHBox = new HBox(textSizeText, weekDaysBox);
		HBox settingBoxTwoHBox = new HBox(themesText, themesBox);
		HBox settingBoxThreeHBox = new HBox(temperatureText, temperatureBox);
		HBox settingBoxFourHBox = new HBox(timeZoneText, timeZoneBox);
		HBox settingBoxFiveHBox = new HBox(chooseHourText, hourBox);

		VBox settingBoxOne = new VBox(settingsText);
		VBox settingBoxTwo = new VBox(4, searchText, settingBoxOneHBox, settingBoxTwoHBox, settingBoxThreeHBox);
		VBox settingBoxThree = new VBox(4, settingBoxFourHBox, settingBoxFiveHBox);

		BackgroundFill background_fill = new BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY);
		Background background = new Background(background_fill);

		VBox root = new VBox(20, settingBoxOne, settingBoxTwo, settingBoxThree);
		root.setBackground(background);

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
