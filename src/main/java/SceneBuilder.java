import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SceneBuilder {
	public static Stage mainStage;

	//{city, state, country, coordinates}
	public static String[] locationDetails = {"", "", "", ""};

	public void setStage(Stage stage) {
		mainStage = stage;
	}

	public Stage getStage() {
		return mainStage;
	}

}
