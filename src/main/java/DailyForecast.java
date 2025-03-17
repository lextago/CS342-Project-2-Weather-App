import hourlyWeather.HourlyPeriod;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Pair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DailyForecast extends SceneBuilder{
	public static BorderPane getScreen(){
		//https://api.weather.gov/gridpoints/LOT/77,70/forecast/hourly

		VBox root = new VBox(5);

		ComboBox<String> numDaysChoices = new ComboBox<>();
		VBox textFieldsHolder = new VBox(1);

		numDaysChoices.getItems().addAll("1 Day", "3 Day", "5 Day", "7 Day");

		numDaysChoices.setOnAction(event -> {
			String day = numDaysChoices.getValue();
			int number = Integer.parseInt(day.split("")[0]);

			textFieldsHolder.getChildren().clear();

			int currents = 0;

			List<HourlyPeriod> forecasts = MyWeatherAPI.getHourlyForecast("LOT", 77, 70);
			ArrayList<Pair<String, double[]>> minAndMax = MyWeatherAPI.getMinAndMaxTemperatures("LOT", 77, 70);
			SimpleDateFormat localDateFormatCurrentDays2 = new SimpleDateFormat("E MM/dd");
			SimpleDateFormat localDateFormatCurrentDays = new SimpleDateFormat("HH"); // Calculates current hour


			for (int i = 0; i < number; i++) {
				HourlyPeriod currentForecastNows = forecasts.get(currents);
				Date currentDays = currentForecastNows.startTime;
				int currentTimes = Integer.parseInt(localDateFormatCurrentDays.format(currentDays));
				if (i == 0) {
					currents = 24 - currentTimes;
				} else {
					currents += 24;
				}

				String formattedDate = localDateFormatCurrentDays2.format(currentDays);
				double min = minAndMax.get(i).getValue()[0];
				double max = minAndMax.get(i).getValue()[1];
				min = min*1.8 + 32; // Celsius to Fahrenheit
				max = max*1.8 + 32;

				TextField field = new TextField(formattedDate + " " + min + " " + max);
				field.setEditable(false);
				field.setFont(new Font("Inter", 20));
				field.setStyle("-fx-text-fill: #ffffff; -fx-background-color: rgba(88,129,87,0.8);");
				field.setBorder(Border.stroke(Color.BLACK));
				field.setPrefSize(160,50);
				textFieldsHolder.getChildren().add(field);
			}
		});

		root.getChildren().addAll(numDaysChoices, textFieldsHolder);
		BorderPane rootPane = new BorderPane(root);
		rootPane.setPrefWidth(350);
		HBox hBox = new HBox(rootPane);
		hBox.setAlignment(Pos.CENTER);

		Label dailyForecast = new Label("Daily Forecast");
		dailyForecast.setTextFill(Color.rgb(255,255,255));
		dailyForecast.setFont(new Font("Inter", 40));
		BorderPane dailyForecastPane = new BorderPane(dailyForecast);

		BackgroundFill background_fill = new BackgroundFill(Color.rgb(160,255,161), CornerRadii.EMPTY, Insets.EMPTY);
		Background background = new Background(background_fill);

		root = new VBox(30, dailyForecastPane, hBox);
		root.setBackground(background);

		return new BorderPane(root);
	}
}
