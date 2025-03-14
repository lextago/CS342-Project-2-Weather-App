import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Settings {
	public static BorderPane getScreen() {
		Label settingsLabel = new Label("Settings");
		settingsLabel.setTextFill(Color.rgb(0,0,0));
		settingsLabel.setFont(new Font("Inter", 40));
		settingsLabel.setPadding(new Insets(20,20,20,20));

		TextField searchText = new TextField("Search");
		searchText.setFont(new Font("Inter", 15));
		searchText.setBackground(new Background(new BackgroundFill(Color.grayRgb(233), CornerRadii.EMPTY, Insets.EMPTY)));

		Label textSizeText = new Label("Text Size");
		textSizeText.setFont(new Font("Inter", 20));

		Label themesText = new Label("Themes");
		themesText.setFont(new Font("Inter", 20));

		Label temperatureText = new Label("Temperature");
		temperatureText.setFont(new Font("Inter", 20));

		Label timeZoneText = new Label("Time Zone");
		timeZoneText.setFont(new Font("Inter", 20));

		Label chooseHourText = new Label("24hr/12hr");
		chooseHourText.setFont(new Font("Inter", 20));

		String fontSize[] = {"4px", "8px", "12px", "16px", "20px"};
		ComboBox fontSizeBox = new ComboBox(FXCollections.observableArrayList(fontSize));
		fontSizeBox.setPrefWidth(100);

		String allThemes[] = {"Matcha", "Cocoa", "Milk", "Coffee", "Ube"};
		ComboBox themesBox = new ComboBox(FXCollections.observableArrayList(allThemes));
		themesBox.setPrefWidth(100);

		String temperatureTypes[] = {"F", "C"};
		ComboBox temperatureBox = new ComboBox(FXCollections.observableArrayList(temperatureTypes));
		temperatureBox.setPrefWidth(100);

		String timeZoneTypes[] = {"CST", "EST", "WST"};
		ComboBox timeZoneBox = new ComboBox(FXCollections.observableArrayList(timeZoneTypes));
		timeZoneBox.setPrefWidth(100);

		String hourTypes[] = {"24hr", "12hr", "6hr"};
		ComboBox hourBox = new ComboBox(FXCollections.observableArrayList(hourTypes));
		hourBox.setPrefWidth(100);

		HBox searchTextHBoxTemp = new HBox();
		searchTextHBoxTemp.setStyle("-fx-background-color: #E9E9E9;");
		searchTextHBoxTemp.getChildren().addAll(searchText);
		searchTextHBoxTemp.setBorder(Border.stroke(Color.BLACK));
		BorderPane settingSearchPanel = new BorderPane(searchTextHBoxTemp);
		settingSearchPanel.setPadding(new Insets(5,20,20,25));
		HBox searchTextHBox = new HBox(settingSearchPanel);

		HBox settingBoxOneHBoxTemp = new HBox();
		settingBoxOneHBoxTemp.setPadding(new Insets(5, 5, 5, 5));
		settingBoxOneHBoxTemp.setStyle("-fx-background-color: #FFFFFF;");
		settingBoxOneHBoxTemp.getChildren().addAll(textSizeText, fontSizeBox);
		settingBoxOneHBoxTemp.setAlignment(Pos.CENTER);
		settingBoxOneHBoxTemp.setBorder(Border.stroke(Color.BLACK));
		settingBoxOneHBoxTemp.setSpacing(132);
		BorderPane settingPanelOne = new BorderPane(settingBoxOneHBoxTemp);
		HBox settingBoxOneHBox= new HBox(settingPanelOne);
		settingBoxOneHBox.setAlignment(Pos.CENTER);

		HBox settingBoxTwoHBoxTemp = new HBox();
		settingBoxTwoHBoxTemp.setPadding(new Insets(5, 5, 5, 5));
		settingBoxTwoHBoxTemp.setStyle("-fx-background-color: #FFFFFF;");
		settingBoxTwoHBoxTemp.getChildren().addAll(themesText, themesBox);
		settingBoxTwoHBoxTemp.setAlignment(Pos.CENTER);
		settingBoxTwoHBoxTemp.setBorder(Border.stroke(Color.BLACK));
		settingBoxTwoHBoxTemp.setSpacing(140);
		BorderPane settingPanelTwo = new BorderPane(settingBoxTwoHBoxTemp);
		HBox settingBoxTwoHBox= new HBox(settingPanelTwo);
		settingBoxTwoHBox.setAlignment(Pos.CENTER);

		HBox settingBoxThreeHBoxTemp = new HBox();
		settingBoxThreeHBoxTemp.setPadding(new Insets(5, 5, 5, 5));
		settingBoxThreeHBoxTemp.setStyle("-fx-background-color: #FFFFFF;");
		settingBoxThreeHBoxTemp.getChildren().addAll(temperatureText, temperatureBox);
		settingBoxThreeHBoxTemp.setAlignment(Pos.CENTER);
		settingBoxThreeHBoxTemp.setBorder(Border.stroke(Color.BLACK));
		settingBoxThreeHBoxTemp.setSpacing(100);
		BorderPane settingPanelThree = new BorderPane(settingBoxThreeHBoxTemp);
		HBox settingBoxThreeHBox = new HBox(settingPanelThree);
		settingBoxThreeHBox.setAlignment(Pos.CENTER);

		HBox settingBoxFourHBoxTemp = new HBox();
		settingBoxFourHBoxTemp.setPadding(new Insets(5, 5, 5, 5));
		settingBoxFourHBoxTemp.setStyle("-fx-background-color: #FFFFFF;");
		settingBoxFourHBoxTemp.getChildren().addAll(timeZoneText, timeZoneBox);
		settingBoxFourHBoxTemp.setAlignment(Pos.CENTER);
		settingBoxFourHBoxTemp.setBorder(Border.stroke(Color.BLACK));
		settingBoxFourHBoxTemp.setSpacing(118);
		BorderPane settingPanelFour = new BorderPane(settingBoxFourHBoxTemp);
		HBox settingBoxFourHBox = new HBox(settingPanelFour);
		settingBoxFourHBox.setAlignment(Pos.CENTER);

		HBox settingBoxFiveHBoxTemp = new HBox();
		settingBoxFiveHBoxTemp.setPadding(new Insets(5, 5, 5, 5));
		settingBoxFiveHBoxTemp.setStyle("-fx-background-color: #FFFFFF;");
		settingBoxFiveHBoxTemp.getChildren().addAll(chooseHourText, hourBox);
		settingBoxFiveHBoxTemp.setAlignment(Pos.CENTER);
		settingBoxFiveHBoxTemp.setBorder(Border.stroke(Color.BLACK));
		settingBoxFiveHBoxTemp.setSpacing(125);
		BorderPane settingPanelFive = new BorderPane(settingBoxFiveHBoxTemp);
		HBox settingBoxFiveHBox = new HBox(settingPanelFive);
		settingBoxFiveHBox.setAlignment(Pos.CENTER);

		VBox settingBoxOne = new VBox(4, settingsLabel, searchTextHBox);
		VBox settingBoxTwo = new VBox(4, settingBoxOneHBox, settingBoxTwoHBox, settingBoxThreeHBox);
		VBox settingBoxThree = new VBox(4, settingBoxFourHBox, settingBoxFiveHBox);

		BackgroundFill background_fill = new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY);
		Background background = new Background(background_fill);

		VBox root = new VBox(20, settingBoxOne, settingBoxTwo, settingBoxThree);
		root.setBackground(background);

		BorderPane borderPane = new BorderPane(root);
		return borderPane;
	}
}
