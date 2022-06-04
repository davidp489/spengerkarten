import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class New_Quiz_C extends Application{

	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		GridPane pane = new GridPane();
		ListView<String> listView1 = new ListView<String>();
		TextField txt1 = new TextField();
		TextField txt2 = new TextField();
		listView1.getItems().add("Cool");
		
		
		pane.add(txt1, 3, 2);
		pane.add(txt2, 3, 4);
		pane.add(listView1, 8, 3);
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.show();
	}
}