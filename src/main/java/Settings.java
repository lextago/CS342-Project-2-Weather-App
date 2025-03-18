import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Settings extends SceneBuilder{
	public static Scene getScene(){
		BorderPane root = getRoot();
		BorderPane rootPane = new BorderPane(root);
		rootPane.setBottom(NavigationBar.getNavigationBar());

		Scene scene = new Scene(rootPane, 360, 640);
		scene.getStylesheets().add(NavigationBar.class.getResource("/css/style.css").toExternalForm());

		return scene;
	}
	public static BorderPane getRoot() {
		// Creates all text

		Label settingsLabel = new Label("Settings");
		settingsLabel.setTextFill(Color.rgb(0,0,0));
		settingsLabel.setFont(new Font("Inter", 40));
		settingsLabel.setPadding(new Insets(20,20,20,20));

		Label sizeText = new Label("Text Size");
		Label themesText = new Label("Themes");
		Label temperatureText = new Label("Temperature");
		Label chooseHourText = new Label("24hr/12hr");

		// Creates options for dropdowns

		String[] sizeOptions = {"S", "M", "L"};
		String[] themesOptions = {"Matcha", "Cocoa", "Milk", "Coffee", "Ube"};
		String[] temperatureOptions = {"Fahrenheit", "Celsius"};
		String[] hourOptions = {"24hr", "12hr"};

		HBox settingBoxText = createSettingsBox(sizeText, sizeOptions);
		HBox settingBoxThemes = createSettingsBox(themesText, themesOptions);
		HBox settingBoxTemperature = createSettingsBox(temperatureText, temperatureOptions);
		HBox settingBoxHour = createSettingsBox(chooseHourText, hourOptions);

		// Displays VBox in order from top to bottom of the screen

		VBox settingBoxTop = new VBox(4, settingsLabel, settingBoxText, settingBoxThemes, settingBoxTemperature);
		VBox settingBoxBottom = new VBox(4, settingBoxHour);

		// Sets the background

		BackgroundFill background_fill = new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY);
		Background background = new Background(background_fill);

		Image matchaBackground = new Image("/images/matcha-background.jpg", 360, 640, false, true);
		BackgroundImage backgroundImage = new BackgroundImage(matchaBackground, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null, null);

		VBox root = new VBox(20, settingBoxTop, settingBoxBottom);

		root.setBackground(new Background(backgroundImage));

		BorderPane borderPane = new BorderPane(root);
		return borderPane;
	}

	private static HBox createSettingsBox(Label text, String[] options){
		text.setFont(new Font("Inter", 20));
		ComboBox dropdown = new ComboBox(FXCollections.observableArrayList(options));
		dropdown.setPrefWidth(100);

		BorderPane settingPanel = new BorderPane();
		settingPanel.setLeft(text);
		settingPanel.setRight(dropdown);

		settingPanel.setPadding(new Insets(5));
		settingPanel.setBorder(Border.stroke(Color.BLACK));
		settingPanel.setStyle("-fx-background-color: #FFFFFF;");
		settingPanel.setPrefWidth(340);

		HBox settingBox = new HBox(settingPanel);
		settingBox.setAlignment(Pos.CENTER);

		return settingBox;
	}
}
