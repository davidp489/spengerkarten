import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
	
	private Button yes;
	private Button no;
	
	Quiz newQuiz;
	
	private List<String> keys;
	
	private boolean checkIfRand = false;
	
	private static int index = 0;
	
	private int note;
	
	private int correct = 0;
	private int wrong = 0;
	private float percentage;
	
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
		
		newQuiz.addVocab("Kitchen", "Küche");
		newQuiz.addVocab("Table", "Tisch");
		newQuiz.addVocab("House", "Haus");
		newQuiz.addVocab("Microphone", "Mikrofon");
		newQuiz.addVocab("Software Engineer", "Software Entwickler");
		newQuiz.addVocab("Flower", "Blume");
		newQuiz.addVocab("Tree", "Baum");
		newQuiz.addVocab("Keyboard", "Tastatur");
		newQuiz.addVocab("Mouse", "Maus");
		newQuiz.addVocab("Frog", "Frosch");
		newQuiz.addVocab("Apple", "Apfel");
		
		//Die Buttons für die Art, wie man das Quiz angehen will
		VBox leftvbox = new VBox();
		//Ein Element des Quizes wird hier abgebildet
		VBox quizElement = new VBox();
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
		
		this.reveal = new Button("Lösung anzeigen");
		this.hide = new Button("Verstecken");
		
		this.yes = new Button("Ja");
		this.no = new Button("Nein");
		
		
		this.back = new Button("Zurueck");
		Label quizname = new Label(newQuiz.getName());
		this.continueButton = new Button("Weiter");
		
		
		Label livestatlable = new Label("Live stats: " + this.newQuiz.getName());
		Label correctAnswers = new Label("Richtige antworten: " + this.correct + "/" + this.newQuiz.getSize());
		Label wrongAnswers = new Label("Falsche antworten: " + this.wrong + "/" + this.newQuiz.getSize());
		Label percentageCount = new Label("Richtig in %: -");
		Label grade = new Label("Note: -");
		
		Label pruefung = new Label("Hast du die Antwort gewusst?");
		Label endOfQuiz = new Label("Du bist am Ende angelangt, rechts siehst du dein Ergebnis");
		
		//Inhalt des Elementes
		Text quizKey = new Text("Key: " + newQuiz.getKeyFromIndex(index));
		
		
		labelHome.setOnMouseClicked((e -> {
			try {
				backHome.start(stage);
			} catch (Exception e1) {
				// TODO Auto-generated catch blocksss
				e1.printStackTrace();
			}
		}));
		
		
		
		random.setOnAction((ActionEvent event) -> {
			//Score wird zurückgesetzt
			this.correct = 0;
			this.wrong = 0;
			correctAnswers.setText("Richtige antworten: " + this.correct + "/" + this.newQuiz.getSize());
			wrongAnswers.setText("Falsche antworten: " + this.wrong + "/" + this.newQuiz.getSize());
			percentageCount.setText("Richtig in %: -");
			grade.setText("Note: -");
			
			/*Checkt beim Starten vom Randomizer, ob der "Lösung anzeigen" Button noch da ist (Da er nach dem Ende des Quizzes entfernt wird) 
			 *	wenn nein, wird er hinzugefügt. 
			 *	
			 *	Wenn die Nachricht "endOfQuiz" beim Betätigen noch da ist, wird er entfernt
			 *	(Die Nachricht erscheint, wenn man das Quiz fertig gemacht hat).
			 *
			 *	Beim erneuten Starten wird "quizKey" wieder hinzugefügt
			 *
			*/
			if(!livestats.getChildren().contains(reveal) && quizElement.getChildren().contains(endOfQuiz) && !quizElement.getChildren().contains(quizKey))
			{
				livestats.getChildren().add(reveal);
				quizElement.getChildren().remove(endOfQuiz);
				quizElement.getChildren().add(quizKey);
			}
			
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
			
			if(checkIfRand)
			{
				//Nimmt die Liste, wenn sie mit einem Randomizer versehen ist
				quizKey.setText("Value: " + newQuiz.getValue(keys.get(index).toString()));
				//Fügt die Buttons hinzu, womit man abpruefen kann, ob man richtig oder falsch lag
				quizElement.getChildren().addAll(pruefung, yes, no);
			} else
			{
				livestats.getChildren().add(hide);
				//Andernfalls nimmt die Liste mit der richtigen Reihenfolge 
				quizKey.setText("Value: " + newQuiz.getValue(newQuiz.getKeyFromIndex(index)));
			}
		});
		
		yes.setOnAction((ActionEvent event) -> {
			livestats.getChildren().add(reveal);
			//Nach einer richtigen Antwort wird die Zahl der korrekten Antworten inkrementiert
			correct++;
			System.out.format("Anzahl an korrekten Antworten: %s", correct);
			System.out.println();
			quizElement.getChildren().removeAll(pruefung, yes, no);
			//Setzt den Text auf den neuen Punktestand
			correctAnswers.setText("Richtige antworten: " + this.correct + "/" + this.newQuiz.getSize());
			//Sobald das Ende des Quizes erreicht wird
			if(index == newQuiz.getSize() - 1)
			{
				livestats.getChildren().removeAll(reveal);
				quizElement.getChildren().add(endOfQuiz);
				quizElement.getChildren().remove(quizKey);
				
				//Rechnet das Ergebnis in Prozent aus
				this.percentage = (float) correct / newQuiz.getSize();
				
				//Formattiert die Dezimalzahl in Prozent
				NumberFormat defaultFormat = NumberFormat.getPercentInstance();
				String percentageFormatted = defaultFormat.format(percentage);
				
				percentageCount.setText("Richtig in %: " + percentageFormatted);
				
				//Bestimmung der Note
				if(percentage < 0.5)
				{
					note = 5;
					grade.setText("Note: " + note);
				} 
				else if(percentage >= 0.5 && percentage < 0.65) 
				{
					note = 4;
					grade.setText("Note: " + note);
				} 
				else if(percentage >= 0.65 && percentage < 0.8)
				{
					note = 3;
					grade.setText("Note: " + note);
				} 
				else if(percentage >= 0.8 && percentage < 0.9)
				{
					note = 2;
					grade.setText("Note: " + note);
				} 
				else if(percentage >= 0.9)
				{
					note = 1;
					grade.setText("Note: " + note);
				}
				return;
			}
			index++;
			System.out.println(index);
			
			
			//Das nächste Element wird angezeigt
			quizKey.setText("Key: " + keys.get(index).toString());
			
		});
		
		no.setOnAction((ActionEvent event) -> {
			livestats.getChildren().add(reveal);
			
			//Nach einer falschen Antwort wird die Zahl der falschen Antworten inkrementiert
			wrong++;
			System.out.format("Anzahl an Fehler: %s", wrong);
			System.out.println();
			quizElement.getChildren().removeAll(pruefung, yes, no);
			
			//Setzt den Text auf den neuen Punktestand
			wrongAnswers.setText("Falsche antworten: " + this.wrong + "/" + this.newQuiz.getSize());
			
			//Sobald das Ende des Quizes erreicht wird
			if(index == newQuiz.getSize() - 1)
			{
				livestats.getChildren().removeAll(reveal);
				quizElement.getChildren().add(endOfQuiz);
				quizElement.getChildren().remove(quizKey);
				//Rechnet das Ergebnis in Prozent aus
				this.percentage = (float) correct / newQuiz.getSize();
				//Formattiert die Dezimalzahl in Prozent als String
				NumberFormat defaultFormat = NumberFormat.getPercentInstance();
				String percentageFormatted = defaultFormat.format(percentage);
				
				percentageCount.setText("Richtig in %: " + percentageFormatted);
				
				//Bestimmung der Note
				if(percentage < 0.5)
				{
					note = 5;
					grade.setText("Note: " + note);
				} 
				else if(percentage >= 0.5 && percentage < 0.65) 
				{
					note = 4;
					grade.setText("Note: " + note);
				} 
				else if(percentage >= 0.65 && percentage < 0.8)
				{
					note = 3;
					grade.setText("Note: " + note);
				} 
				else if(percentage >= 0.8 && percentage < 0.9)
				{
					note = 2;
					grade.setText("Note: " + note);
				} 
				else if(percentage >= 0.9)
				{
					note = 1;
					grade.setText("Note: " + note);
				}
				return;
			}
			index++;
			System.out.println(index);
			
			
			//Das nächste Element wird angezeigt
			quizKey.setText("Key: " + keys.get(index).toString());
			
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
		
		
		pane.setTop(menuBar);
		pane.setLeft(leftvbox);
		pane.setCenter(quizElement);
		pane.setBottom(bottomhbox);
		pane.setRight(livestats);
		
		menuBar.setStyle("-fx-background-color: #ffd0fe");
		leftvbox.setStyle("-fx-background-color: #ffc3cb");
		quizElement.setStyle("-fx-background-color: #ffefc8");
		bottomhbox.setStyle("-fx-background-color: #ceffc6");
		livestats.setStyle("-fx-background-color: #befaff");
		
		//Button/Livestat Styling
		back.setStyle("-fx-font-size: 25");
		quizname.setStyle("-fx-font-size: 25");
		continueButton.setStyle("-fx-font-size: 25");
		quizKey.setStyle("-fx-font-size: 30");
		livestatlable.setStyle("-fx-font-size: 25");
		correctAnswers.setStyle("-fx-font-size: 25");
		wrongAnswers.setStyle("-fx-font-size: 25");
		percentageCount.setStyle("-fx-font-size: 25");
		grade.setStyle("-fx-font-size: 25");
		reveal.setStyle("-fx-font-size: 25");
		hide.setStyle("-fx-font-size: 25");
		yes.setStyle("-fx-font-size: 25");
		no.setStyle("-fx-font-size: 25");
		pruefung.setStyle("-fx-font-size: 20");
		endOfQuiz.setStyle("-fx-font-size: 25");
		
		random.setStyle("-fx-font-size: 20");
		random.setPrefSize(200, 200);
		
		writeValue.setStyle("-fx-font-size: 20");
		writeValue.setPrefSize(200, 200);
		
		multipleChoice.setStyle("-fx-font-size: 20");
		multipleChoice.setPrefSize(200, 200);
		
		leftvbox.setSpacing(15);
		bottomhbox.setSpacing(20);
		
		leftvbox.setPadding(new Insets(100, 0, 200, 50));
		bottomhbox.setPadding(new Insets(0, 0, 1000, 200));
		
		
		quizElement.setAlignment(Pos.CENTER);
		
		
		
		bottomhbox.setAlignment(Pos.CENTER);
		
		
		
		
		
		leftvbox.getChildren().addAll(random, writeValue, multipleChoice);
		bottomhbox.getChildren().addAll(back, quizname,  continueButton);
		quizElement.getChildren().addAll(quizKey);
		livestats.getChildren().addAll(livestatlable, correctAnswers, wrongAnswers, percentageCount, grade, reveal);
		
		scene = new Scene(pane, 1280, 720);
		
		stage.setScene(scene);
		stage.show();
		
	}
	
	
}
