import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SceneBuilder {
	public static Stage mainStage;

	public static String city;
	public static String state;
	public static double longitude;
	public static double latitude;
	public static String region;
	public static int gridX;
	public static int gridY;

	public SceneBuilder(){
		city = "Chicago";
		state = "Illinois";
		region = "LOT";
		gridX = 77;
		gridY = 70;
	}

	public static void setLocation(String region, int gridX, int gridY){
		SceneBuilder.region = region;
		SceneBuilder.gridX = gridX;
		SceneBuilder.gridY = gridY;
	}

	public String getPlace(){
		return city + "," + state;
	}

	public void setStage(Stage stage) {
		mainStage = stage;
	}

	public Stage getStage() {
		return mainStage;
	}

}
