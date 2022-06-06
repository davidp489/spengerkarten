import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main_Page_L extends Application{

	public static void main(String[] args) 
	{
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception 
	{
		MenuBar menuBar = new MenuBar();
		Menu home = new Menu("Home");
		Menu New = new Menu("New");
		Menu load = new Menu("Load");
		menuBar.getMenus().addAll(home, New, load);
		
		VBox root = new VBox(menuBar);
		ScrollPane scrollPane = new ScrollPane();
		GridPane gridPane = new GridPane();
		scrollPane.setContent(gridPane);
		BorderPane pane = new BorderPane();
		pane.setTop(root);
		pane.setCenter(scrollPane);
		
		Scene scene = new Scene(pane, 1920, 1080);
		stage.setScene(scene);
		stage.show();
		testGridPane(gridPane);
	}

	// noch nonsense, nur zum testen
	public void testGridPane(GridPane pane) {
		pane.addRow(1);
		pane.addRow(2);
		pane.addColumn(1);
		pane.addColumn(2);
		
		pane.add(new ListView<String>(), 0, 0);
		pane.add(new ListView<String>(), 1, 1);
		pane.add(new ListView<String>(), 2, 2);
	}
}







