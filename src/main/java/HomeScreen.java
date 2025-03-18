import hourlyWeather.HourlyPeriod;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import weather.Period;
import weather.WeatherAPI;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HomeScreen extends SceneBuilder{
	private static ArrayList<HourlyPeriod> hourlyForecast;

	private static BorderPane prevHourBox; //static global variable used for lambda method which requires 'final' variables
	static DropShadow dropShadow = new DropShadow();

	public static BorderPane getScreen(){
		Period currentForecast = WeatherAPI.getForecast(region, gridX, gridY).get(0);
		hourlyForecast = MyWeatherAPI.getHourlyForecast(region,gridX,gridY);
//		double[] minAndMax = MyWeatherAPI.getMinAndMaxTemperature(region, gridX, gridY).get(0);
		if (hourlyForecast == null){
			System.out.println("No forecast found");
		}

		//Home screen is split into two main components: the top half and bottom half.
		//---elements for homeBoxOne (top portion of screen)
		Button locationButton = getLocationButton();

		Label temperatureLabel = new Label(hourlyForecast.get(0).temperature + "°");
		Label weatherLabel = new Label(hourlyForecast.get(0).shortForecast);
		weatherLabel.setTextFill(Color.web("#FCFFCB"));
		weatherLabel.setFont(Font.font("Verdana", 14));

		temperatureLabel.setFont(Font.font("Verdana", FontWeight.BOLD,50));
		temperatureLabel.setTextFill(Color.rgb(255,255,255));
		temperatureLabel.setEffect(dropShadow);

		HBox homeBoxOneTop = new HBox(locationButton); //Placed at the very top of the screen
		homeBoxOneTop.setAlignment(Pos.CENTER);

		VBox homeBoxOneCenter = new VBox(temperatureLabel, weatherLabel); //Root for the top portion of the screen
		homeBoxOneCenter.setAlignment(Pos.CENTER);

		//--putting all the elements together for homeBoxOne (top portion of the screen)
		BorderPane homeBoxOne = new BorderPane(homeBoxOneCenter);
		homeBoxOne.setPrefHeight(150);
		homeBoxOne.setTop(homeBoxOneTop);

		//middle of homeboxone and two
		Label descriptionText = new Label(currentForecast.detailedForecast);
		descriptionText.setWrapText(true);
		descriptionText.setFont(Font.font("Verdana", FontWeight.MEDIUM,12));
		descriptionText.setTextFill(Color.rgb(255,255,255));
		descriptionText.setEffect(dropShadow);

		BorderPane descriptionBox = new BorderPane(descriptionText);
		descriptionBox.setPrefSize(300, 70);
		descriptionBox.setId("homeBox");
		descriptionBox.setPadding(new Insets(10));

		//---elements for homeBoxTwo (lower portion of screen)
		//The bottom half of the screen is split into a left and right component

		ScrollPane hourlyForecastScroll = getHourlyScroll();

		//---elements in homeBoxTwoLeft (left side of homeBoxTwo)
		Label todayWeatherText = new Label("Today's Weather");
		todayWeatherText.setFont(Font.font("Verdana",FontWeight.BOLD, 16));
		todayWeatherText.setTextFill(Color.rgb(255,255,255));
		todayWeatherText.setPrefSize(220,18);
		todayWeatherText.setEffect(dropShadow);

		//--putting all the elements together for the left part of the bottom of the screen
		VBox homeBoxTwoLeft = new VBox(todayWeatherText, hourlyForecastScroll);

		//---elements in homeBoxTwoRight (right side of homeBoxTwo)
		TextField alertsText = new TextField("Alerts");
		alertsText.setPrefSize(100, 200);
		alertsText.setEditable(false);
		alertsText.setAlignment(Pos.TOP_LEFT);
		alertsText.setId("homeBox");

		TextField image = new TextField("image");
		image.setPrefSize(100, 100);

		VBox homeBoxTwoRight = new VBox(10, alertsText, image);

		//--putting all elements together for homeBoxTwo (bottom portion of screen)
		HBox homeBoxTwo = new HBox(10, homeBoxTwoLeft, homeBoxTwoRight);
		homeBoxTwo.setAlignment(Pos.CENTER);

		//--putting all elements together in BorderPane root (whole screen)
		VBox centerRoot = new VBox(10, homeBoxOne, descriptionBox);

		BorderPane root = new BorderPane(centerRoot);
		root.setPadding(new Insets(10, 10, 0, 10));
		root.setBottom(homeBoxTwo); //aligns homeBoxTwo to the bottom of the screen

		Image homeBackground = new Image("/images/matcha-background.jpg", 360, 640, false, true);
		BackgroundImage backgroundImage = new BackgroundImage(homeBackground, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null, null);
		root.setBackground(new Background(backgroundImage));

		return new BorderPane(root);
	}

	//Creates dialog window to prompt user for location
	private static Button getLocationButton(){
		Button locationButton = new Button(getLocation());
		locationButton.setPrefSize(150,40);
		locationButton.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 14));

		locationButton.setOnAction(e -> {

			Stage locationDialog = new Stage();

			LocationDetails locationScreen = new LocationDetails(locationDialog);
			BorderPane root = LocationDetails.getScreen();

			Scene nextScene = new Scene(root, 315, 560);

			locationDialog.setScene(nextScene);
			locationDialog.setTitle("LocationDetails");
			locationDialog.setResizable(false);

			locationDialog.initOwner(stage);
			locationDialog.initModality(Modality.WINDOW_MODAL); //prevents user interaction with main stage until the dialog window has closed
			locationDialog.showAndWait();
		});

		return locationButton;
	}

	private static ScrollPane getHourlyScroll(){
		VBox hourlyForecastBox = new VBox();
		ScrollPane hourlyForecastScroll = new ScrollPane(hourlyForecastBox); //This ScrollPane allows the user to scroll through the VBox
		hourlyForecastScroll.setPrefSize(220, 300);
		hourlyForecastScroll.setId("shelfScroll");

		ArrayList<HBox> moreInfoBoxes = new ArrayList<>();
		ArrayList<BorderPane> hourBoxes = new ArrayList<>();

		for(int i = 0; i <= 24; i++){
			HourlyPeriod currentPeriod = hourlyForecast.get(i);

			Date currentHour = currentPeriod.startTime;
			SimpleDateFormat localDateFormat = new SimpleDateFormat("hh a");
			String hourTime = localDateFormat.format(currentHour);

			Label time = new Label(hourTime);
			time.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 15));
			time.setTextFill(Color.rgb(255,255,255));
			time.setEffect(dropShadow);

			Label temp = new Label(String.valueOf(currentPeriod.temperature) + "°");
			temp.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
			temp.setTextFill(Color.rgb(255,255,255));
			temp.setEffect(dropShadow);

			Label rainChance = new Label(currentPeriod.probabilityOfPrecipitation.value + "%");
			rainChance.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
			rainChance.setTextFill(Color.web("4B62F3FF"));
			rainChance.setEffect(dropShadow);

			HBox statusBox = new HBox(30, time, temp, rainChance);
			statusBox.setAlignment(Pos.CENTER);

			Label wind = new Label("Wind: " + currentPeriod.windSpeed + " " + currentPeriod.windDirection);
			wind.setFont(Font.font("Verdana", 9));
			wind.setTextFill(Color.rgb(255,255,255));

			Label humidity = new Label("Humidity: " + currentPeriod.relativeHumidity.value + "%");
			humidity.setFont(Font.font("Verdana", 9));
			humidity.setTextFill(Color.rgb(255,255,255));

			HBox expandedStatusBox = new HBox(20, wind, humidity);
			expandedStatusBox.setAlignment(Pos.CENTER);
			expandedStatusBox.setVisible(false);
			moreInfoBoxes.add(expandedStatusBox);

			BorderPane currHourBox = new BorderPane();
			currHourBox.setCenter(statusBox);
			currHourBox.setBottom(expandedStatusBox);
			currHourBox.setPadding(new Insets(6));
			currHourBox.setPrefSize(204,50);

			if(i == 0){
				currHourBox.setId("shelfTop");
			}
			else if(i == 24){
				currHourBox.setId("shelfBottom");
			}
			else{
				currHourBox.setId("shelfMiddle");
			}

			hourBoxes.add(currHourBox);
			hourlyForecastBox.getChildren().add(currHourBox);

			prevHourBox = currHourBox;
			currHourBox.setOnMouseClicked(e -> {
				int index = hourBoxes.indexOf(currHourBox);
				int prevIndex = hourBoxes.indexOf(prevHourBox);

				if(!moreInfoBoxes.get(index).isVisible()){
					moreInfoBoxes.get(index).setVisible(true);

					if(currHourBox != prevHourBox){
						moreInfoBoxes.get(prevIndex).setVisible(false);
					}

					prevHourBox = currHourBox;
				}
				else{
					moreInfoBoxes.get(index).setVisible(false);
				}
			});
		}

		hourlyForecastScroll.setStyle("-fx-background-color: transparent");

		return hourlyForecastScroll;
	}
}
