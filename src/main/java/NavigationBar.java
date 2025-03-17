import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class NavigationBar {
	public static HBox navigationBar;
	private static Stage mainStage;

	public HBox getNavigationBar() {
		return navigationBar;
	}

	public NavigationBar(Stage stage){
		mainStage = stage;

		Button homeButton, dailyButton, trendsButton, settingsButton;

		homeButton = new Button("Home");
		homeButton.setPrefSize(160, 50);

		dailyButton = new Button("Daily");
		dailyButton.setPrefSize(160,50);

		trendsButton = new Button("Trends");
		trendsButton.setPrefSize(160,50);

		settingsButton = new Button("Settings");
		settingsButton.setPrefSize(160,50);

		homeButton.setOnAction(e -> navigationBarHandler(e, HomeScreen.getScreen(), "Home Screen"));
		dailyButton.setOnAction(e -> navigationBarHandler(e, DailyForecast.getScreen(), "Daily Forecast"));
		trendsButton.setOnAction(e -> navigationBarHandler(e, WeeklyTrends.getScreen(), "Weekly Trends"));
		settingsButton.setOnAction(e -> navigationBarHandler(e, Settings.getScreen(), "Settings"));

		navigationBar = new HBox(homeButton, dailyButton, trendsButton, settingsButton);
	}

	/*	Event handler for each button in the navigation bar.
	Adds the navigation bar to the bottom of the BorderPane, uses that BorderPane as the root
	for the next scene, then changes the stage's current scene to the next.
	*/
	private static void navigationBarHandler(ActionEvent event, BorderPane root, String title){
		root.setBottom(navigationBar);

		Scene nextScene = new Scene(root, 360, 640);
		nextScene.getStylesheets().add(NavigationBar.class.getResource("/css/style.css").toExternalForm());

		mainStage.setScene(nextScene);
		mainStage.setTitle(title);
	}
}
