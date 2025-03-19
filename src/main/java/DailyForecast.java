import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Pair;
import weather.Period;

import java.time.DayOfWeek;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DailyForecast extends SceneBuilder{
	public static Scene getScene(){
		BorderPane root = getRoot();
		BorderPane rootPane = new BorderPane(root);
		rootPane.setBottom(NavigationBar.getNavigationBar());

		Scene scene = new Scene(rootPane, 360, 640);
		scene.getStylesheets().add(NavigationBar.class.getResource("/css/style.css").toExternalForm());

		return scene;
	}
	public static BorderPane getRoot(){
		//https://api.weather.gov/gridpoints/LOT/77,70/forecast/hourly

		VBox root = new VBox(5);
		VBox holdEachDay = new VBox(1);

		ComboBox<String> numDaysChoices = new ComboBox<>(); // Dropdown of all day choices
		numDaysChoices.setPromptText("Select Days");
		numDaysChoices.setId("daysComboBox");
		numDaysChoices.getItems().addAll("3 Day", "5 Day", "7 Day");

		// Outputs the number of days desired

		numDaysChoices.setOnAction(event -> {

			// Gets number of days desired
			String dayChoice = numDaysChoices.getValue();
			int dayChoiceNumber = Integer.parseInt(dayChoice.split("")[0]);

			holdEachDay.getChildren().clear();

			ArrayList<Pair<String, double[]>> minAndMax = MyWeatherAPI.getMinAndMaxTemperatures("LOT", 77, 70);
			ArrayList<Period> forecastPeriod = MyWeatherAPI.getForecast("LOT", 77, 70);


			for (int i = 0; i < dayChoiceNumber; i++) {

				int min = (int) minAndMax.get(i).getValue()[0]; // Lowest Celsius for the day
				int max = (int) minAndMax.get(i).getValue()[1];

				min = (int) (min*1.8 + 32); // Celsius to Fahrenheit
				max = (int) (max*1.8 + 32);


				Pair<String, double[]> pair = minAndMax.get(i);
				String dateString = pair.getKey();

				String dateTimeString = dateString.split("/")[0]; // Makes the date format ISO

				// Formats properly to get date
				DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
				OffsetDateTime dateTime = OffsetDateTime.parse(dateTimeString, formatter);

				DayOfWeek dayOfWeek = dateTime.getDayOfWeek(); // Gets the day of the week
				String dayOfWeekAbbreviation = dayOfWeek.toString().substring(0,3);

				double rainProbability = forecastPeriod.get(i).probabilityOfPrecipitation.value;

				Label leftLabel = new Label(dayOfWeekAbbreviation);
				leftLabel.setFont(Font.font("Inter", 20));
				leftLabel.setTextFill(Color.WHITE);
				Label centerLabel = new Label("L: " + min + " H: " + max);
				centerLabel.setFont(Font.font("Inter", 20));
				centerLabel.setTextFill(Color.WHITE);
				Label rightLabel = new Label(rainProbability + "%");
				rightLabel.setFont(Font.font("Inter", 20));
				rightLabel.setTextFill(Color.WHITE);


				BorderPane labelPane = new BorderPane();
				labelPane.setLeft(leftLabel);
				labelPane.setCenter(centerLabel);
				labelPane.setRight(rightLabel);
				labelPane.setPadding(new Insets(5));
				labelPane.setBorder(Border.stroke(Color.BLACK));
				labelPane.setStyle("-fx-background-color: #588157;");
				labelPane.setOpacity(.8);
				labelPane.setPrefWidth(340);

				HBox hbox = new HBox(10, labelPane);
				hbox.setAlignment(Pos.CENTER);

				TextField field = new TextField(dayOfWeek + "     L:" + min + "     H:" + max);
				field.setEditable(false);
				field.setFont(Font.font("Inter", 20));
				field.setStyle("-fx-text-fill: #ffffff; -fx-background-color: rgba(88,129,87,0.8);");
				field.setBorder(Border.stroke(Color.BLACK));
				field.setPrefSize(160,50);
				holdEachDay.getChildren().add(hbox);
			}
		});

		root.getChildren().addAll(numDaysChoices, holdEachDay);
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

		Image homeBackground = new Image("/images/backgrounds/matcha-background.jpg", 360, 640, false, true);
		BackgroundImage backgroundImage = new BackgroundImage(homeBackground, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null, null);
		root.setBackground(new Background(backgroundImage));

		return new BorderPane(root);
	}
}
