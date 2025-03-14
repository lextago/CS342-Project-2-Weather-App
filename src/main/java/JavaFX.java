import hourlyWeather.HourlyPeriod;
import javafx.application.Application;

import javafx.event.ActionEvent;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class JavaFX extends Application {
	HBox navigationBar;

	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Home Screen");

		Button homeButton, dailyButton, trendsButton, settingsButton;

		homeButton = new Button("Home");
		homeButton.setPrefSize(160, 50);

		dailyButton = new Button("Daily");
		dailyButton.setPrefSize(160,50);

		trendsButton = new Button("Trends");
		trendsButton.setPrefSize(160,50);

		settingsButton = new Button("Settings");
		settingsButton.setPrefSize(160,50);

		navigationBar = new HBox(homeButton, dailyButton, trendsButton, settingsButton);

		homeButton.setOnAction(e -> navigationBarHandler(e,  primaryStage, HomeScreen.getScreen(), "Home Screen"));
		dailyButton.setOnAction(e -> navigationBarHandler(e, primaryStage, DailyForecast.getScreen(), "Daily Forecast"));
		trendsButton.setOnAction(e -> navigationBarHandler(e,  primaryStage, WeeklyTrends.getScreen(), "Weekly Trends"));
		settingsButton.setOnAction(e -> navigationBarHandler(e,  primaryStage, Settings.getScreen(), "Settings"));

		BorderPane homeRoot = HomeScreen.getScreen();
		homeRoot.setBottom(navigationBar);

		Scene home = new Scene(homeRoot,  360, 640);
		primaryStage.setMinWidth(370);
		primaryStage.setMinHeight(650);
		primaryStage.setMaxWidth(370);
		primaryStage.setMaxHeight(650);

		primaryStage.setScene(home);
		primaryStage.show();

		ArrayList<HourlyPeriod> hourlyPeriods = MyWeatherAPI.getHourlyForecast("LOT",77,70);
		System.out.println(hourlyPeriods.size());

	}

	/*	Event handler for each button in the navigation bar.
		Adds the navigation bar to the bottom of the BorderPane, uses that BorderPane as the root
		for the next scene, then changes the stage's current scene to the next.
	*/
	private void navigationBarHandler(ActionEvent event, Stage currentStage, BorderPane root, String title){
		root.setBottom(navigationBar);

		Scene nextScene = new Scene(root, 360, 640);
		currentStage.setScene(nextScene);
		currentStage.setTitle(title);
	}
}
