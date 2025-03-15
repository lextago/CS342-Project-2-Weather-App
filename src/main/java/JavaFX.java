import hourlyWeather.HourlyPeriod;
import javafx.application.Application;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class JavaFX extends Application {
	private static Stage mainStage;
	private static HBox navigationBar;

	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(Stage primaryStage){
		primaryStage.setTitle("Home Screen");
		SceneBuilder sceneBuilder = new SceneBuilder();
		sceneBuilder.setStage(primaryStage);
		mainStage = primaryStage;

		DailyForecast dailyForecast = new DailyForecast();
		WeeklyTrends weeklyTrends = new WeeklyTrends();
		Settings settings = new Settings();

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

		homeButton.setOnAction(e -> navigationBarHandler(e, HomeScreen.getScreen(), "Home Screen"));
		dailyButton.setOnAction(e -> navigationBarHandler(e, DailyForecast.getScreen(), "Daily Forecast"));
		trendsButton.setOnAction(e -> navigationBarHandler(e, WeeklyTrends.getScreen(), "Weekly Trends"));
		settingsButton.setOnAction(e -> navigationBarHandler(e, Settings.getScreen(), "Settings"));

		BorderPane homeRoot = HomeScreen.getScreen();
		homeRoot.setBottom(navigationBar);

		Scene home = new Scene(homeRoot,  360, 640);

		primaryStage.setResizable(false);

		primaryStage.setScene(home);
		primaryStage.show();

		ArrayList<HourlyPeriod> hourlyPeriods = MyWeatherAPI.getHourlyForecast("LOT",77,70);
//		System.out.println(hourlyPeriods.size());

	}

	/*	Event handler for each button in the navigation bar.
		Adds the navigation bar to the bottom of the BorderPane, uses that BorderPane as the root
		for the next scene, then changes the stage's current scene to the next.
	*/
	private void navigationBarHandler(ActionEvent event, BorderPane root, String title){
		root.setBottom(navigationBar);

		Scene nextScene = new Scene(root, 360, 640);
		mainStage.setScene(nextScene);
		mainStage.setTitle(title);
	}

	public void popupErrorMsg() {
		Stage myDialog = new Stage();


		Button okButton = new Button("Ok");
		okButton.setOnAction(e-> {
			myDialog.close();
		});

		VBox root = new VBox(30, okButton);
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(10));

		Scene myDialogScene = new Scene(root, 300, 250);

		myDialog.setScene(myDialogScene);

		myDialog.initOwner(mainStage);
		myDialog.initModality(Modality.WINDOW_MODAL);
		myDialog.showAndWait();
	}
}
