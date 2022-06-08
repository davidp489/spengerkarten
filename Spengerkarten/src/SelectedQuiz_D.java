import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Diese Klasse beinhaltet die Logik hinter dem Randomizer und der normalen Abbildung der einzelnen Elementen eines Quizes
 * 
 * @author pav22044@spengergasse.at
 * @version 2022-06-07
 *
 */

public class SelectedQuiz_D extends Application
{
	private BorderPane pane;
	Scene scene;
	
	private Button random;
	private Button writeValue;
	private Button multipleChoice;
	private Button back;
	private Button continueButton;
	private Button reveal;
	private Button hide;
	
	Quiz newQuiz;
	
	private List<String> keys;
	
	private boolean checkIfRand = false;
	
	private static int index = 0;
	
	Main_Page_L backHome = new Main_Page_L();
	
	
	
	public static void main(String[] args)
	{
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception
	{
		
		//Test Quiz Objekt
		newQuiz = new Quiz("quizname");
		
		newQuiz.addVocab("Erste", "EErste");
		newQuiz.addVocab("Zweite", "ZZweite");
		newQuiz.addVocab("Dritte", "DDritte");
		newQuiz.addVocab("Vierte", "VVierte");
		newQuiz.addVocab("Fuenfte", "FFuenfte");
		newQuiz.addVocab("Sechste", "SSechste");
		newQuiz.addVocab("Siebte", "SSiebte");
		
		//Die Buttons für die Art, wie man das Quiz angehen will
		VBox leftvbox = new VBox();
		//Ein Element des Quizes wird hier abgebildet
		HBox quizElement = new HBox();
		//Hier befinden sich die Buttons 'Weiter' und 'Zurueck'
		HBox bottomhbox = new HBox();
		//Zeigt den live-stand der bisherigen Antworten
		VBox livestats = new VBox();
		
		pane = new BorderPane();
		
		MenuBar menuBar = new MenuBar();
		Label labelHome = new Label("Home");
		Menu home = new Menu("", labelHome);
		menuBar.getMenus().add(home);
		
		this.random = new Button("Random");
		this.writeValue = new Button("Starten");
		this.multipleChoice = new Button("Multiple Choice");
		
		this.writeValue.setPrefSize(100, 30);
		this.random.setPrefSize(100, 30);
		this.multipleChoice.setPrefSize(100, 30);
		
		this.reveal = new Button("Auflösen");
		this.hide = new Button("Verstecken");
		
		
		this.back = new Button("Zurueck");
		Label quizname = new Label(newQuiz.getName());
		this.continueButton = new Button("Weiter");
		
		
		Label livestatlable = new Label("Live stats:");
		Label correctAnswers = new Label("Richtige antworten: ");
		Label wrongAnswers = new Label("Falsche antworten: ");
		Label percentage = new Label("Richtig in %: ");
		Label grade = new Label("Note: ");
		
		labelHome.setOnMouseClicked((e -> {
			try {
				backHome.start(stage);
			} catch (Exception e1) {
				// TODO Auto-generated catch blocksss
				e1.printStackTrace();
			}
		}));
		
		//Inhalt des Elementes
		Label quizKey = new Label("Key: " + newQuiz.getKeyFromIndex(index));
		quizKey.setPrefSize(200, 20);
		
		random.setOnAction((ActionEvent event) -> {
			this.checkIfRand = true;
			//Index wird automatisch auf 0 gesetzt
			index = 0;
			//Inhalt der Hashmap wird in einer Liste gespeichert
			keys = new ArrayList<String>(newQuiz.getMap().keySet());
			//Die Liste wird geshuffled bzw. mit einem Randomizer versehen.
			Collections.shuffle(keys);
			System.out.format("Index: %s", index);
			System.out.println();
			System.out.format("Liste mit Randomizer: %s", keys);
			System.out.println();
			//Der erste Index wird abgebildet
			quizKey.setText("Key: " + keys.get(0).toString());
		});
		
		/*Wenn der Button 'Aufloesen' gedrückt wird, wird die Antwort der Frage/des Vokabulars angezeigt und 
		 * der Button wird mit dem Button 'Verstecken' ersetzt
		 */
		reveal.setOnAction((ActionEvent event) -> {
			livestats.getChildren().remove(reveal);
			livestats.getChildren().add(hide);
			if(checkIfRand)
			{
				//Nimmt die Liste, wenn sie mit einem Randomizer versehen ist
				quizKey.setText("Value: " + newQuiz.getValue(keys.get(index).toString()));
			} else
			{
				//Andernfalls nimmt die Liste mit der richtigen Reihenfolge 
				quizKey.setText("Value: " + newQuiz.getValue(newQuiz.getKeyFromIndex(index)));
			}
		});
		
		/*Der Button 'Verstecken' wird ersetzt mit dem Button 'Aufloesen', die Frage bzw. die nicht übersetzte Vokabel wird angezeigt
		 * 
		 */
		hide.setOnAction((ActionEvent event) -> {
			livestats.getChildren().remove(hide);
			livestats.getChildren().add(reveal);
			if(checkIfRand)
			{
				//Nimmt die Liste, wenn sie mit einem Randomizer versehen ist
				quizKey.setText("Key: " + keys.get(index).toString());
			} else
			{
				//Andernfalls nimmt die Liste mit der richtigen Reihenfolge 
				quizKey.setText("Key: " + newQuiz.getKeyFromIndex(index));
			}
		});
			
		
			
		//Wenn gedrückt, wird der Index erhöht und das Label wird auf den neuen Index gesetzt	
		continueButton.setOnAction((ActionEvent event) -> {
			if(index == newQuiz.getSize() - 1)
			{
				return;
			}
			//Sobald der Random Button gedrückt wird, wird 'checkIfRand' auf true gesetzt, somit wird die Liste geshuffled
			if(checkIfRand)
			{
				index++;
				System.out.format("Index: %s", index);
				System.out.println();
				System.out.format("Liste mit Randomizer: %s", keys);
				System.out.println();
				//Die Liste mit dem Randomizer
				quizKey.setText("Key: " + keys.get(index).toString());
			//Andernfalls wird die normale Liste ohne Randomizer verwendet
			} else
			{
				index++;
				System.out.format("Index: %s", index);
				System.out.println();
				System.out.format("Liste: %s", newQuiz.getMap());
				System.out.println();
				//Die Liste ohne Randomizer
				quizKey.setText("Key: " + newQuiz.getKeyFromIndex(index));
			}
				
		});
			
		//Wenn gedrückt, wird der Index verringert und das Label wird auf den neuen Index gesetzt
		back.setOnAction((ActionEvent event) -> {
			if(index == 0)
			{
				return;
			}
			//Sobald der Random Button gedrückt wird, wird 'checkIfRand' auf true gesetzt, somit wird die Liste geshuffled
			if(checkIfRand)
			{
				index--;
				System.out.format("Index: %s", index);
				System.out.println();
				System.out.format("Liste mit Randomizer: %s", keys);
				System.out.println();
				//Die Liste mit dem Randomizer
				quizKey.setText("Key: " + keys.get(index).toString());
			//Andernfalls wird die normale Liste ohne Randomizer verwendet
			} else
			{
				index--;
				System.out.format("Index: %s", index);
				System.out.println();
				System.out.format("Liste: %s", newQuiz.getMap());
				System.out.println();
				//Die Liste ohne Randomizer
				quizKey.setText("Key: " + newQuiz.getKeyFromIndex(index));
			}
			
				
		});
		
		
		
		
		leftvbox.setSpacing(15);
		bottomhbox.setSpacing(20);
		
		leftvbox.setPadding(new Insets(100, 0, 200, 50));
		bottomhbox.setPadding(new Insets(0, 0, 1000, 200));
		quizElement.setPadding(new Insets(100, 500, 0, 100));
		
		pane.setTop(menuBar);
		pane.setLeft(leftvbox);
		pane.setCenter(quizElement);
		pane.setBottom(bottomhbox);
		pane.setRight(livestats);
		
		
		
		leftvbox.getChildren().addAll(random, writeValue, multipleChoice);
		bottomhbox.getChildren().addAll(back, quizname,  continueButton);
		quizElement.getChildren().addAll(quizKey);
		livestats.getChildren().addAll(livestatlable, correctAnswers, wrongAnswers, percentage, grade, reveal);
		
		scene = new Scene(pane, 1000, 720);
		
		stage.setScene(scene);
		stage.show();
		
	}
	
	
}
