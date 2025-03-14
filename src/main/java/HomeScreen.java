import hourlyWeather.HourlyPeriod;
import hourlyWeather.HourlyProbabilityOfPrecipitation;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import weather.Period;
import weather.WeatherAPI;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HomeScreen extends SceneBuilder{
	static Button hourlyStatusExpanded; //static global variable used for lambda method which requires 'final' variables

	public static BorderPane getScene(){
		//int temp = WeatherAPI.getTodaysTemperature(77,70);
		Period currentForecast = WeatherAPI.getForecast("LOT", 77, 70).get(0);
		ArrayList<HourlyPeriod> hourlyForecast = MyWeatherAPI.getHourlyForecast("LOT",77,70);
		if (hourlyForecast == null){
			System.out.println("No forecast found");
		}

		//Home screen is split into two main components: the top half and bottom half.
		BorderPane homeBoxOne;
		HBox homeBoxTwo;

		//---elements for homeBoxOne (top portion of screen)
		HBox homeBoxOneTop; //Placed at the very top of the screen
		VBox homeBoxOneCenter; //Root for the top portion of the screen

		Button locationButton = new Button("Chicago IL");

		locationButton.setOnAction(e -> {
			BorderPane root = LocationDetails.getScene();
			Scene nextScene = new Scene(root, 360, 640);
			mainStage.setScene(nextScene);
			mainStage.setTitle("LocationDetails");
		});

		Label temperatureLabel = new Label();
		Label weatherLabel = new Label();
		temperatureLabel.setText(String.valueOf(hourlyForecast.get(0).temperature));
		temperatureLabel.setFont(new Font(50));
		weatherLabel.setText(hourlyForecast.get(0).shortForecast);

//		MyWeatherAPI api = new MyWeatherAPI();
//		System.out.println(Arrays.toString(api.getCoords()));

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
		VBox hourlyForecastBox = new VBox();

		ScrollPane hourlyForecastScroll = new ScrollPane(); //This ScrollPane allows the user to scroll through the VBox
		hourlyForecastScroll.setContent(hourlyForecastBox);
		hourlyForecastScroll.setPrefSize(220, 300);

		//---elements in homeBoxTwoLeft (left side of homeBoxTwo)
		TextField todayWeatherText;
		TextArea descriptionText;

		ArrayList<Button> hourlyButtons = new ArrayList<>();
		ArrayList<String> hourlyLabels = new ArrayList<>();
		ArrayList<String> hourlyExtendedLabels = new ArrayList<>();
		for(int i = 1; i <= 25; i++){
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
}
