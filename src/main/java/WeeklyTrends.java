import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class WeeklyTrends extends SceneBuilder {
	public static Scene getScene(){
		BorderPane root = getRoot();
		BorderPane rootPane = new BorderPane(root);
		rootPane.setBottom(NavigationBar.getNavigationBar());

		Scene scene = new Scene(rootPane, 360, 640);
		scene.getStylesheets().add(NavigationBar.class.getResource("/css/style.css").toExternalForm());

		return scene;
	}

	public static BorderPane getRoot(){
		TextField weeklyTrends = new TextField("Weekly Trends");
		VBox root = new VBox(weeklyTrends);
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Pair;

import java.time.DayOfWeek;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class WeeklyTrends extends SceneBuilder {
	public static BorderPane getScreen(){
		// Use WeatherAPI in weather folder
		// https://api.weather.gov/gridpoints/LOT/77,70/forecast
		Label weeklyTrends = new Label("Weekly Trends");
		weeklyTrends.setTextFill(Color.rgb(255,255,255));
		weeklyTrends.setFont(new Font("Inter", 40));
		BorderPane weeklyTrendsPane = new BorderPane(weeklyTrends);

		ComboBox<String> numDaysChoices = new ComboBox<>(); // Dropdown of all day choices
		numDaysChoices.setPromptText("Select Days");
		numDaysChoices.setId("daysComboBox");
		numDaysChoices.getItems().addAll("3 Day", "5 Day", "7 Day");

		HBox numDaysAndTempBox = new HBox(numDaysChoices);

		NumberAxis xAxis = new NumberAxis();
		NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Day");

		final LineChart<Number,Number> lineChart =
				new LineChart<Number, Number>(xAxis,yAxis);

		lineChart.setTitle("Weekly Trends");


		numDaysChoices.setOnAction(e -> {;

			String dayChoice = numDaysChoices.getValue();
			int dayChoiceNumber = Integer.parseInt(dayChoice.split("")[0]);

			lineChart.getData().clear();

			XYChart.Series xyValues = new XYChart.Series();
			xyValues.setName("Low");

			XYChart.Series xyValuesTwo = new XYChart.Series();
			xyValuesTwo.setName("High");

			ArrayList<Pair<String, double[]>> minAndMax = MyWeatherAPI.getMinAndMaxTemperatures("LOT", 77, 70);

			for (int i = 0; i < dayChoiceNumber; i++) {
				Pair<String, double[]> pair = minAndMax.get(i);
				String dateString = pair.getKey();

				String dateTimeString = dateString.split("/")[0]; // Makes the date format ISO

				// Formats properly to get date
				DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
				OffsetDateTime dateTime = OffsetDateTime.parse(dateTimeString, formatter);

				DayOfWeek dayOfWeek = dateTime.getDayOfWeek(); // Gets the day of the week
				String dayOfWeekAbbreviation = dayOfWeek.toString().substring(0,3);
//				System.out.println(dayOfWeekAbbreviation);

				int min = (int) minAndMax.get(i).getValue()[0]; // Lowest Celsius for the day

				min = (int) (min * 1.8 + 32); // Celsius to Fahrenheit

				int max = (int) minAndMax.get(i).getValue()[1];

				max = (int) (max * 1.8 + 32);

				xyValues.getData().add(new XYChart.Data(i+1, min));
				xyValuesTwo.getData().add(new XYChart.Data(i+1, max));
//				System.out.println(dayOfWeekAbbreviation);
			}

			lineChart.getXAxis().setLabel("Day");
			//lineChart.getXAxis().setTickLabelsVisible(true);
			lineChart.getXAxis().setStyle("-fx-background-color: green; -fx-border-color: black; -fx-prompt-text-fill: white");
			lineChart.getXAxis().setTickLabelGap(1);

			lineChart.getData().addAll(xyValues, xyValuesTwo);
		});
		HBox chartBox = new HBox(lineChart);

		Label mondayLabel = new Label("M");
		mondayLabel.setFont(new Font("Inter", 40));

		Label tuesdayLabel = new Label("T");
		tuesdayLabel.setFont(new Font("Inter", 40));

		Label wednesdayLabel = new Label("W");
		wednesdayLabel.setFont(new Font("Inter", 40));

		Label thursdayLabel = new Label("Th");
		thursdayLabel.setFont(new Font("Inter", 40));

		Label fridayLabel = new Label("F");
		fridayLabel.setFont(new Font("Inter", 40));

		Label saturdayLabel = new Label("S");
		saturdayLabel.setFont(new Font("Inter", 40));

		Label sundayLabel = new Label("SU");
		sundayLabel.setFont(new Font("Inter", 40));

		BorderPane mondayPane = new BorderPane();
		mondayPane.setCenter(mondayLabel);
		mondayPane.setPadding(new Insets(5));
		mondayPane.setStyle("-fx-background-color: #FFFFFF;");
		mondayPane.setBorder(Border.stroke(Color.BLACK));

		BorderPane tuesdayPane = new BorderPane();
		tuesdayPane.setCenter(tuesdayLabel);
		tuesdayPane.setPadding(new Insets(5));
		tuesdayPane.setStyle("-fx-background-color: #FFFFFF;");
		tuesdayPane.setBorder(Border.stroke(Color.BLACK));

		BorderPane wednesdayPane = new BorderPane();
		wednesdayPane.setCenter(wednesdayLabel);
		wednesdayPane.setPadding(new Insets(5));
		wednesdayPane.setStyle("-fx-background-color: #FFFFFF;");
		wednesdayPane.setBorder(Border.stroke(Color.BLACK));

		BorderPane thursdayPane = new BorderPane();
		thursdayPane.setCenter(thursdayLabel);
		thursdayPane.setPadding(new Insets(5));
		thursdayPane.setStyle("-fx-background-color: #FFFFFF;");
		thursdayPane.setBorder(Border.stroke(Color.BLACK));

		BorderPane fridayPane = new BorderPane();
		fridayPane.setCenter(fridayLabel);
		fridayPane.setPadding(new Insets(5));
		fridayPane.setStyle("-fx-background-color: #FFFFFF;");
		fridayPane.setBorder(Border.stroke(Color.BLACK));

		BorderPane saturdayPane = new BorderPane();
		saturdayPane.setCenter(saturdayLabel);
		saturdayPane.setPadding(new Insets(5));
		saturdayPane.setStyle("-fx-background-color: #FFFFFF;");
		saturdayPane.setBorder(Border.stroke(Color.BLACK));

		BorderPane sundayPane = new BorderPane();
		sundayPane.setCenter(sundayLabel);
		sundayPane.setPadding(new Insets(5));
		sundayPane.setStyle("-fx-background-color: #FFFFFF;");
		sundayPane.setBorder(Border.stroke(Color.BLACK));

		HBox allWeekDays = new HBox(mondayPane, tuesdayPane, wednesdayPane, thursdayPane, fridayPane, saturdayPane, sundayPane);
		allWeekDays.setAlignment(Pos.CENTER);

		VBox dropdownAndTitle = new VBox(4, weeklyTrendsPane, numDaysAndTempBox);
		dropdownAndTitle.setAlignment(Pos.CENTER);
		VBox root = new VBox(30, dropdownAndTitle, chartBox, allWeekDays);

		Image homeBackground = new Image("/images/matcha-background.jpg", 360, 640, false, true);
		BackgroundImage backgroundImage = new BackgroundImage(homeBackground, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null, null);
		root.setBackground(new Background(backgroundImage));

		return new BorderPane(root);
	}
}