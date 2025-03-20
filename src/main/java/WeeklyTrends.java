import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Pair;

import java.time.DayOfWeek;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class WeeklyTrends extends SceneBuilder {
	public static Scene getScene(){
		BorderPane root = getRoot();
		BorderPane rootPane = new BorderPane(root);
		rootPane.setBottom(NavigationBar.getNavigationBar());

		Scene scene = new Scene(rootPane, 360, 640);
		scene.getStylesheets().add(SceneBuilder.class.getResource("/css/trends.css").toExternalForm());

		return scene;
	}

	public static BorderPane getRoot(){
		// Use WeatherAPI in weather folder
		// https://api.weather.gov/gridpoints/LOT/77,70/forecast
		Label weeklyTrends = new Label("Weekly Trends");
		weeklyTrends.setTextFill(Color.WHITE);
		weeklyTrends.setFont(Font.font("Inter", FontWeight.BOLD, 40));
		weeklyTrends.setPadding(new Insets(20,20,20,20));
		weeklyTrends.setAlignment(Pos.CENTER);
		weeklyTrends.setEffect(HomeScreen.dropShadow);
		BorderPane weeklyTrendsPane = new BorderPane(weeklyTrends);

		ComboBox<String> numDaysChoices = new ComboBox<>(); // Dropdown of all day choices
		numDaysChoices.setId("daysComboBox");
		numDaysChoices.setPromptText("Select Days");
		numDaysChoices.getItems().addAll("3 Day", "5 Day", "7 Day");
		numDaysChoices.setEffect(HomeScreen.dropShadow);
		numDaysChoices.setPrefWidth(230);

		HBox numDaysAndTempBox = new HBox(numDaysChoices);
		numDaysAndTempBox.setAlignment(Pos.CENTER);

		ObservableList<String> days = FXCollections.observableArrayList();

		CategoryAxis xAxis = new CategoryAxis();
		xAxis.setEffect(HomeScreen.dropShadow);
		xAxis.setTickLabelFont(Font.font("Verdana", 15));
		xAxis.setStyle("-fx-text-fill: White");
		xAxis.setTickLabelFill(Color.WHITE);

		NumberAxis yAxis = new NumberAxis();
		yAxis.setEffect(HomeScreen.dropShadow);
		yAxis.setTickLabelFont(Font.font("Verdana", 15));
		yAxis.setStyle("-fx-text-fill: White");
		yAxis.setTickLabelFill(Color.WHITE);
		yAxis.setTickUnit(10);
		yAxis.setMinorTickCount(0);

		final LineChart<String,Number> lineChart =
				new LineChart<String, Number>(xAxis,yAxis);

		lineChart.setEffect(HomeScreen.dropShadow);
		lineChart.setStyle("-fx-prompt-text-fill: White");

		ArrayList<Pair<String, double[]>> minAndMax = MyWeatherAPI.getMinAndMaxTemperatures(region, gridX, gridY);

		HBox allWeekDays = new HBox();

		numDaysChoices.setOnAction(e -> {;

			String dayChoice = numDaysChoices.getValue();
			int dayChoiceNumber = Integer.parseInt(dayChoice.split("")[0]);
			allWeekDays.getChildren().clear();

			lineChart.getData().clear();
			days.clear();

			XYChart.Series xyValues = new XYChart.Series();
			xyValues.setName("Low");

			XYChart.Series xyValuesTwo = new XYChart.Series();
			xyValuesTwo.setName("High");

			for (int i = 1; i < dayChoiceNumber + 1; i++) {
				Pair<String, double[]> pair = minAndMax.get(i);
				String dateString = pair.getKey();

				String dateTimeString = dateString.split("/")[0]; // Makes the date format ISO

				// Formats properly to get date
				DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
				OffsetDateTime dateTime = OffsetDateTime.parse(dateTimeString, formatter);

				DayOfWeek dayOfWeek = dateTime.getDayOfWeek(); // Gets the day of the week
				String dayOfWeekAbbreviation = dayOfWeek.toString().substring(0,3);

				/*String dayOfWeekTwoLetter = dayOfWeek.toString().substring(0,2);
				String dayOfWeekOneLetter = dayOfWeek.toString().substring(0,1);
				if (dayOfWeekTwoLetter.equals("TH") || dayOfWeekTwoLetter.equals("SU")) {
					Label weekDayLabel = new Label(dayOfWeekTwoLetter);
					weekDayLabel.setFont(new Font("Inter", 40));
					BorderPane weekDayPane = new BorderPane();
					weekDayPane.setEffect(HomeScreen.dropShadow);
					weekDayPane.setCenter(weekDayLabel);
					weekDayPane.setPadding(new Insets(5));
					weekDayPane.setStyle("-fx-background-color: #FFFFFF;");
					weekDayPane.setBorder(Border.stroke(Color.BLACK));
					allWeekDays.getChildren().add(weekDayPane);
				} else{
					Label weekDayLabel = new Label(dayOfWeekOneLetter);
					weekDayLabel.setFont(new Font("Inter", 40));
					BorderPane weekDayPane = new BorderPane();
					weekDayPane.setEffect(HomeScreen.dropShadow);
					weekDayPane.setCenter(weekDayLabel);
					weekDayPane.setPadding(new Insets(5));
					weekDayPane.setStyle("-fx-background-color: #FFFFFF;");
					weekDayPane.setBorder(Border.stroke(Color.BLACK));
					allWeekDays.getChildren().add(weekDayPane);
				}*/


				days.add(dayOfWeekAbbreviation);
				xAxis.setCategories(days);
				int min = (int) minAndMax.get(i).getValue()[0];
				int max = (int) minAndMax.get(i).getValue()[1];

				if (temperatureUnit.equals("Fahrenheit")) {
					min = (int) (min * 1.8 + 32); // Celsius to Fahrenheit
					max = (int) (max * 1.8 + 32);
				}

				xyValues.getData().add(new XYChart.Data(dayOfWeekAbbreviation, min));
				xyValuesTwo.getData().add(new XYChart.Data(dayOfWeekAbbreviation, max));
			}

			lineChart.getData().addAll(xyValues, xyValuesTwo);
			lineChart.setId("firstChart");
		});

		HBox chartBox = new HBox(lineChart);

		allWeekDays.setAlignment(Pos.CENTER);

		VBox dropdownAndTitle = new VBox(4, weeklyTrendsPane, numDaysAndTempBox, chartBox);
		dropdownAndTitle.setAlignment(Pos.CENTER);
		VBox root = new VBox(30, dropdownAndTitle);

		root.setBackground(new Background(backgroundImage));

		return new BorderPane(root);
	}
}