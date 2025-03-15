import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

	public LocationDetails(Stage stage) {
		dialogStage = stage;
	}

	public static BorderPane getScreen() {
		Button back = new Button("Back");

		Button info = new Button("Info");

		back.setOnAction(e -> {
			dialogStage.close();
		});

		TextField location = new TextField("Location");

		Label cityLabel = new Label("City:");
		Label stateLabel = new Label("State:");
		Label countryLabel = new Label("Country:");
		Label coordsLabel = new Label("Coordinates (ISO 8601 format):");

		TextField cityInput = new TextField();
		TextField stateInput = new TextField();
		TextField countryInput = new TextField();
		TextField coordsInput = new TextField();

		HBox cityBox = createDetailsBox(cityLabel, cityInput);
		HBox stateBox = createDetailsBox(stateLabel, stateInput);
		HBox countryBox = createDetailsBox(countryLabel, countryInput);
		HBox coordsBox = createDetailsBox(coordsLabel, coordsInput);

		if(System.getenv("MAPBOX_API_KEY") == null){
			cityInput.setEditable(false);
			stateInput.setEditable(false);
			countryInput.setEditable(false);
		}

		Button submit = new Button("Submit");

		submit.setOnAction(e -> {
			locationDetails[0] = cityInput.getText();
			locationDetails[1] = stateInput.getText();
			locationDetails[2] = countryInput.getText();
			locationDetails[3] = coordsInput.getText();

			ArrayList<Pair<Double[], String>> coordsArray = MyWeatherAPI.getCoords(locationDetails[0], locationDetails[1], locationDetails[2]);
			for(Pair<Double[], String> pair : coordsArray){
				System.out.println(pair.getValue() + " " + Arrays.toString(pair.getKey()));
			}
		});

		TextField results = new TextField();

		VBox root = new VBox(30, back, info, location, cityBox, stateBox, countryBox, coordsBox, submit, results);

		return new BorderPane(root);
	}

	private static HBox createDetailsBox(Label detailLabel, TextField detailInput){
		BorderPane detailPane = new BorderPane();
		detailPane.setLeft(detailLabel);
		detailPane.setRight(detailInput);

		detailPane.setPadding(new Insets(5));
		detailPane.setBorder(Border.stroke(Color.BLACK));
		detailPane.setStyle("-fx-background-color: #FFFFFF;");
		detailPane.setPrefWidth(340);

		HBox detailBox = new HBox(detailPane);
		detailBox.setAlignment(Pos.CENTER);

		return detailBox;
	}
}
