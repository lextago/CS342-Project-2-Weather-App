import javafx.scene.layout.*;

//This class builds each screen of the weather app inside a function. Each function
//returns the screen as a BorderPane object to be used as the root for a Scene.
interface SceneBuilder {

	public BorderPane getScreen();
}
