import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Set;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class New_Quiz_C extends Application{
	
	String vocSetName;
	int selectedIndex = -1;
	boolean cheange = true;
	private int vocCounter = 0;
	LinkedHashMap<String, String> cacheLHM = new LinkedHashMap<String, String>();
	ListView<String> listView = new ListView<String>();

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane pane = new BorderPane(); //Ist der komplette Bildschirm
		VBox vBoxLeft = new VBox(); //VBox links
		VBox vBoxCenter = new VBox(); //VBox center
		VBox vBoxRight = new VBox(); //VBox rechts
		
		
		Label labelHome = new Label("Home");
		MenuBar menuBar = new MenuBar();
		Menu home = new Menu("", labelHome);
		Menu load = new Menu("Load");
		MenuItem importCsv = new MenuItem("Import");
		menuBar.getMenus().addAll(home, load);
		load.getItems().add(importCsv);
		TextField key = new TextField();
		key.setPromptText("first vocab");
		TextField value = new TextField();
		value.setPromptText("second vocab");
		Button addButton = new Button("Add");
		Button saveButton = new Button("Save");
		Label vocNumberLb = new Label("Anzahl an Vokabeln: " + vocCounter);
		Button removeButton = new Button("Remove");
		removeButton.setDisable(true);
		
		
		//Hintergrund Sachen
		//Add Button
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
				else if(key.getText().equals(value.getText()))
				{
					errAlert.setContentText("first Input and second Input are the same");
					errAlert.show();
				}
				else if(key.getText().contains("/") || value.getText().contains("/"))
				{
					errAlert.setContentText("\"/\" is not allowed");
					errAlert.show();
				}
				else if(selectedIndex != -1)
				{
					//String selectedKey = new ArrayList<String>(cacheLHM.keySet()).get(selectedIndex);
					LinkedHashMap<String, String> saveLHM = new LinkedHashMap<String, String>();
					listView.getItems().clear();
					int i = 0;
					for(String word : cacheLHM.keySet())
					{
						if(i == selectedIndex)
						{
							saveLHM.put(key.getText(), value.getText());
							listView.getItems().add(key.getText() + " / " + value.getText());
						}
						else
						{
							saveLHM.put(word, cacheLHM.get(word));
							listView.getItems().add(word + " / " + cacheLHM.get(word));
						}
						i++;
					}
					cacheLHM.clear();
					cacheLHM = saveLHM;
					System.out.println(cacheLHM);
					key.setText("");
					value.setText("");
					
					selectedIndex = -1;
					
				}
				else
				{
					boolean duplicateKey = false; //wird auf true gesetzt wenn es den Wert von key bereits im Karteikartenset gibt
					boolean duplicateValue = false; //wird auf true gesetzt wenn es en Wert von value bereits im Karteikartenset gibt
					Alert duplicateAlert = new Alert(AlertType.ERROR);
					duplicateAlert.setTitle("Error");
					duplicateAlert.setHeaderText("Duplicate entry!");
					
					for(String x : cacheLHM.keySet())
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
					
					for(String y : cacheLHM.values())
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
					
					if(key.getText().equals(value.getText()))
					{
						duplicateAlert.setHeaderText("Same entry");
						duplicateAlert.setContentText("Das erste und zweite Wort sind gleich");
						duplicateAlert.show();
					}
					else if(duplicateKey == true && duplicateValue == true)
					{
						duplicateAlert.setContentText("Diese Karteikarte existiert bereits");
						duplicateAlert.show();
					}
					else if(duplicateKey == true || duplicateValue == true)
					{
						//Hier muss nicht der Content geï¿½ndert werden weil das schon oben gemacht wurde
						duplicateAlert.show();
					}
					else
					{
						System.out.println("Hier1");
						listView.getItems().add(key.getText()+ " / " + value.getText());
						System.out.println("Hier2");
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
		
			//Edit
			listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
				
				
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					//aufteilen in firstWord und secondWord
					String[] vocabWords;
					String firstWord = "";
					String secondWord = "";
					if(listView.getSelectionModel().getSelectedItem() != null)
					{
						vocabWords = listView.getSelectionModel().getSelectedItem().split("/");
						firstWord = vocabWords[0].substring(0, vocabWords[0].length() - 1);
						secondWord = vocabWords[1].substring(1);
						System.out.println(firstWord);
						System.out.println(secondWord);
					
						//Ändern
						selectedIndex = listView.getSelectionModel().getSelectedIndex();
						key.setText(firstWord);
						value.setText(secondWord);
						
						//Remove Button
						removeButton.setDisable(false);
						removeButton.setOnAction(removeEvent -> {
							System.out.println(listView.getSelectionModel().getSelectedItem());
							String[] getKey = listView.getSelectionModel().getSelectedItem().split(" ");
							listView.getItems().remove(listView.getSelectionModel().getSelectedItem());
							cacheLHM.remove(getKey[0]);
							key.setText("");
							value.setText("");
							removeButton.setDisable(true);
							System.out.println(cacheLHM);
							listView.getItems().clear();
							
							for(String word : cacheLHM.keySet())
							{
								listView.getItems().add(word + " / " + cacheLHM.get(word));
							}
							vocCounter--;
							vocNumberLb.setText("Anzahl an Vokabeln: " + Integer.toString(vocCounter));
							selectedIndex = -1;
						});
					}
					else
					{
						removeButton.setDisable(true);
					}
				}
				
			});
		
		//Import Button
		importCsv.setOnAction(importEvent ->{
			FileChooser fileChoose = new FileChooser();
			ExtensionFilter exFilter = new ExtensionFilter("CSV Files", "*.csv");
			fileChoose.getExtensionFilters().add(exFilter);
			File selectedFile = fileChoose.showOpenDialog(stage);
			if(selectedFile != null)
			{
				/*try {
					int i = 1;
					BufferedReader br = new BufferedReader(new FileReader(selectedFile));
					String word = "";
					String word1 = "";
					String word2 = "";
					while((word = br.readLine()) != null)
					{
						if(i%2 == 0)
						{
							word1 = word;
						}
						else if(i%2 != 0)
						{
							word2 = word;
							cacheLHM.put(word1, word2);
							listView.getItems().add(word1 + " / " + word2);
							//Prï¿½fen ob doppelte drinnen sind.
							//Funktioniert noch nicht ganz. Aber vertrau ich schaff das. Lukas mach hier bitte nix ich will das selber hinbekommen.
						}
						i++;
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}*/
				
				Quiz quiz = getQuiz(selectedFile.getAbsolutePath());
				for(int i = 0; i < quiz.getSize(); i++) {
					String quizKey = quiz.getKeyFromIndex(i);
					listView.getItems().add(quizKey + " / " + quiz.getValue(quizKey));
					cacheLHM.put(quizKey, quiz.getValue(quizKey));
				}
				vocCounter += quiz.getSize();
				vocNumberLb.setText("Anzahl an Vokabeln: " + vocCounter);
			}
			else
			{
				Alert fileAlert = new Alert(AlertType.ERROR);
				fileAlert.setTitle("File Error");
				fileAlert.setHeaderText("No File!");
				fileAlert.setContentText("You need to choose a file");
				fileAlert.show();
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
		removeButton.setStyle("-fx-font-size: 14");
		saveV.setStyle("-fx-font-size: 14");
		saveTF.setStyle("-fx-font-size: 14");
		saveB.setStyle("-fx-font-size: 14");
		
		//Save Button
		saveButton.setOnAction((ActionEvent saveShow) ->
		{
			saveStage.show();
		});
		
		//Save Button (give Quiz a name)
		//Testen ob beim saven ein Name fï¿½r das Kartenset angegeben wird
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
				else if(cacheLHM.size() <= 5)
				{
					Alert saveAlert = new Alert(AlertType.ERROR);
					saveAlert.setTitle("Error");
					saveAlert.setHeaderText("Quiz to small");
					saveAlert.setContentText("Please make more entries");
					saveAlert.show();
				}
				else
				{
					vocSetName = saveTF.getText();
					saveTF.setText("");
					saveStage.hide();
					//In HashMap ï¿½bertragen
					Quiz quiz = new Quiz(vocSetName);
					for(String x : cacheLHM.keySet())
					{
						quiz.addVocab(x, cacheLHM.get(x));
					}
					try {
						new Main_Page_L().quizUebergabe(stage, quiz);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//Zurï¿½ck zu Home
				}
				
			}
			
		});
		
		//Go back to Home
		labelHome.setOnMouseClicked(homeEvent -> {
			Main_Page_L goHome = new Main_Page_L();
			try {
				goHome.start(stage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		//Main Sachen
		pane.setTop(menuBar);
		pane.setLeft(vBoxLeft);
		pane.setCenter(vBoxCenter);
		pane.setRight(vBoxRight);
	
		//Center
		vBoxCenter.getChildren().addAll(listView, removeButton);
		
		//Left
		vBoxLeft.getChildren().addAll(key, value, addButton, saveButton);
		
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
	
	private Quiz getQuiz(String path) {
		String nameWithEnding = new File(path).getName();
		Quiz quiz = new Quiz(nameWithEnding.substring(0, nameWithEnding.indexOf(".")));
		Scanner s = null;
		try {
			s = new Scanner(new File(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(s.hasNextLine()) {
			String line = s.nextLine();
			String key = line.substring(0, line.indexOf(",")); 
			String value = line.substring(line.indexOf(",")+1);
			quiz.addVocab(key, value);
		}
		s.close();
		return quiz;
	}
}