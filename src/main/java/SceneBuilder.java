import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import weather.Period;
import weather.WeatherAPI;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

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
