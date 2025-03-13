import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import weather.Period;
import weather.WeatherAPI;

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
		TextField dailyForecast = new TextField("Daily Forecast");

		VBox root = new VBox(dailyForecast);

		return new BorderPane(root);
	}

	public static BorderPane weeklyTrendsScreen(){
		TextField weeklyTrends = new TextField("Weekly Trends");

		VBox root = new VBox(weeklyTrends);

		return new BorderPane(root);
	}

	public static BorderPane settingsScreen(){
		TextField settings = new TextField("Settings");

		VBox root = new VBox(settings);

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
