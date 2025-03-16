import javafx.event.ActionEvent;
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

	private static ArrayList<Pair<String, int[]>> gridpoints;
	private static ArrayList<Pair<String, Double[]>> coordinates;
	private static VBox locationsBox;
	private static ScrollPane locationsScroll;
	private static BorderPane root;
	private static TextField cityInput, stateInput, longitudeInput, latitudeInput;
	private static Label results;

	public LocationDetails(Stage stage) {
		dialogStage = stage;
	}

	public static BorderPane getScreen() {
		//Building the upper half portion of the screen
		Button back = new Button("Back");
		Button info = new Button("Info");
		BorderPane locationTop = new BorderPane();
		locationTop.setLeft(back);
		locationTop.setRight(info);
		locationTop.setPrefHeight(40);

		//No changes are made when back is clicked
		back.setOnAction(e -> {
			dialogStage.close();
		});

		Label locationLabel = new Label("Enter City/State (MAPBOX_API_KEY required) :");
		Label cityLabel = new Label("City:");
		Label stateLabel = new Label("State:");
		Label coordsLabel = new Label("Coordinates (WGS 84/EPSG 4326 format) :");
		Label longitudeLabel = new Label("Longitude:");
		Label latitudeLabel = new Label("Latitude:");

		cityInput = new TextField();
		stateInput = new TextField();
		longitudeInput = new TextField();
		latitudeInput = new TextField();

		HBox cityBox = getDetailsBox(cityLabel, cityInput);
		HBox stateBox = getDetailsBox(stateLabel, stateInput);
		HBox latitudeBox = getDetailsBox(latitudeLabel, latitudeInput);
		HBox longitudeBox = getDetailsBox(longitudeLabel, longitudeInput);

		VBox placeBox = new VBox(5, locationLabel, cityBox, stateBox);
		VBox coordsBox = new VBox(5, coordsLabel, latitudeBox, longitudeBox);

		//MAPBOX_API_KEY is required to input a city/state
		if(System.getenv("MAPBOX_API_KEY") == null){
			cityInput.setEditable(false);
			stateInput.setEditable(false);
		}

		//Elements for the bottom half of the screen
		Button submitButton = new Button("Submit");
		HBox submitHBox = new HBox(submitButton);

		results = new Label("Results :");
		locationsScroll = new ScrollPane();
		locationsScroll.setPrefHeight(225);

		//Placing all the elements together
		VBox rootCenterBox = new VBox(10, placeBox, coordsBox);
		BorderPane rootCenter = new BorderPane(rootCenterBox);
		//putting the submit button in the bottom right corner of rootCenter
		rootCenter.setBottom(submitHBox);
		submitHBox.setAlignment(Pos.CENTER_RIGHT);

		locationsBox = new VBox(5, results, locationsScroll);
		root = new BorderPane(rootCenter);

		root.setTop(locationTop);
		root.setBottom(locationsBox);
		root.setPadding(new Insets(10));

		submitButton.setOnAction(LocationDetails::submitHandler);

		return new BorderPane(root);
	}

	//Creates the HBox for each input box
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

	/*  Handler for the submit button
		When the user clicks submit, two things happen:
		- If the user has inputted a city/state, then MyWeatherAPI.getCoords() retrieves the coordinates
		of that location. Multiple locations may share the same name, so multiple coordinates will be
		retrieved. MyWeatherAPI.getGridInfo() retrieves the grid information for each pair of
		coordinates.
		- If the user has inputted a pair of coordinates, then MyWeatherAPI.getGridInfo() retrieves the
		grid information of that location.

		If the user has inputted both a pair of coordinates AND a place, the information for both
		locations will be retrieved even if they are different locations.

		On default, if the user has inputted an invalid location, the results will display the
		information for the United States.
	*/
	private static void submitHandler(ActionEvent event){
		//resets the location
		city = "";
		state = "";
		latitude = 0.0;
		longitude = 0.0;
		Pair<String, int[]> gridInfo;
		gridpoints = new ArrayList<>();
		coordinates = new ArrayList<>();

		if(!cityInput.getText().isEmpty() || !stateInput.getText().isEmpty()){
			city = cityInput.getText();
			state = stateInput.getText();
			coordinates = MyWeatherAPI.getCoords(city, state); //Returns a Pair<String, Double[]> of (Address, Coordinates)

			for(Pair<String, Double[]> coordPair : coordinates){
				System.out.println(Arrays.toString(coordPair.getValue()) + " " + coordPair.getKey()); //for debugging purposes

				gridInfo = MyWeatherAPI.getGridInfo(coordPair.getValue()[0], coordPair.getValue()[1]); //Returns a Pair<String, int[]> of (gridId, gridX, gridY)
				if(gridInfo == null || gridInfo.getKey().isEmpty()){
					continue;
				}
				gridpoints.add(gridInfo);
			}
		}

		if(!latitudeInput.getText().isEmpty() && !longitudeInput.getText().isEmpty()){
			//Formats the lat/lon coords to follow the format for the NWS API service
			String latitudeText = String.format("%.4f", Double.parseDouble(latitudeInput.getText()));
			String longitudeText = String.format("%.4f", Double.parseDouble(longitudeInput.getText()));

			//yes... we converted a string -> double -> string -> double... only way to format a double without using another package
			latitude = Double.parseDouble(latitudeText);
			longitude = Double.parseDouble(longitudeText);

			gridInfo = MyWeatherAPI.getGridInfo(latitude, longitude);
			if (gridInfo != null && !gridInfo.getKey().isEmpty()){
				gridpoints.add(gridInfo);

				String location = latitude + "," + longitude;
				//since coordinates is an ArrayList of Pair<String, Double[]> objects, it's necessary to create an "address" for this coordinate
				Pair<String, Double[]> coords = new Pair<>(location, new Double[]{latitude, longitude});
				coordinates.addFirst(coords);
			}
		}

		//Once all the data has been retrieved, getLocationScroll() is called to display all the necessary outputs.
		locationsScroll = getLocationScroll();
		locationsBox = new VBox(5, results, locationsScroll);
		root.setBottom(locationsBox);
	}

	//Creates ScrollPane to display the results
	//Each result is displayed onto a button, where the user can scroll and select the location they desire
	private static ScrollPane getLocationScroll(){
		VBox locationsBox = new VBox();

		ArrayList<Button> buttonsArray = new ArrayList<>();
		for(int i = 0; i < gridpoints.size(); i++){
			Button locationButton = new Button(coordinates.get(i).getKey()); //The text for the button is the address of the location
			buttonsArray.add(locationButton);

			locationButton.setPrefSize(200, 50);
			locationButton.setAlignment(Pos.CENTER);
			locationsBox.getChildren().add(locationButton);

			locationButton.setOnAction(e -> {
				int index = buttonsArray.indexOf(locationButton);
				Pair<String, int[]> buttonInfo = gridpoints.get(index);

				//this sets the location for the whole app
				setLocation(coordinates.get(index).getKey());
				setGridpoint(buttonInfo.getKey(), buttonInfo.getValue()[0], buttonInfo.getValue()[1]);

				//prepares the home screen to send the user back
				NavigationBar navBar = new NavigationBar(mainStage);
				BorderPane homeScreen = HomeScreen.getScreen();
				homeScreen.setBottom(navBar.getNavigationBar());

				Scene homeScene = new Scene(homeScreen, 360, 640);
				mainStage.setScene(homeScene);

				//closes the dialog window
				dialogStage.close();
			});
		}

		ScrollPane locationScroll = new ScrollPane(locationsBox);
		locationScroll.setPrefHeight(225);

		return locationScroll;
	}
}
