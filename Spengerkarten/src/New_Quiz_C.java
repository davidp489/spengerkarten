import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;


public class New_Quiz_C extends Application{
	
	private int vocCounter = 0;

	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		BorderPane pane = new BorderPane(); //Ist der komplette Bildschirm
		VBox vBoxLeft = new VBox(); //VBox links
		VBox vBoxCenter = new VBox(); //VBox center
		VBox vBoxRight = new VBox(); //VBox rechts
		
		
		
		MenuBar menuBar = new MenuBar();
		Menu home = new Menu("Home");
		Menu load = new Menu("Load");
		menuBar.getMenus().addAll(home, load);
		ListView<String> listView = new ListView<String>();
		TextField txt1 = new TextField();
		TextField txt2 = new TextField();
		Button addButton = new Button("Add");
		Label vocNumber = new Label("Anzahl an Vokabeln: " + vocCounter);
		
		
		listView.getItems().add("Cool / Cool2");
		listView.getItems().add("Yeet / Yeet2");
		listView.getItems().add("123 / 1222");
		listView.getItems().add("1234 / Wort");
		listView.getItems().add("4453 / Wort2");
		listView.getItems().add("Cool / Cool2");
		listView.getItems().add("Yeet / Yeet2");
		listView.getItems().add("123 / 1222");
		listView.getItems().add("1234 / Wort");
		listView.getItems().add("4453 / Wort2");
		listView.getItems().add("Cool / Cool2");
		listView.getItems().add("Yeet / Yeet2");
		listView.getItems().add("123 / 1222");
		listView.getItems().add("1234 / Wort");
		listView.getItems().add("4453 / Wort2");
		listView.getItems().add("Cool / Cool2");
		listView.getItems().add("Yeet / Yeet2");
		listView.getItems().add("123 / 1222");
		listView.getItems().add("1234 / Wort");
		listView.getItems().add("4453 / Wort2");
		
		
		//Hintergrund Sachen
		addButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(txt1.getText().isBlank() || txt2.getText().isBlank())
				{
					System.out.println("true aber blank");
				}
				else
				{
					/*
					VBox pV1 = new VBox();
					
					Text tV = new Text("Eingabe ist nicht korrekt");
					Button bV = new Button("Ok");
					pV1.getChildren().addAll(tV, bV);
					pV1.setAlignment(Pos.CENTER);
					tV.setStyle("-fx-font-size: 20");
					
					Stage popUp = new Stage();
					popUp.setResizable(false);
					popUp.setTitle("Error");
					Scene error = new Scene(pV1, 200, 150);
					popUp.setScene(error);
					popUp.show();
					*/
					
					Popup pu = new Popup();
					Text tpu = new Text("Eingabe enthält Fehler");
					VBox vpu = new VBox();
					Button bpu = new Button("Ok");
					pu.getContent().add(vpu);
					vpu.setAlignment(Pos.CENTER);
					vpu.getChildren().addAll(tpu, bpu);
					pu.show(stage);
					
					txt1.setText("");
					txt2.setText("");
				}
			}
			
		});
		
		//GridPane pane = new GridPane();
		//BorderPane
		pane.setTop(menuBar);
		pane.setLeft(vBoxLeft);
		pane.setCenter(vBoxCenter);
		pane.setRight(vBoxRight);
	
		//Center
		vBoxCenter.getChildren().add(listView);
		
		//Left
		vBoxLeft.getChildren().add(txt1);
		vBoxLeft.getChildren().add(txt2);
		vBoxLeft.getChildren().add(addButton);
		
		//Right
		vBoxRight.getChildren().add(vocNumber);
		
		//Bottom
		
		//Styling
		VBox.setMargin(txt1, new Insets(50, 10, 10, 10));
		VBox.setMargin(txt2, new Insets(10));
		VBox.setMargin(addButton, new Insets(5, 10, 10, 10));
		VBox.setMargin(listView, new Insets(5));
		System.out.println(listView.getItems());
		vBoxLeft.setMinWidth(100);
		vBoxLeft.setPrefWidth(150);
		vBoxLeft.setMaxWidth(200);
		vocNumber.setStyle("-fx-font-size: 14");
		txt1.setStyle("-fx-font-size: 14");
		txt2.setStyle("-fx-font-size: 14");
		home.setStyle("-fx-font-size: 14");
		load.setStyle("-fx-font-size: 14");
		listView.setStyle("-fx-font-size: 14");
		addButton.setStyle("-fx-font-size: 14");
		
		
		
		Scene scene = new Scene(pane, 500, 500);
		stage.setScene(scene);
		stage.show();
	}
}