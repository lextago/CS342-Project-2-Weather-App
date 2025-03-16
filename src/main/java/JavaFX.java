import javafx.application.Application;

import javafx.scene.Scene;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class JavaFX extends Application {

	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(Stage primaryStage){
		primaryStage.setTitle("Home Screen");
		SceneBuilder sceneBuilder = new SceneBuilder();
		sceneBuilder.setStage(primaryStage);

		NavigationBar navBar = new NavigationBar(primaryStage);
		HBox navigationBar = navBar.getNavigationBar();

		BorderPane homeRoot = HomeScreen.getScreen();
		homeRoot.setBottom(navigationBar);

		Scene home = new Scene(homeRoot,  360, 640);

		home.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

		primaryStage.setResizable(false);

		primaryStage.setScene(home);
		primaryStage.show();
	}
}
