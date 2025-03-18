import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class WeeklyTrends extends SceneBuilder {
	public static Scene getScene(){
		BorderPane root = getRoot();
		BorderPane rootPane = new BorderPane(root);
		rootPane.setBottom(NavigationBar.getNavigationBar());

		Scene scene = new Scene(rootPane, 360, 640);
		scene.getStylesheets().add(NavigationBar.class.getResource("/css/style.css").toExternalForm());

		return scene;
	}

	public static BorderPane getRoot(){
		TextField weeklyTrends = new TextField("Weekly Trends");
		VBox root = new VBox(weeklyTrends);

		return new BorderPane(root);
	}
}
