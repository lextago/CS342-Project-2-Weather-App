import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class DailyForecast extends SceneBuilder{
	public static BorderPane getScene(){
		VBox root = new VBox();
		VBox days = new VBox();
		TextField dailyForecast = new TextField("Daily Forecast");

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
