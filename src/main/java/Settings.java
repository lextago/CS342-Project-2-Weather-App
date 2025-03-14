import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Settings extends SceneBuilder{
	public static BorderPane getScene() {
		// Creates all text

		Label settingsLabel = new Label("Settings");
		settingsLabel.setTextFill(Color.rgb(0,0,0));
		settingsLabel.setFont(new Font("Inter", 40));
		settingsLabel.setPadding(new Insets(20,20,20,20));

		Label textSizeText = new Label("Text Size");
		textSizeText.setFont(new Font("Inter", 20));

		Label themesText = new Label("Themes");
		themesText.setFont(new Font("Inter", 20));

		Label temperatureText = new Label("Temperature");
		temperatureText.setFont(new Font("Inter", 20));

		Label chooseHourText = new Label("24hr/12hr");
		chooseHourText.setFont(new Font("Inter", 20));

		// Creates all dropdowns

		String[] fontSize = {"S", "M", "L"};
		ComboBox fontSizeBox = new ComboBox(FXCollections.observableArrayList(fontSize));
		fontSizeBox.setPrefWidth(100);

		String[] allThemes = {"Matcha", "Cocoa", "Milk", "Coffee", "Ube"};
		ComboBox themesBox = new ComboBox(FXCollections.observableArrayList(allThemes));
		themesBox.setPrefWidth(100);
    
		String[] temperatureTypes = {"Fahrenheit", "Celsius"};
		ComboBox temperatureBox = new ComboBox(FXCollections.observableArrayList(temperatureTypes));
		temperatureBox.setPrefWidth(100);

		String[] timeZoneTypes = {"CST", "EST", "WST"};
		ComboBox timeZoneBox = new ComboBox(FXCollections.observableArrayList(timeZoneTypes));
		timeZoneBox.setPrefWidth(100);

		String[] hourTypes = {"24hr", "12hr", "6hr"};
		ComboBox hourBox = new ComboBox(FXCollections.observableArrayList(hourTypes));
		hourBox.setPrefWidth(100);

		// Creates "Text Size" panel

		BorderPane settingPanelText = new BorderPane();
		settingPanelText.setLeft(textSizeText);
		settingPanelText.setRight(fontSizeBox);

		settingPanelText.setPadding(new Insets(5));
		settingPanelText.setBorder(Border.stroke(Color.BLACK));
		settingPanelText.setStyle("-fx-background-color: #FFFFFF;");
		settingPanelText.setPrefWidth(340);

		HBox settingBoxText = new HBox(settingPanelText);
		settingBoxText.setAlignment(Pos.CENTER);

		// Creates "Themes" panel

		BorderPane settingPanelThemes = new BorderPane();
		settingPanelThemes.setLeft(themesText);
		settingPanelThemes.setRight(themesBox);

		settingPanelThemes.setPadding(new Insets(5));
		settingPanelThemes.setBorder(Border.stroke(Color.BLACK));
		settingPanelThemes.setStyle("-fx-background-color: #FFFFFF;");
		settingPanelThemes.setPrefWidth(340);

		HBox settingBoxThemes = new HBox(settingPanelThemes);
		settingBoxThemes.setAlignment(Pos.CENTER);

		// Creates "Temperature" panel

		BorderPane settingPanelTemperature = new BorderPane();
		settingPanelTemperature.setLeft(temperatureText);
		settingPanelTemperature.setRight(temperatureBox);

		settingPanelTemperature.setPadding(new Insets(5));
		settingPanelTemperature.setBorder(Border.stroke(Color.BLACK));
		settingPanelTemperature.setStyle("-fx-background-color: #FFFFFF;");
		settingPanelTemperature.setPrefWidth(340);

		HBox settingBoxTemperature = new HBox(settingPanelTemperature);
		settingBoxTemperature.setAlignment(Pos.CENTER);

		// Creates "24hr/12hr" panel

		BorderPane settingPanelHour = new BorderPane();
		settingPanelHour.setLeft(chooseHourText);
		settingPanelHour.setRight(hourBox);

		settingPanelHour.setPadding(new Insets(5));
		settingPanelHour.setBorder(Border.stroke(Color.BLACK));
		settingPanelHour.setStyle("-fx-background-color: #FFFFFF;");
		settingPanelHour.setPrefWidth(340);

		HBox settingBoxHour = new HBox(settingPanelHour);
		settingBoxHour.setAlignment(Pos.CENTER);

		// Displays VBox in order from top to bottom of the screen

		VBox settingBoxTop = new VBox(4, settingsLabel, settingBoxText, settingBoxThemes, settingBoxTemperature);
		VBox settingBoxBottom = new VBox(4, settingBoxHour);

		// Sets the background

		BackgroundFill background_fill = new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY);
		Background background = new Background(background_fill);

		VBox root = new VBox(20, settingBoxTop, settingBoxBottom);
		root.setBackground(background);

		BorderPane borderPane = new BorderPane(root);
		return borderPane;
	}
}
