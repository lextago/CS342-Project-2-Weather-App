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
		String stylesheet = "style.css";

		// Allows the changing of themes based on settings
		switch(theme){
			case "Matcha":
				stylesheet = "/css/trends/trends_matcha.css";
				break;
			case "Cocoa":
				stylesheet = "/css/trends/trends_cocoa.css";
				break;
			case "Milk":
				stylesheet = "/css/trends/trends_milk.css";
				break;
			case "Ube":
				stylesheet = "/css/trends/trends_ube.css";
				break;
		}
		scene.getStylesheets().add(SceneBuilder.class.getResource(stylesheet).toExternalForm());
		return scene;
	}

	public static BorderPane getRoot(){
		// Creates a "Weekly Trends" label for the title
		Label weeklyTrends = new Label("Weekly Trends");
		weeklyTrends.setTextFill(Color.WHITE);
		weeklyTrends.setFont(Font.font("Inter", FontWeight.BOLD, 40));
		weeklyTrends.setPadding(new Insets(20,20,20,20));
		weeklyTrends.setAlignment(Pos.CENTER);
		weeklyTrends.setEffect(HomeScreen.dropShadow);

		// Creates a dropdown with the choice of 3, 5, or 7 days of weekly trends to be displayed
		ComboBox<String> numDaysChoices = new ComboBox<>();
		numDaysChoices.setId("comboBox");
		numDaysChoices.setPromptText("Select Days");
		numDaysChoices.getItems().addAll("3 Day", "5 Day", "7 Day");
		numDaysChoices.setEffect(HomeScreen.dropShadow);
		numDaysChoices.setPrefWidth(230);

		ObservableList<String> days = FXCollections.observableArrayList(); // Stores days

		// Ensures that xAxis looks good when displayed with low and high temperature data
		CategoryAxis xAxis = new CategoryAxis();
		xAxis.setEffect(HomeScreen.dropShadow);
		xAxis.setTickLabelFont(Font.font("Verdana", 15));
		xAxis.setStyle("-fx-text-fill: White");
		xAxis.setTickLabelFill(Color.WHITE);

		// Ensures that yAxis looks good when displayed with low and high temperature data
		NumberAxis yAxis = new NumberAxis();
		yAxis.setEffect(HomeScreen.dropShadow);
		yAxis.setTickLabelFont(Font.font("Verdana", 15));
		yAxis.setStyle("-fx-text-fill: White");
		yAxis.setTickLabelFill(Color.WHITE);
		yAxis.setTickUnit(10);
		yAxis.setMinorTickCount(0);

		// Makes a lineChart with xAxis and yAxis
		final LineChart<String,Number> lineChart =
				new LineChart<String, Number>(xAxis,yAxis);
		lineChart.setEffect(HomeScreen.dropShadow);
		lineChart.setStyle("-fx-prompt-text-fill: White");

		ArrayList<Pair<String, double[]>> minAndMax = MyWeatherAPI.getMinAndMaxTemperatures(region, gridX, gridY);
		HBox allWeekDays = new HBox();
		allWeekDays.setAlignment(Pos.CENTER);

		// Displays number of days desired everytime a dropdown selection is clicked
		numDaysChoices.setOnAction(e -> {;
            // Gets number of days desired
			String dayChoice = numDaysChoices.getValue();
			int dayChoiceNumber = Integer.parseInt(dayChoice.split("")[0]);

			allWeekDays.getChildren().clear();
			lineChart.getData().clear();
			days.clear();

			// Creating the lines for low and high temperature
			XYChart.Series lowTempLine = new XYChart.Series();
			lowTempLine.setName("Low");
			XYChart.Series highTempLine = new XYChart.Series();
			highTempLine.setName("High");

			// Adds low temperature and high temperature for number of days desired
			for (int i = 0; i < dayChoiceNumber; i++) {
				// Gets current day abbreviation and adds it to xAxis
				Pair<String, double[]> pair = minAndMax.get(i);
				String dateString = pair.getKey();
				String dateTimeString = dateString.split("/")[0]; // Makes the date format ISO
				DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
				OffsetDateTime dateTime = OffsetDateTime.parse(dateTimeString, formatter);
				DayOfWeek dayOfWeek = dateTime.getDayOfWeek(); // Gets the day of the week
				String dayOfWeekAbbreviation = dayOfWeek.toString().substring(0,3);
				days.add(dayOfWeekAbbreviation);
				xAxis.setCategories(days);

				int min = (int) minAndMax.get(i).getValue()[0]; // Low temperature
				int max = (int) minAndMax.get(i).getValue()[1];

				if (temperatureUnit.equals("Fahrenheit")) {
					min = (int) (min * 1.8 + 32); // Celsius to Fahrenheit
					max = (int) (max * 1.8 + 32);
				}

				lowTempLine.getData().add(new XYChart.Data(dayOfWeekAbbreviation, min)); // Adds low temperature to the low temperature line
				highTempLine.getData().add(new XYChart.Data(dayOfWeekAbbreviation, max));
			}
			// lineChart gets all the lines
			lineChart.getData().addAll(lowTempLine, highTempLine);
			lineChart.setId("firstChart");
		});

		// Organizes everything to be displayed properly
		HBox chartBox = new HBox(lineChart);
		VBox dropdownAndTitle = new VBox(4, weeklyTrends, numDaysChoices, chartBox);
		dropdownAndTitle.setAlignment(Pos.CENTER);
		VBox root = new VBox(30, dropdownAndTitle);
		root.setBackground(new Background(backgroundImage));

		return new BorderPane(root);
	}
}