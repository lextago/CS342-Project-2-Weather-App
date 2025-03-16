import hourlyWeather.HourlyPeriod;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import weather.Period;
import weather.WeatherAPI;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DailyForecast extends SceneBuilder{
	public static BorderPane getScene(){
		//https://api.weather.gov/gridpoints/LOT/77,70/forecast/hourly

		//Testing!!!
		//
 		//
		// 1st hour

		HourlyPeriod currentForecast = MyWeatherAPI.getHourlyForecast("LOT", 77, 70).get(0);
		System.out.println("Forecast right now (current hour)");
		System.out.println(currentForecast.shortForecast);

		Date currentHour = currentForecast.startTime;//.after(new Date(Calendar.YEAR, Calendar.MARCH, Calendar.DAY_OF_MONTH)) ? currentForecast.startTime : new Date();
		System.out.println("All details of forecast right now: (Current hour)");
		System.out.println(currentHour);

		SimpleDateFormat localDateFormat = new SimpleDateFormat("hh a");
		System.out.println("Current time:");
		System.out.println(localDateFormat.format(currentHour));


        // 2nd hour
		HourlyPeriod currentForecastTwo = MyWeatherAPI.getHourlyForecast("LOT", 77, 70).get(1);
		System.out.println("\nForecast right now (2nd hour )");
		System.out.println(currentForecastTwo.shortForecast);

		Date currentHourTwo = currentForecastTwo.startTime;//.after(new Date(Calendar.YEAR, Calendar.MARCH, Calendar.DAY_OF_MONTH+1)) ? currentForecast.startTime : new Date();
		System.out.println("All details of forecast right now: (2nd hour)");
		System.out.println(currentHourTwo);

		SimpleDateFormat localDateFormatTwo = new SimpleDateFormat("hh a");
		System.out.println("Current time:");
		System.out.println(localDateFormatTwo.format(currentHourTwo));

		// Next Day
		SimpleDateFormat localDateFormatMilitary = new SimpleDateFormat("HH");
		System.out.println("\nCurrent time: (Military)");
		System.out.println(localDateFormatMilitary.format(currentHour));
		int current = Integer.parseInt(localDateFormatMilitary.format(currentHour));
		int calculateNextDay = 24 - current;
		System.out.println("Next day at: ");
		System.out.println(calculateNextDay);

		// Next Day Data Testing
		HourlyPeriod currentForecastThree = MyWeatherAPI.getHourlyForecast("LOT", 77, 70).get(calculateNextDay); // Very important, gets to next day
		Date currentHourThree = currentForecastThree.startTime;//.after(new Date(Calendar.YEAR, Calendar.MARCH, Calendar.DAY_OF_MONTH+1)) ? currentForecast.startTime : new Date();
		System.out.println("\nAll details of forecast right now: (next day)");
		System.out.println(currentHourThree);

		//
 		//
 		// Testing!
		// After clicking day button:
		// Implement a loop here for daily based on amount of days chosen (3,5, or 7)(current day):
		// print what day it is: "Sat March 14"
		// print the hourly times.
		// Print their forecasts.

		VBox root = new VBox(10);
		root.setAlignment(Pos.CENTER);
		ComboBox<String> numDaysChoices = new ComboBox<>();
		VBox textFieldsHolder = new VBox(5);
		textFieldsHolder.setAlignment(Pos.CENTER);

		numDaysChoices.getItems().addAll("1 Day", "3 Day", "5 Day", "7 Day");

		numDaysChoices.setOnAction(event -> {
			String day = numDaysChoices.getValue();
			int number = Integer.parseInt(day.split("")[0]);

			textFieldsHolder.getChildren().clear();
			for (int i = 0; i < number; i++) {
				textFieldsHolder.getChildren().add(new TextField("Day" + (i + 1)));
			}
		});

		root.getChildren().addAll(numDaysChoices, textFieldsHolder);

		Label dailyForecast = new Label("Daily Forecast");
		dailyForecast.setTextFill(Color.rgb(255,255,255));
		dailyForecast.setFont(new Font("Inter", 40));
		BorderPane dailyForecastPane = new BorderPane(dailyForecast);

		BackgroundFill background_fill = new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY);
		Background background = new Background(background_fill);

		root = new VBox(25, dailyForecastPane, root);
		root.setBackground(background);

		return new BorderPane(root);
	}
}
