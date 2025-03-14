import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SceneBuilder {
	public static Stage mainStage;

	public void setStage(Stage stage) {
		mainStage = stage;
	}

	public Stage getStage() {
		return mainStage;
	}

}
