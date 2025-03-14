import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class WeeklyTrends {
	public static BorderPane getScreen(){
		TextField weeklyTrends = new TextField("Weekly Trends");
		VBox root = new VBox(weeklyTrends);

		return new BorderPane(root);
	}
}
