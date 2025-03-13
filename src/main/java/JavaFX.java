import javafx.application.Application;

import javafx.event.ActionEvent;
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

public class JavaFX extends Application {
	HBox navigationBar;

	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("I'm a professional Weather App!");

		Button homeButton, dailyButton, trendsButton, settingsButton;

		homeButton = new Button("Home");
		homeButton.setPrefWidth(160);

		dailyButton = new Button("Daily");
		dailyButton.setPrefWidth(160);

		trendsButton = new Button("Trends");
		trendsButton.setPrefWidth(160);

		settingsButton = new Button("Settings");
		settingsButton.setPrefWidth(160);

		navigationBar = new HBox(homeButton, dailyButton, trendsButton, settingsButton);

		homeButton.setOnAction(e -> navigationBarHandler(e, SceneBuilder.homeScreen(), primaryStage));
		dailyButton.setOnAction(e -> navigationBarHandler(e, SceneBuilder.dailyForecastScreen(), primaryStage));
		trendsButton.setOnAction(e -> navigationBarHandler(e, SceneBuilder.weeklyTrendsScreen(), primaryStage));
		settingsButton.setOnAction(e -> navigationBarHandler(e, SceneBuilder.settingsScreen(), primaryStage));

		BorderPane homeRoot = SceneBuilder.homeScreen();
		homeRoot.setBottom(navigationBar);

		Scene home = new Scene(homeRoot,  360, 640);
		primaryStage.setScene(home);
		primaryStage.show();
	}

	/*	Event handler for each button in the navigation bar.
		Adds the navigation bar to the bottom of the BorderPane, uses that BorderPane as the root
		for the next scene, then changes the stage's current scene to the next.
	*/
	private void navigationBarHandler(ActionEvent event, BorderPane root, Stage currentStage){
		root.setBottom(navigationBar);

		Scene nextScene = new Scene(root, 360, 640);
		currentStage.setScene(nextScene);
	}
}
