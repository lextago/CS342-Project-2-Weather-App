import javafx.application.Application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class JavaFX extends Application {

	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(Stage primaryStage){
		Label welcome = new Label("CS342 Project 2: Weather App");
		welcome.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

		Label description = new Label("Please be patient while the app loads. The API can be slow at times.");
		description.setMaxWidth(300);
		description.setWrapText(true);
		description.setTextAlignment(TextAlignment.CENTER);

		Label author = new Label("Spring 2025 Project by Alexander Tago and Nicholas Laird");
		author.setFont(Font.font("Verdana", 10));

		Button continueButton = new Button("Continue");
		continueButton.setPrefSize(100,50);
		continueButton.setOnAction(e -> {
			primaryStage.close();
			primaryStage.setTitle("Home Screen");
			SceneBuilder sceneBuilder = new SceneBuilder();
			sceneBuilder.setStage(primaryStage);

			NavigationBar navBar = new NavigationBar(primaryStage);
			Scene home = HomeScreen.getScene();

			primaryStage.setResizable(false);
			primaryStage.setScene(home);
			primaryStage.show();
		});

		VBox rootTop = new VBox(5, welcome, description);
		rootTop.setAlignment(Pos.CENTER);

		BorderPane rootPane = new BorderPane();
		rootPane.setTop(rootTop);
		rootPane.setCenter(continueButton);
		rootPane.setBottom(author);
		rootPane.setPadding(new Insets(5));
		rootPane.setId("root");

		Scene scene = new Scene(rootPane, 400, 200);
		scene.getStylesheets().add(getClass().getResource("/css/main.css").toExternalForm());

		primaryStage.setTitle("Welcome to our Weather App");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
