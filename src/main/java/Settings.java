import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Arrays;
import java.util.List;

public class Settings extends SceneBuilder{
	private static VBox root;
	private static DropShadow dropShadow = new DropShadow();
	private static BorderPane settingBoxThemes, settingBoxTemperature, settingBoxHour;

	public static Scene getScene(){
		BorderPane root = getRoot();
		BorderPane rootPane = new BorderPane(root);
		rootPane.setBottom(NavigationBar.getNavigationBar());

		Scene scene = new Scene(rootPane, 360, 640);
		scene.getStylesheets().add(SceneBuilder.class.getResource("/css/style.css").toExternalForm());

		return scene;
	}

	public static BorderPane getRoot() {
		Label settingsLabel = new Label("Settings");
		settingsLabel.setTextFill(Color.WHITE);
		settingsLabel.setFont(Font.font("Inter", FontWeight.BOLD ,40));
		settingsLabel.setPadding(new Insets(20,20,20,20));
		settingsLabel.setEffect(dropShadow);

		Label themesText = new Label("Themes");
		Label temperatureText = new Label("Temperature");
		Label chooseHourText = new Label("24hr/12hr");

		// Creates options for dropdowns
		List<String> themesOptions = Arrays.asList("Matcha", "Cocoa", "Milk", "Ube");
		List<String> temperatureOptions = Arrays.asList("Fahrenheit", "Celsius");
		List<String> hourOptions = Arrays.asList("12hr", "24hr");

		ComboBox<String> themesDropdown = new ComboBox(FXCollections.observableArrayList(themesOptions));
		themesDropdown.getSelectionModel().select(themesOptions.indexOf(theme));
		themesDropdown.setOnAction(e->{
			String theme = themesDropdown.getValue();
			switch(theme){
				case "Matcha":
					SceneBuilder.setTheme("Matcha");
					SceneBuilder.setBackgroundImage("/images/backgrounds/plant_wallpaper.jpg");
					updateBoxThemes("#8A9D6C");
					root.setBackground(new Background(backgroundImage));
					break;
				case "Cocoa":
					SceneBuilder.setTheme("Cocoa");
					SceneBuilder.setBackgroundImage("/images/backgrounds/brown_background.jpg");
					updateBoxThemes(" #a47148");
					root.setBackground(new Background(backgroundImage));
					break;
				case "Milk":
					SceneBuilder.setTheme("Milk");
					SceneBuilder.setBackgroundImage("/images/backgrounds/cream_background.jpg");
					updateBoxThemes("#d5bdaf");
					root.setBackground(new Background(backgroundImage));
					break;
				case "Ube":
					SceneBuilder.setTheme("Ube");
					SceneBuilder.setBackgroundImage("/images/backgrounds/purple_background.jpg");
					updateBoxThemes("#9d4edd");
					root.setBackground(new Background(backgroundImage));
					break;
			}
		});

		ComboBox<String> temperatureDropdown = new ComboBox(FXCollections.observableArrayList(temperatureOptions));
		temperatureDropdown.getSelectionModel().select(temperatureOptions.indexOf(temperatureUnit));
		temperatureDropdown.setOnAction(e->{
			String unit = temperatureDropdown.getValue();
			SceneBuilder.temperatureUnit = unit;
		});

		ComboBox<String> hourDropdown = new ComboBox(FXCollections.observableArrayList(hourOptions));
		hourDropdown.getSelectionModel().select(hourOptions.indexOf(timeFormat));
		hourDropdown.setOnAction(e->{
			String unit = hourDropdown.getValue();
			SceneBuilder.timeFormat = unit;
		});

		settingBoxThemes = createSettingsBox(themesText, themesDropdown);
		settingBoxTemperature = createSettingsBox(temperatureText, temperatureDropdown);
		settingBoxHour = createSettingsBox(chooseHourText, hourDropdown);


		//Putting all the elements together
		VBox settingBoxTop = new VBox(4, settingsLabel, settingBoxThemes, settingBoxTemperature, settingBoxHour);
		settingBoxTop.setAlignment(Pos.CENTER);

		root = new VBox(20, settingBoxTop);
		root.setBackground(new Background(backgroundImage));

		BorderPane borderPane = new BorderPane(root);
		return borderPane;
	}

	//creating and designing each settings box
	private static BorderPane createSettingsBox(Label text, ComboBox dropdown){
		text.setFont(new Font("Inter", 20));
		text.setTextFill(Color.WHITE);
		text.setEffect(dropShadow);
		dropdown.setPrefWidth(100);

		BorderPane settingPanel = new BorderPane();
		settingPanel.setLeft(text);
		settingPanel.setRight(dropdown);

		settingPanel.setPadding(new Insets(5));
		settingPanel.setBorder(Border.stroke(Color.BLACK));
		settingPanel.setMaxWidth(340);
		settingPanel.setOpacity(0.9);

		switch(theme){
			case "Matcha":
				settingPanel.setStyle("-fx-background-color: #8A9D6C;");
				break;
			case "Cocoa":
				settingPanel.setStyle("-fx-background-color: #a47148;");
				break;
			case "Milk":
				settingPanel.setStyle("-fx-background-color: #d5bdaf;");
				break;
			case "Ube":
				settingPanel.setStyle("-fx-background-color: #9d4edd;");
				break;
		}
		return settingPanel;
	}

	private static void updateBoxThemes(String color){
		settingBoxThemes.setStyle("-fx-background-color: " + color + ";");
		settingBoxTemperature.setStyle("-fx-background-color: " + color + ";");
		settingBoxHour.setStyle("-fx-background-color: " + color + ";");
	}
}
