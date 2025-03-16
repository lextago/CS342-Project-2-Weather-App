import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import weather.Period;
import weather.WeatherAPI;

public class DailyForecast extends SceneBuilder{
	public static BorderPane getScene(){
		VBox root = new VBox();
		VBox days = new VBox();

		//Period currentForecast = WeatherAPI.getForecast("LOT", 77, 70).get(0);

		// Creates all text

		Label dailyForecast = new Label("Daily Forecast");
		dailyForecast.setAlignment(Pos.CENTER);
		dailyForecast.setTextFill(Color.rgb(255,255,255));
		dailyForecast.setFont(new Font("Inter", 40));
		dailyForecast.setPadding(new Insets(20,20,20,20));

		TextField sevenDays = new TextField("7 Days");
		sevenDays.setPrefSize(25,25);

		TextField dayOne = new TextField("Day One");
		dayOne.setPrefSize(60,60);

		TextField dayTwo = new TextField("Day Two");
		dayTwo.setPrefSize(60,60);

		TextField dayThree = new TextField("Day Three");
		dayThree.setPrefSize(60,60);

		TextField dayFour = new TextField("Day Four");
		dayFour.setPrefSize(60,60);

		TextField dayFive = new TextField("Day Five");
		dayFive.setPrefSize(60,60);

		TextField daySix = new TextField("Day Six");
		daySix.setPrefSize(60,60);

		TextField daySeven = new TextField("Day Seven");
		daySeven.setPrefSize(60,60);

		days = new VBox(sevenDays, dayOne, dayTwo, dayThree, dayFour, dayFive, daySix, daySeven);
		root = new VBox(dailyForecast, days);
		root.setSpacing(60);

		return new BorderPane(root);
	}
}
