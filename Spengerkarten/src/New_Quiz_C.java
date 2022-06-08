import java.util.LinkedHashMap;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class New_Quiz_C extends Application{
	
	String vocSetName;
	private int vocCounter = 0;
	LinkedHashMap<String, String> cacheLHM = new LinkedHashMap<String, String>();

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
		Label vocNumberLb = new Label("Anzahl an Vokabeln: " + vocCounter);
		
		
		
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
					boolean duplicateKey = false; //wird auf true gesetzt wenn es den Wert von key bereits im Karteikartenset gibt
					boolean duplicateValue = false; //wird auf true gesetzt wenn es en Wert von value bereits im Karteikartenset gibt
					Alert duplicateAlert = new Alert(AlertType.ERROR);
					duplicateAlert.setTitle("Error");
					duplicateAlert.setHeaderText("Duplicate entry!");
					
					for(String x : cacheLHM.keySet()) //setzt duplicateEntry bei bedarf auf true
					{
						if(key.getText().equals(x) || value.getText().equals(x))
						{
							if(key.getText().equals(x))
							{
								duplicateAlert.setContentText("Das erste Wort befindet sich bereits in den Karteikarten");
								duplicateKey = true;
							}
							else if(value.getText().equals(x))
							{
								duplicateAlert.setContentText("Das zweite Wort befindet sich bereits in den Karteikarten");
								duplicateValue = true;
							}
							
						}
					}
					
					for(String y : cacheLHM.values()) //setzt duplicateEntry bei bedarf auf true
					{
						if(value.getText().equals(y) || key.getText().equals(y))
						{
							if(key.getText().equals(y))
							{
								duplicateAlert.setContentText("Das erste Wort befindet sich bereits in den Karteikarten");
								duplicateKey = true;
							}
							else if(value.getText().equals(y))
							{
								duplicateAlert.setContentText("Das zweite Wort befindet sich bereits in den Karteikarten");
								duplicateValue = true;
							}
						}
					}
					
					if(duplicateKey == true && duplicateValue == true)
					{
						duplicateAlert.setContentText("Diese Karteikarte existiert bereits");
						duplicateAlert.show();
					}
					else if(duplicateKey == true || duplicateValue == true)
					{
						//Hier muss nicht der Content ge�ndert werden weil das schon oben gemacht wurde
						duplicateAlert.show();
					}
					else
					{
						listView.getItems().add(key.getText()+ " / " + value.getText());
						cacheLHM.put(key.getText(), value.getText());
						vocCounter++;
						vocNumberLb.setText("Anzahl an Vokabeln: " + Integer.toString(vocCounter));
						key.setText("");
						value.setText("");
						System.out.println(cacheLHM);
					}
				}
			}
		});
		
		
		//Bennen der Karten
		VBox saveV = new VBox();
		Text saveTxt = new Text("Name your Spengerkarten:");
		TextField saveTF = new TextField();
		Button saveB = new Button("Save");
		Scene save = new Scene(saveV, 200, 150);
		Stage saveStage = new Stage();
		saveStage.initModality(Modality.APPLICATION_MODAL);
		saveV.setAlignment(Pos.CENTER);
		saveStage.setResizable(false);
		saveStage.setScene(save);
		saveV.getChildren().addAll(saveTxt, saveTF, saveB);
		VBox.setMargin(saveTF, new Insets(10));
		saveV.setStyle("-fx-font-size: 14");
		saveTF.setStyle("-fx-font-size: 14");
		saveB.setStyle("-fx-font-size: 14");
		
		saveButton.setOnAction((ActionEvent saveShow) ->
		{
			saveStage.show();
		});
		
		//Testen ob beim saven ein Name f�r das Kartenset angegeben wird
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
					saveTF.setText("");
					saveStage.hide();
					//In HashMap �bertragen
					Quiz quiz = new Quiz(vocSetName);
					for(String x : cacheLHM.keySet())
					{
						quiz.addVocab(x, cacheLHM.get(x));
					}
					try {
						new Main_Page_L().uebergabe(stage, quiz);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//Zur�ck zu Home
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
		vBoxRight.getChildren().add(vocNumberLb);
		
		//Styling
		VBox.setMargin(key, new Insets(50, 10, 10, 10));
		VBox.setMargin(value, new Insets(10));
		VBox.setMargin(addButton, new Insets(5, 10, 10, 10));
		VBox.setMargin(saveButton, new Insets(190, 10, 10, 10));
		VBox.setMargin(listView, new Insets(5));
		vBoxLeft.setMinWidth(100);
		vBoxLeft.setPrefWidth(150);
		vBoxLeft.setMaxWidth(200);
		vocNumberLb.setStyle("-fx-font-size: 14");
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