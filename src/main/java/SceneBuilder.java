import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.stage.Stage;

public class SceneBuilder {
	public static Stage stage;

	public static String location;
	public static double latitude;
	public static double longitude;
	public static String region;
	public static int gridX;
	public static int gridY;
	public static String theme;
	public static BackgroundImage backgroundImage;
	public static String temperatureUnit;
	public static String timeFormat;

	public SceneBuilder(){
		location = "Chicago, Illinois";
		latitude = 41.882;
		longitude = -87.6324;
		region = "LOT";
		gridX = 75;
		gridY = 73;
		theme = "Matcha";
		temperatureUnit = "Fahrenheit";
		timeFormat = "12hr";
		setBackgroundImage("/images/backgrounds/plant_wallpaper.jpg");
	}

	public static void setGridpoint(String region, int gridX, int gridY){
		SceneBuilder.region = region;
		SceneBuilder.gridX = gridX;
		SceneBuilder.gridY = gridY;
	}

	public static void setCoordinates(double latitude, double longitude){
		SceneBuilder.latitude = latitude;
		SceneBuilder.longitude = longitude;
	}

	public static void setLocation(String location) {
		SceneBuilder.location = location;
	}

	public static String getLocation(){
		return location;
	}

	public static void setBackgroundImage(String url){
		Image image = new Image(url, 360, 640, false, true);
		backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null, null);
	}

	public static void setTheme(String theme){
		SceneBuilder.theme = theme;
	}

	public void setStage(Stage stage) {
		SceneBuilder.stage = stage;
	}

	public static int convertFahrenheitToCelsius(int fahrenheit){
		return (fahrenheit - 32) * 5 / 9;
	}

	public static int convertCelsiusToFahrenheit(int celsius){
		return (celsius * 9 / 5) + 32;
	}

	public Stage getStage() {
		return stage;
	}

}
