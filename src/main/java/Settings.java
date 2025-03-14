import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Settings {
	public static BorderPane getScreen() {
		Label settingsLabel = new Label("Settings");
		settingsLabel.setFont(new Font("Inter", 50));

		TextField searchText = new TextField("Search");

		TextField textSizeText = new TextField("Text Size");
		searchText.setEditable(false);

		TextField themesText = new TextField("Themes");
		themesText.setEditable(false);

		TextField temperatureText = new TextField("Temperature");
		temperatureText.setEditable(false);

		TextField timeZoneText = new TextField("Time Zone");
		timeZoneText.setEditable(false);

		TextField chooseHourText = new TextField("24hr/12hr");
		chooseHourText.setEditable(false);

		String weekDays[] = {"4px", "8px", "12px", "16px", "20px"};
		ComboBox weekDaysBox = new ComboBox(FXCollections.observableArrayList(weekDays));
		weekDaysBox.setPrefWidth(300);

		String allThemes[] = {"Matcha", "Cocoa", "Milk", "Coffee", "Ube"};
		ComboBox themesBox = new ComboBox(FXCollections.observableArrayList(allThemes));
		themesBox.setPrefWidth(300);

		String temperatureTypes[] = {"Fahrenheit", "Celsius"};
		ComboBox temperatureBox = new ComboBox(FXCollections.observableArrayList(temperatureTypes));
		temperatureBox.setPrefWidth(300);

		String timeZoneTypes[] = {"CST", "EST", "WST"};
		ComboBox timeZoneBox = new ComboBox(FXCollections.observableArrayList(timeZoneTypes));
		timeZoneBox.setPrefWidth(300);

		String hourTypes[] = {"24hr", "12hr", "6hr"};
		ComboBox hourBox = new ComboBox(FXCollections.observableArrayList(hourTypes));
		hourBox.setPrefWidth(300);

		HBox settingBoxOneHBox = new HBox(textSizeText, weekDaysBox);
		HBox settingBoxTwoHBox = new HBox(themesText, themesBox);
		HBox settingBoxThreeHBox = new HBox(temperatureText, temperatureBox);
		HBox settingBoxFourHBox = new HBox(timeZoneText, timeZoneBox);
		HBox settingBoxFiveHBox = new HBox(chooseHourText, hourBox);

		VBox settingBoxOne = new VBox(4, searchText);
		VBox settingBoxTwo = new VBox(4, settingBoxOneHBox, settingBoxTwoHBox, settingBoxThreeHBox);
		VBox settingBoxThree = new VBox(4, settingBoxFourHBox, settingBoxFiveHBox);

		BackgroundFill background_fill = new BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY);
		Background background = new Background(background_fill);

		VBox root = new VBox(20, settingsLabel, settingBoxOne, settingBoxTwo, settingBoxThree);
		root.setBackground(background);

		BorderPane borderPane = new BorderPane(root);
		return borderPane;
	}
}
