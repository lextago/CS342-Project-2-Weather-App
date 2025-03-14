import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Settings {
	public static BorderPane getScreen(){
		TextField settingsText = new TextField("Settings");
		settingsText.setEditable(false);
		TextField searchText = new TextField("Search");
		TextField textSizeText = new TextField("Text Size");
		TextField themesText = new TextField("Themes");
		TextField temperatureText = new TextField("Temperature");
		TextField timeZoneText = new TextField("Time Zone");
		TextField chooseHourText = new TextField("24hr/12hr");

		VBox settingBoxOne = new VBox(settingsText);
		VBox settingBoxTwo = new VBox(4, searchText, textSizeText, themesText, temperatureText);
		VBox settingBoxThree = new VBox(4, timeZoneText, chooseHourText);

		VBox root = new VBox(20, settingBoxOne, settingBoxTwo, settingBoxThree);

		return new BorderPane(root);
	}
}
