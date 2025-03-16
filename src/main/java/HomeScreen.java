import hourlyWeather.HourlyPeriod;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import weather.Period;
import weather.WeatherAPI;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HomeScreen extends SceneBuilder{
	private static Period currentForecast;
	private static ArrayList<HourlyPeriod> hourlyForecast;

	private static Button hourlyStatusExpanded; //static global variable used for lambda method which requires 'final' variables

	public static BorderPane getScreen(){
		//int temp = WeatherAPI.getTodaysTemperature(77,70);
		currentForecast = WeatherAPI.getForecast(region, gridX, gridY).get(0);
		hourlyForecast = MyWeatherAPI.getHourlyForecast(region,gridX,gridY);
		if (hourlyForecast == null){
			System.out.println("No forecast found");
		}

		//Home screen is split into two main components: the top half and bottom half.
		BorderPane homeBoxOne;
		HBox homeBoxTwo;

		//---elements for homeBoxOne (top portion of screen)
		HBox homeBoxOneTop; //Placed at the very top of the screen
		VBox homeBoxOneCenter; //Root for the top portion of the screen

		Button locationButton = getLocationButton();

		Label temperatureLabel = new Label();
		Label weatherLabel = new Label();
		temperatureLabel.setText(String.valueOf(hourlyForecast.get(0).temperature));
		temperatureLabel.setFont(new Font(50));
		weatherLabel.setText(hourlyForecast.get(0).shortForecast);

		homeBoxOneTop = new HBox(locationButton);
		homeBoxOneTop.setAlignment(Pos.CENTER);

		homeBoxOneCenter = new VBox(temperatureLabel, weatherLabel);
		homeBoxOneCenter.setAlignment(Pos.CENTER);

		//--putting all the elements together for homeBoxOne (top portion of the screen)
		homeBoxOne = new BorderPane(homeBoxOneCenter);
		homeBoxOne.setPrefHeight(300);
		homeBoxOne.setTop(homeBoxOneTop);

//		homeBoxOne.setBorder(new Border(new BorderStroke(Color.BLACK,
//				BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

		//---elements for homeBoxTwo (lower portion of screen)
		VBox homeBoxTwoLeft, homeBoxTwoRight; //The bottom half of the screen is split into a left and right component

		ScrollPane hourlyForecastScroll = getHourlyScroll();

		//---elements in homeBoxTwoLeft (left side of homeBoxTwo)
		TextField todayWeatherText;
		TextArea descriptionText;

		todayWeatherText = new TextField("Today's Weather");
		todayWeatherText.setEditable(false);
		todayWeatherText.setFont(Font.font("Verdana", 14));
		todayWeatherText.setMaxSize(204,18);

		descriptionText = new TextArea(currentForecast.detailedForecast);
		descriptionText.setEditable(false);
		descriptionText.setMaxSize(204, 70);
		descriptionText.setWrapText(true);
		descriptionText.setFont(Font.font("Verdana", 10));

		//--putting all the elements together for the left part of the bottom of the screen
		homeBoxTwoLeft = new VBox(todayWeatherText, descriptionText, hourlyForecastScroll);

		//---elements in homeBoxTwoRight (right side of homeBoxTwo)
		TextField alertsText, image;

		alertsText = new TextField("Alerts");
		alertsText.setPrefSize(100, 200);
		alertsText.setEditable(false);
		alertsText.setAlignment(Pos.TOP_LEFT);

		image = new TextField("image");
		image.setPrefSize(100, 100);

		homeBoxTwoRight = new VBox(10, alertsText, image);

		//--putting all elements together for homeBoxTwo (bottom portion of screen)
		homeBoxTwo = new HBox(10, homeBoxTwoLeft, homeBoxTwoRight);
		homeBoxTwo.setAlignment(Pos.CENTER);

		//--putting all elements together in BorderPane root (whole screen)
		VBox centerRoot = new VBox(homeBoxOne);

		BorderPane root = new BorderPane(centerRoot);
		root.setPadding(new Insets(10));
		root.setBottom(homeBoxTwo); //aligns homeBoxTwo to the bottom of the screen

		return new BorderPane(root);
	}

	//Creates dialog window to prompt user for location
	private static Button getLocationButton(){
		Button locationButton = new Button(getLocation());

		locationButton.setOnAction(e -> {

			Stage locationDialog = new Stage();

			LocationDetails locationScreen = new LocationDetails(locationDialog);
			BorderPane root = LocationDetails.getScreen();

			Scene nextScene = new Scene(root, 315, 560);

			locationDialog.setScene(nextScene);
			locationDialog.setTitle("LocationDetails");
			locationDialog.setResizable(false);

			locationDialog.initOwner(mainStage);
			locationDialog.initModality(Modality.WINDOW_MODAL); //prevents user interaction with main stage until the dialog window has closed
			locationDialog.showAndWait();
		});

		return locationButton;
	}

	private static ScrollPane getHourlyScroll(){
		VBox hourlyForecastBox = new VBox();
		ScrollPane hourlyForecastScroll = new ScrollPane(hourlyForecastBox); //This ScrollPane allows the user to scroll through the VBox
		hourlyForecastScroll.setPrefSize(220, 300);

		ArrayList<Button> hourlyButtons = new ArrayList<>();
		ArrayList<String> hourlyLabels = new ArrayList<>();
		ArrayList<String> hourlyExtendedLabels = new ArrayList<>();

		for(int i = 1; i <= 24; i++){ //Creates 24 buttons to display the hourly forecast
			//Converts Date object into "hh a" (hour am/pm) format
			HourlyPeriod currentPeriod = hourlyForecast.get(i);
			Date currentHour = currentPeriod.startTime;
			SimpleDateFormat localDateFormat = new SimpleDateFormat("hh a");
			String hourTime = localDateFormat.format(currentHour);

			String textBody = hourTime + " | " + currentPeriod.temperature + "° | " + currentPeriod.probabilityOfPrecipitation.value + "%";
			String extendedTextBody = textBody + "\n" + currentPeriod.windSpeed + " " + currentPeriod.windDirection;

			Button hourlyStatusButton = new Button(textBody);
			hourlyStatusButton.setPrefSize(204, 50);
			hourlyStatusButton.setAlignment(Pos.TOP_LEFT);

			hourlyForecastBox.getChildren().add(hourlyStatusButton);

			hourlyButtons.add(hourlyStatusButton);
			hourlyLabels.add(textBody);
			hourlyExtendedLabels.add(extendedTextBody);

			//Whenever the user clicks an hourly status button, it is enlarged with additional information.
			//The previous hourly status button that was expanded will be returned to its original state.
			hourlyStatusExpanded = hourlyStatusButton;
			hourlyStatusButton.setOnAction(e-> {
				int index = hourlyButtons.indexOf(hourlyStatusButton);
				int prevIndex = hourlyButtons.indexOf(hourlyStatusExpanded);
				if (hourlyStatusButton.getHeight() == 50){
					hourlyStatusButton.setPrefHeight(100);
					hourlyStatusButton.setText(hourlyExtendedLabels.get(index));

					if(hourlyStatusButton != hourlyStatusExpanded){
						hourlyStatusExpanded.setPrefHeight(50);
						hourlyStatusExpanded.setText(hourlyLabels.get(prevIndex));
					}
					hourlyStatusExpanded = (Button) e.getSource();
				}
				else{
					hourlyStatusButton.setPrefHeight(50);
					hourlyStatusButton.setText(hourlyLabels.get(index));
				}
			});
		}

		return hourlyForecastScroll;
	}
}
