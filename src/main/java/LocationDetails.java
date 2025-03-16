import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;

public class LocationDetails extends SceneBuilder{
	public static Stage dialogStage;
	private static ArrayList<Pair<String, Double[]>> locations;
	private static VBox locationsBox;
	private static ScrollPane locationsScroll;

	public LocationDetails(Stage stage) {
		dialogStage = stage;
	}

	public static BorderPane getScreen() {
		Button back = new Button("Back");
		Button info = new Button("Info");
		BorderPane locationTop = new BorderPane();
		locationTop.setLeft(back);
		locationTop.setRight(info);
		locationTop.setPrefHeight(50);

		back.setOnAction(e -> {
			dialogStage.close();
		});

		Label locationLabel = new Label("Change Location");

		Label cityLabel = new Label("City:");
		Label stateLabel = new Label("State:");
		Label longitudeLabel = new Label("Longitude:");
		Label latitudeLabel = new Label("Latitude:");

		Label coordsLabel = new Label("Coordinates (ISO 8601 format):");

		TextField cityInput = new TextField();
		TextField stateInput = new TextField();
		TextField longitudeInput = new TextField();
		TextField latitudeInput = new TextField();

		HBox cityBox = getDetailsBox(cityLabel, cityInput);
		HBox stateBox = getDetailsBox(stateLabel, stateInput);
		HBox longitudeBox = getDetailsBox(longitudeLabel, longitudeInput);
		HBox latitudeBox = getDetailsBox(latitudeLabel, latitudeInput);

		VBox placeBox = new VBox(5, locationLabel, cityBox, stateBox);
		VBox coordsBox = new VBox(5, coordsLabel, longitudeBox, latitudeBox);

		if(System.getenv("MAPBOX_API_KEY") == null){
			cityInput.setEditable(false);
			stateInput.setEditable(false);
		}

		Button submitButton = new Button("Submit");
		HBox submitHBox = new HBox(submitButton);

		Label results = new Label("Results");
		locationsScroll = new ScrollPane();
		locationsScroll.setPrefHeight(225);

		VBox rootCenterBox = new VBox(20, placeBox, coordsBox);
		BorderPane rootCenter = new BorderPane(rootCenterBox);
		rootCenter.setBottom(submitHBox);
		submitHBox.setAlignment(Pos.CENTER_RIGHT);

		locationsBox = new VBox(5, results, locationsScroll);
		BorderPane root = new BorderPane(rootCenter);

		root.setTop(locationTop);
		root.setBottom(locationsBox);
		root.setPadding(new Insets(10));

		submitButton.setOnAction(e -> {
			city = cityInput.getText();
			state = stateInput.getText();

			if(!longitudeInput.getText().isEmpty() && !latitudeInput.getText().isEmpty()){
				longitude = Double.parseDouble(longitudeInput.getText());
				latitude = Double.parseDouble(latitudeInput.getText());
			}

			locations = MyWeatherAPI.getCoords(city, state);

			for(Pair<String, Double[]> pair : locations){
				System.out.println(Arrays.toString(pair.getValue()) + " " + pair.getKey());
			}

			locationsScroll = getLocationScroll();
			locationsBox = new VBox(5, results, locationsScroll);
			root.setBottom(locationsBox);
		});

		return new BorderPane(root);
	}

	private static HBox getDetailsBox(Label detailLabel, TextField detailInput){
		detailInput.setPrefWidth(100);

		BorderPane detailPane = new BorderPane();
		detailPane.setLeft(detailLabel);
		detailPane.setRight(detailInput);

		detailPane.setPadding(new Insets(5));
		detailPane.setBorder(Border.stroke(Color.BLACK));
		detailPane.setStyle("-fx-background-color: #FFFFFF;");
		detailPane.setPrefWidth(295);

		HBox detailBox = new HBox(detailPane);
		detailBox.setAlignment(Pos.CENTER);

		return detailBox;
	}

	private static ScrollPane getLocationScroll(){
		VBox locationsBox = new VBox();

		ArrayList<Button> buttonsArray = new ArrayList<>();
		ArrayList<Pair<String, int[]>> gridInfoArray = new ArrayList<>();
		for(Pair<String, Double[]> pair : locations){
			Pair<String, int[]> gridInfo = MyWeatherAPI.getGridInfo(pair.getValue()[0], pair.getValue()[1]);
//			System.out.println(Arrays.toString(gridInfo.getValue()) + " " + gridInfo.getKey());
			if(gridInfo == null || gridInfo.getKey().isEmpty()){
				continue;
			}
			gridInfoArray.add(gridInfo);

			Button locationButton = new Button(pair.getKey());
			buttonsArray.add(locationButton);

			locationButton.setPrefSize(200, 50);
			locationButton.setAlignment(Pos.CENTER);
			locationsBox.getChildren().add(locationButton);

			locationButton.setOnAction(e -> {
				int index = buttonsArray.indexOf(locationButton);
				Pair<String, int[]> buttonInfo = gridInfoArray.get(index);

				setLocation(buttonInfo.getKey(), buttonInfo.getValue()[0], buttonInfo.getValue()[1]);

				NavigationBar navBar = new NavigationBar(mainStage);
				BorderPane homeScreen = HomeScreen.getScreen();
				homeScreen.setBottom(navBar.getNavigationBar());

				Scene homeScene = new Scene(homeScreen, 360, 640);
				mainStage.setScene(homeScene);

				dialogStage.close();
			});
		}

		ScrollPane locationScroll = new ScrollPane(locationsBox);
		locationScroll.setPrefHeight(225);

		return locationScroll;
	}
}
