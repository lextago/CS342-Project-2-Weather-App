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
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;

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

		primaryStage.setResizable(false);

		primaryStage.setScene(home);
		primaryStage.show();

		ArrayList<Pair<String, double[]>> minAndMax = MyWeatherAPI.getMinAndMaxTemperatures("LOT", 77, 70);
		for(Pair<String, double[]> pair : minAndMax){
			System.out.println(pair.getKey() + " " + Arrays.toString(pair.getValue()));
		}

		ArrayList<Pair<String, Integer>> rainChances = MyWeatherAPI.getProbabilityOfPrecipitation("LOT", 77, 70);
		for(Pair<String, Integer> pair : rainChances){
			System.out.println(pair.getKey() + " " + pair.getValue());
		}

	}
}
