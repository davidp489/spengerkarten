import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
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
	
	private Button random, writeValue, multipleChoice, back, continueButton, reveal, hide, yes, no;
	
	private Label livestatlable, correctAnswers, wrongAnswers, percentageCount, grade, abpruefung, endOfQuiz, quizCount;
	
	private Text quizKey;
	
	Quiz newQuiz;
	
	private List<String> keys;
	
	private boolean checkIfRand = false;
	
	private static int index = 0;
	
	private int note;
	
	private int correct = 0;
	private int wrong = 0;
	private float percentage;
	
	Main_Page_L backHome = new Main_Page_L();
	
	
	public void start(Stage stage) throws Exception
	{
		
		//Die Buttons f�r die Art, wie man das Quiz angehen will
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
		this.multipleChoice.setOnAction(e->{
			new Multiple_Choice_L().quizUebergabe(stage, newQuiz);
		});
		
		this.writeValue.setPrefSize(100, 30);
		this.random.setPrefSize(100, 30);
		this.multipleChoice.setPrefSize(100, 30);
		
		this.reveal = new Button("L�sung anzeigen");
		this.hide = new Button("Verstecken");
		
		//Wird im Vertical Box "quizElement" verschachtelt
		HBox yesnoButtons = new HBox();
		
		this.yes = new Button("Ja");
		this.no = new Button("Nein");
		
		yesnoButtons.getChildren().addAll(yes, no);
		
		
		this.back = new Button("Zur�ck");
		this.quizCount = new Label((index+1) + "/" + newQuiz.getSize());
		this.continueButton = new Button("Weiter");
		
		
		this.livestatlable = new Label("Live stats: " + this.newQuiz.getName());
		this.correctAnswers = new Label("Richtige antworten: " + this.correct + "/" + this.newQuiz.getSize());
		this.wrongAnswers = new Label("Falsche antworten: " + this.wrong + "/" + this.newQuiz.getSize());
		this.percentageCount = new Label("Richtig in %: -");
		this.grade = new Label("Note: -");
		
		this.abpruefung = new Label("Hast du die Antwort gewusst?");
		this.endOfQuiz = new Label("Du bist am Ende angelangt, rechts siehst du dein Ergebnis");
		
		//Inhalt des Elementes
		this.quizKey = new Text(newQuiz.getKeyFromIndex(index));
		
		
		labelHome.setOnMouseClicked((e -> {
			try {
				backHome.start(stage);
			} catch (Exception e1) {
				// TODO Auto-generated catch blocksss
				e1.printStackTrace();
			}
		}));
		
		
		
		random.setOnAction((ActionEvent event) -> {
			bottomhbox.getChildren().removeAll(continueButton, back);

			refreshRandomizer();
			
			/*Checkt beim Starten vom Randomizer, ob der "L�sung anzeigen" Button noch da ist (Da er nach dem Ende des Quizzes entfernt wird) 
			 *	wenn nein, wird er hinzugef�gt. 
			 *	
			 *	Wenn die Nachricht "endOfQuiz" beim Bet�tigen noch da ist, wird er entfernt
			 *	(Die Nachricht erscheint, wenn man das Quiz fertig gemacht hat).
			 *
			 *	Beim erneuten Starten wird "quizKey" wieder hinzugef�gt
			 *
			*/
			if(livestats.getChildren().isEmpty())
			{
				livestats.getChildren().addAll(livestatlable, correctAnswers, wrongAnswers, percentageCount, grade);
			}
			
			if(quizElement.getChildren().contains(endOfQuiz))
			{
				quizElement.getChildren().remove(endOfQuiz);
				quizElement.getChildren().add(quizKey);
				quizElement.getChildren().add(reveal);
				
			}
		});
		
		/*Wenn der Button 'Aufloesen' gedr�ckt wird, wird die Antwort der Frage/des Vokabulars angezeigt und 
		 * der Button wird mit dem Button 'Verstecken' ersetzt
		 */
		reveal.setOnAction((ActionEvent event) -> {
			quizElement.getChildren().remove(reveal);
			
			if(checkIfRand)
			{
				//Nimmt die Liste, wenn sie mit einem Randomizer versehen ist
				quizKey.setText(newQuiz.getValue(keys.get(index).toString()));
				//F�gt die Buttons hinzu, womit man abpruefen kann, ob man richtig oder falsch lag
				quizElement.getChildren().addAll(abpruefung, yesnoButtons);
			} else
			{
				quizElement.getChildren().add(hide);
				//Andernfalls nimmt die Liste mit der richtigen Reihenfolge 
				quizKey.setText(newQuiz.getValue(newQuiz.getKeyFromIndex(index)));
			}
		});
		
		yes.setOnAction((ActionEvent event) -> {
			//Nach einer richtigen Antwort wird die Zahl der korrekten Antworten inkrementiert
			correct++;
			System.out.format("Anzahl an korrekten Antworten: %s", correct);
			System.out.println();
			quizElement.getChildren().removeAll(abpruefung, yesnoButtons);
			//Setzt den Text auf den neuen Punktestand
			correctAnswers.setText("Richtige antworten: " + this.correct + "/" + this.newQuiz.getSize());
			//Sobald das Ende des Quizes erreicht wird
			if(index == newQuiz.getSize() - 1)
			{
				quizElement.getChildren().removeAll(reveal);
				quizElement.getChildren().add(endOfQuiz);
				quizElement.getChildren().remove(quizKey);
				
				//Rechnet das Ergebnis in Prozent aus
				this.percentage = (float) correct / newQuiz.getSize();
				
				//Formattiert die Dezimalzahl in Prozent
				NumberFormat defaultFormat = NumberFormat.getPercentInstance();
				String percentageFormatted = defaultFormat.format(percentage);
				
				percentageCount.setText("Richtig in %: " + percentageFormatted);
				
				//Bestimmung der Note
				notenBerechnung();
				return;
			}
			quizElement.getChildren().add(reveal);
			index++;
			System.out.format("Index: %s", index);
			System.out.println();
			
			
			//Das n�chste Element wird angezeigt
			quizKey.setText(keys.get(index).toString());
			quizCount.setText((index + 1) + "/" + newQuiz.getSize());
		});
		
		no.setOnAction((ActionEvent event) -> {
			quizElement.getChildren().add(reveal);
			
			//Nach einer falschen Antwort wird die Zahl der falschen Antworten inkrementiert
			wrong++;
			System.out.format("Anzahl an Fehler: %s", wrong);
			System.out.println();
			quizElement.getChildren().removeAll(abpruefung, yesnoButtons);
			
			//Setzt den Text auf den neuen Punktestand
			wrongAnswers.setText("Falsche antworten: " + this.wrong + "/" + this.newQuiz.getSize());
			
			//Sobald das Ende des Quizes erreicht wird
			if(index == newQuiz.getSize() - 1)
			{
				quizElement.getChildren().removeAll(reveal);
				quizElement.getChildren().add(endOfQuiz);
				quizElement.getChildren().remove(quizKey);
				//Rechnet das Ergebnis in Prozent aus
				this.percentage = (float) correct / newQuiz.getSize();
				//Formattiert die Dezimalzahl in Prozent als String
				NumberFormat defaultFormat = NumberFormat.getPercentInstance();
				String percentageFormatted = defaultFormat.format(percentage);
				
				percentageCount.setText("Richtig in %: " + percentageFormatted);
				
				//Bestimmung der Note
				notenBerechnung();
				return;
			}
			index++;
			System.out.format("Index: %s", index);
			System.out.println();
			
			
			//Das n�chste Element wird angezeigt
			quizKey.setText(keys.get(index).toString());
			quizCount.setText((index + 1) + "/" + newQuiz.getSize());
		});
		
		/*Der Button 'Verstecken' wird ersetzt mit dem Button 'Aufloesen', die Frage bzw. die nicht �bersetzte Vokabel wird angezeigt
		 * 
		 */
		hide.setOnAction((ActionEvent event) -> {
			quizElement.getChildren().remove(hide);
			quizElement.getChildren().add(reveal);
			if(checkIfRand)
			{
				//Nimmt die Liste, wenn sie mit einem Randomizer versehen ist
				quizKey.setText(keys.get(index).toString());
			} else
			{
				//Andernfalls nimmt die Liste mit der richtigen Reihenfolge 
				quizKey.setText(newQuiz.getKeyFromIndex(index));
			}
		});
			
		
			
		//Wenn gedr�ckt, wird der Index erh�ht und das Label wird auf den neuen Index gesetzt	
		continueButton.setOnAction((ActionEvent event) -> {
			if(index == newQuiz.getSize() - 1)
			{
				return;
			}
			if(quizElement.getChildren().contains(hide))
			{
				quizElement.getChildren().remove(hide);
				quizElement.getChildren().add(reveal);
			}
			
			index++;
			System.out.format("Index: %s", index);
			System.out.println();
	
			System.out.format("Liste: %s", newQuiz.getMap());
			System.out.println();
			//Die Liste ohne Randomizer
			quizKey.setText(newQuiz.getKeyFromIndex(index));
			quizCount.setText((index + 1) + "/" + newQuiz.getSize());
			
				
		});
			
		//Wenn gedr�ckt, wird der Index verringert und das Label wird auf den neuen Index gesetzt
		back.setOnAction((ActionEvent event) -> {
			if(index == 0)
			{
				return;
			}
			if(quizElement.getChildren().contains(hide))
			{
				quizElement.getChildren().remove(hide);
				quizElement.getChildren().add(reveal);
			}
			
			index--;
			System.out.format("Index: %s", index);
			System.out.println();
			
			System.out.format("Liste: %s", newQuiz.getMap());
			System.out.println();
			//Die Liste ohne Randomizer
			quizKey.setText(newQuiz.getKeyFromIndex(index));
			quizCount.setText((index + 1) + "/" + newQuiz.getSize());
			
				
		});
		
		writeValue.setOnAction(e ->{
			Write_Value_C writeValue = new Write_Value_C();
			try {
				writeValue.quizUebernehmen(stage, newQuiz);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		
		pane.setTop(menuBar);
		pane.setLeft(leftvbox);
		pane.setCenter(quizElement);
		pane.setBottom(bottomhbox);
		pane.setRight(livestats);
		
		
		//Styling
		
		quizKey.setStyle("-fx-font-size: 30");
		abpruefung.setStyle("-fx-font-size: 20");
		
		
		random.setPrefSize(250, 200);
		
		
		writeValue.setPrefSize(250, 200);
		
		
		multipleChoice.setPrefSize(250, 200);
		
		menuBar.setStyle("-fx-background-color: #befaff");
		
		leftvbox.setSpacing(15);
		leftvbox.setPadding(new Insets(100, 25, 200, 25));
		leftvbox.setStyle("-fx-background-color: #1e90ff; -fx-text-fill: #ffffff;");
		
		
		bottomhbox.setPadding(new Insets(20, 0, 1000, 0));
		bottomhbox.setSpacing(20);
		bottomhbox.setAlignment(Pos.CENTER);
		bottomhbox.setStyle("-fx-background-color: #1e90ff;");
		
		quizElement.setAlignment(Pos.CENTER);
		quizElement.setSpacing(20);
		
		livestats.setStyle("-fx-background-color: #1e90ff");
		livestats.setPadding(new Insets(0, 15, 0, 25));
		
		yesnoButtons.setAlignment(Pos.CENTER);
		yesnoButtons.setSpacing(20);
		
		
		
		
		
		
		leftvbox.getChildren().addAll(random, writeValue, multipleChoice);
		bottomhbox.getChildren().addAll(back, quizCount,  continueButton);
		quizElement.getChildren().addAll(quizKey, reveal);
		
		scene = new Scene(pane, 1280, 720);
		scene.getStylesheets().add("Style.css");
		
		stage.setScene(scene);
		stage.show();
		
	}
	
	public void quizUebernehmen(Stage stage, Quiz quiz) {
		newQuiz = quiz;
		index = 0;
		try {
			start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void notenBerechnung()
	{
		if(percentage < 0.5)
		{
			note = 5;
		} 
		else if(percentage >= 0.5 && percentage < 0.65) 
		{
			note = 4;
		} 
		else if(percentage >= 0.65 && percentage < 0.8)
		{
			note = 3;
		} 
		else if(percentage >= 0.8 && percentage < 0.9)
		{
			note = 2;
		} 
		else if(percentage >= 0.9)
		{
			note = 1;
		}
		grade.setText("Note: " + note);
	}
	
	public void refreshRandomizer()
	{
		//Score wird zur�ckgesetzt
		this.correct = 0;
		this.wrong = 0;
		correctAnswers.setText("Richtige antworten: " + this.correct + "/" + this.newQuiz.getSize());
		wrongAnswers.setText("Falsche antworten: " + this.wrong + "/" + this.newQuiz.getSize());
		percentageCount.setText("Richtig in %: -");
		grade.setText("Note: -");
		
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
		quizKey.setText(keys.get(0).toString());
		quizCount.setText((index+1) + "/" + newQuiz.getSize());
	}
}
