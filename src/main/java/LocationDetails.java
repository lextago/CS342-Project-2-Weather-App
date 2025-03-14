import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LocationDetails extends SceneBuilder{
	public static BorderPane getScene() {
		Button back = new Button("Back");

		back.setOnAction(e -> {
			BorderPane root = HomeScreen.getScene();

			Scene nextScene = new Scene(root, 360, 640);
			mainStage.setScene(nextScene);
		});

		TextField location = new TextField("Location");

		VBox root = new VBox(back, location);

		return new BorderPane(root);
	}
}
