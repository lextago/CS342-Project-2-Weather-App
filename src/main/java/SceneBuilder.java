import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SceneBuilder {
	public static Stage mainStage;

	public static String city;
	public static String state;
	public static String location;
	public static double longitude;
	public static double latitude;
	public static String region;
	public static int gridX;
	public static int gridY;

	public SceneBuilder(){
		city = "Chicago";
		state = "Illinois";
		location = "Chicago, Illinois";
		region = "LOT";
		gridX = 77;
		gridY = 70;
	}

	public static void setLocation(String region, int gridX, int gridY){
		SceneBuilder.region = region;
		SceneBuilder.gridX = gridX;
		SceneBuilder.gridY = gridY;
	}

	public String getLocation(){
		return location;
	}

	public void setStage(Stage stage) {
		mainStage = stage;
	}

	public Stage getStage() {
		return mainStage;
	}

}
