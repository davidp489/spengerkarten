import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;


public class New_Quiz_C extends Application{
	
	String vocSetName;
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
		TextField key = new TextField();
		key.setPromptText("first vocab");
		TextField value = new TextField();
		value.setPromptText("second vocab");
		Button addButton = new Button("Add");
		Button saveButton = new Button("Save");
		Label vocNumber = new Label("Anzahl an Vokabeln: " + vocCounter);
		
		
		//Hintergrund Sachen
		addButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) { //Alert bei keiner Eingabe
				Alert errAlert = new Alert(AlertType.ERROR);
				errAlert.setTitle("Error");
				errAlert.setHeaderText("Input invalid!");
				
				if(key.getText().isBlank() && value.getText().isBlank())
				{
					errAlert.setContentText("You need to enter Text");
					errAlert.show();
				}
				else if(key.getText().isBlank())
				{
					errAlert.setContentText("Your first Input is emty");
					errAlert.show();
				}
				else if(value.getText().isBlank())
				{
					errAlert.setContentText("Your second Input is emty");
					errAlert.show();
				}
				else
				{
					listView.getItems().add(key.getText()+ " / " + value.getText());
					vocCounter++;
					System.out.println(vocCounter);
				}
					key.setText("");
					value.setText("");
			}
		});
		
		VBox saveV = new VBox();
		Text saveTxt = new Text("Name your Spengerkarten:");
		TextField saveTF = new TextField();
		Button saveB = new Button("Save");
		Scene save = new Scene(saveV, 200, 150);
		Stage saveStage = new Stage();
		
		//Handled das vom save Button inklusive dem bennen Screen
		saveButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				saveV.setAlignment(Pos.CENTER);
				saveStage.setResizable(false);
				saveStage.setScene(save);
				saveStage.initModality(Modality.APPLICATION_MODAL);
				saveV.getChildren().addAll(saveTxt, saveTF, saveB);
				VBox.setMargin(saveTF, new Insets(10));
				saveV.setStyle("-fx-font-size: 14");
				saveTF.setStyle("-fx-font-size: 14");
				saveB.setStyle("-fx-font-size: 14");
				saveStage.show();
			}
		});
		
		//Testen ob beim saven ein Name für das Kartenset angegeben wird
		saveB.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				if(saveTF.getText().isBlank())
				{
					
					Alert saveAlert = new Alert(AlertType.ERROR);
					saveAlert.setTitle("Error");
					saveAlert.setHeaderText("No Name");
					saveAlert.setContentText("Please enter a Name");
					saveAlert.show();
				}
				else
				{
					vocSetName = saveTF.getText();
					saveStage.hide();
					//In HashMap übertragen
					//Zurück zu Home
				}
				
			}
			
		});
		
		
		//Main Sachen
		pane.setTop(menuBar);
		pane.setLeft(vBoxLeft);
		pane.setCenter(vBoxCenter);
		pane.setRight(vBoxRight);
	
		//Center
		vBoxCenter.getChildren().add(listView);
		
		//Left
		vBoxLeft.getChildren().add(key);
		vBoxLeft.getChildren().add(value);
		vBoxLeft.getChildren().add(addButton);
		vBoxLeft.getChildren().add(saveButton);
		
		//Right
		vBoxRight.getChildren().add(vocNumber);
		
		//Bottom
		
		//Styling
		VBox.setMargin(key, new Insets(50, 10, 10, 10));
		VBox.setMargin(value, new Insets(10));
		VBox.setMargin(addButton, new Insets(5, 10, 10, 10));
		VBox.setMargin(saveButton, new Insets(190, 10, 10, 10));
		VBox.setMargin(listView, new Insets(5));
		vBoxLeft.setMinWidth(100);
		vBoxLeft.setPrefWidth(150);
		vBoxLeft.setMaxWidth(200);
		vocNumber.setStyle("-fx-font-size: 14");
		key.setStyle("-fx-font-size: 14");
		value.setStyle("-fx-font-size: 14");
		home.setStyle("-fx-font-size: 14");
		load.setStyle("-fx-font-size: 14");
		listView.setStyle("-fx-font-size: 14");
		addButton.setStyle("-fx-font-size: 14");
		saveButton.setStyle("-fx-font-size: 14");
		
		
		
		Scene scene = new Scene(pane, 500, 500);
		stage.setScene(scene);
		stage.show();
	}
}