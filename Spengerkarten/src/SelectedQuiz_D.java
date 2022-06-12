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
import javafx.scene.image.Image;
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
	
	private Button randomBtn, writeValueBtn, multipleChoiceBtn, back, continueBtn, revealBtn, hideBtn, yesBtn, noBtn;
	
	private Label livestatLabel, correctAnswersLabel, wrongAnswersLabel, percentageCountLabel, gradeLabel, abpruefungLabel, endOfQuizMessageLabel, quizElementNumberLabel;
	
	private Text quizElementKey;
	
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
		
		//Die Buttons fï¿½r die Art, wie man das Quiz angehen will
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
		
		this.randomBtn = new Button("Random");
		this.writeValueBtn = new Button("Starten");
		this.multipleChoiceBtn = new Button("Multiple Choice");
		this.multipleChoiceBtn.setOnAction(e->{
			new Multiple_Choice_L().quizUebergabe(stage, newQuiz);
		});
		
		this.writeValueBtn.setPrefSize(100, 30);
		this.randomBtn.setPrefSize(100, 30);
		this.multipleChoiceBtn.setPrefSize(100, 30);
		
		this.revealBtn = new Button("Lösung anzeigen");
		this.hideBtn = new Button("Verstecken");
		
		//Wird im Vertical Box "quizElement" verschachtelt
		HBox yesnoButtons = new HBox();
		
		this.yesBtn = new Button("Ja");
		this.noBtn = new Button("Nein");
		
		yesnoButtons.getChildren().addAll(yesBtn, noBtn);
		
		
		this.back = new Button("Zurück");
		this.quizElementNumberLabel = new Label((index+1) + "/" + newQuiz.getSize());
		this.continueBtn = new Button("Weiter");
		
		
		this.livestatLabel = new Label("Live stats: " + this.newQuiz.getName());
		this.correctAnswersLabel = new Label("Richtige antworten: " + this.correct + "/" + this.newQuiz.getSize());
		this.wrongAnswersLabel = new Label("Falsche antworten: " + this.wrong + "/" + this.newQuiz.getSize());
		this.percentageCountLabel = new Label("Richtig in %: -");
		this.gradeLabel = new Label("Note: -");
		
		this.abpruefungLabel = new Label("Hast du die Antwort gewusst?");
		this.endOfQuizMessageLabel = new Label("Du bist am Ende angelangt, rechts siehst du dein Ergebnis");
		
		//Inhalt des Elementes
		this.quizElementKey = new Text(newQuiz.getKeyFromIndex(index));
		
		
		labelHome.setOnMouseClicked((e -> {
			try {
				backHome.start(stage);
			} catch (Exception e1) {
				// TODO Auto-generated catch blocksss
				e1.printStackTrace();
			}
		}));
		
		
		
		randomBtn.setOnAction((ActionEvent event) -> {
			bottomhbox.getChildren().removeAll(continueBtn, back);

			refreshRandomizer();
			
			/*Checkt beim Starten vom Randomizer, ob der "Lï¿½sung anzeigen" Button noch da ist (Da er nach dem Ende des Quizzes entfernt wird) 
			 *	wenn nein, wird er hinzugefï¿½gt. 
			 *	
			 *	Wenn die Nachricht "endOfQuizMessageLabel" beim Betï¿½tigen noch da ist, wird er entfernt
			 *	(Die Nachricht erscheint, wenn man das Quiz fertig gemacht hat).
			 *
			 *	Beim erneuten Starten wird "quizKey" wieder hinzugefï¿½gt
			 *
			*/
			if(livestats.getChildren().isEmpty())
			{
				livestats.getChildren().addAll(livestatLabel, correctAnswersLabel, wrongAnswersLabel, percentageCountLabel, gradeLabel);
			}
			
			if(quizElement.getChildren().contains(endOfQuizMessageLabel))
			{
				quizElement.getChildren().remove(endOfQuizMessageLabel);
				quizElement.getChildren().add(quizElementKey);
				quizElement.getChildren().add(revealBtn);
				
			}
		});
		
		/*Wenn der Button 'Aufloesen' gedrï¿½ckt wird, wird die Antwort der Frage/des Vokabulars angezeigt und 
		 * der Button wird mit dem Button 'Verstecken' ersetzt
		 */
		revealBtn.setOnAction((ActionEvent event) -> {
			quizElement.getChildren().remove(revealBtn);
			
			if(checkIfRand)
			{
				//Nimmt die Liste, wenn sie mit einem Randomizer versehen ist
				quizElementKey.setText(newQuiz.getValue(keys.get(index).toString()));
				//Fï¿½gt die Buttons hinzu, womit man abpruefen kann, ob man richtig oder falsch lag
				quizElement.getChildren().addAll(abpruefungLabel, yesnoButtons);
			} else
			{
				quizElement.getChildren().add(hideBtn);
				//Andernfalls nimmt die Liste mit der richtigen Reihenfolge 
				quizElementKey.setText(newQuiz.getValue(newQuiz.getKeyFromIndex(index)));
			}
		});
		
		yesBtn.setOnAction((ActionEvent event) -> {
			//Nach einer richtigen Antwort wird die Zahl der korrekten Antworten inkrementiert
			correct++;
			System.out.format("Anzahl an korrekten Antworten: %s", correct);
			System.out.println();
			quizElement.getChildren().removeAll(abpruefungLabel, yesnoButtons);
			//Setzt den Text auf den neuen Punktestand
			correctAnswersLabel.setText("Richtige antworten: " + this.correct + "/" + this.newQuiz.getSize());
			//Sobald das Ende des Quizes erreicht wird
			if(index == newQuiz.getSize() - 1)
			{
				quizElement.getChildren().removeAll(revealBtn);
				quizElement.getChildren().add(endOfQuizMessageLabel);
				quizElement.getChildren().remove(quizElementKey);
				
				//Rechnet das Ergebnis in Prozent aus
				this.percentage = (float) correct / newQuiz.getSize();
				
				//Formattiert die Dezimalzahl in Prozent
				NumberFormat defaultFormat = NumberFormat.getPercentInstance();
				String percentageFormatted = defaultFormat.format(percentage);
				
				percentageCountLabel.setText("Richtig in %: " + percentageFormatted);
				
				//Bestimmung der Note
				notenBerechnung();
				return;
			}
			quizElement.getChildren().add(revealBtn);
			index++;
			System.out.format("Index: %s", index);
			System.out.println();
			
			
			//Das nï¿½chste Element wird angezeigt
			quizElementKey.setText(keys.get(index).toString());
			quizElementNumberLabel.setText((index + 1) + "/" + newQuiz.getSize());
		});
		
		noBtn.setOnAction((ActionEvent event) -> {
			quizElement.getChildren().add(revealBtn);
			
			//Nach einer falschen Antwort wird die Zahl der falschen Antworten inkrementiert
			wrong++;
			System.out.format("Anzahl an Fehler: %s", wrong);
			System.out.println();
			quizElement.getChildren().removeAll(abpruefungLabel, yesnoButtons);
			
			//Setzt den Text auf den neuen Punktestand
			wrongAnswersLabel.setText("Falsche antworten: " + this.wrong + "/" + this.newQuiz.getSize());
			
			//Sobald das Ende des Quizes erreicht wird
			if(index == newQuiz.getSize() - 1)
			{
				quizElement.getChildren().removeAll(revealBtn);
				quizElement.getChildren().add(endOfQuizMessageLabel);
				quizElement.getChildren().remove(quizElementKey);
				//Rechnet das Ergebnis in Prozent aus
				this.percentage = (float) correct / newQuiz.getSize();
				//Formattiert die Dezimalzahl in Prozent als String
				NumberFormat defaultFormat = NumberFormat.getPercentInstance();
				String percentageFormatted = defaultFormat.format(percentage);
				
				percentageCountLabel.setText("Richtig in %: " + percentageFormatted);
				
				//Bestimmung der Note
				notenBerechnung();
				return;
			}
			index++;
			System.out.format("Index: %s", index);
			System.out.println();
			
			
			//Das nï¿½chste Element wird angezeigt
			quizElementKey.setText(keys.get(index).toString());
			quizElementNumberLabel.setText((index + 1) + "/" + newQuiz.getSize());
		});
		
		/*Der Button 'Verstecken' wird ersetzt mit dem Button 'Aufloesen', die Frage bzw. die nicht ï¿½bersetzte Vokabel wird angezeigt
		 * 
		 */
		hideBtn.setOnAction((ActionEvent event) -> {
			quizElement.getChildren().remove(hideBtn);
			quizElement.getChildren().add(revealBtn);
			if(checkIfRand)
			{
				//Nimmt die Liste, wenn sie mit einem Randomizer versehen ist
				quizElementKey.setText(keys.get(index).toString());
			} else
			{
				//Andernfalls nimmt die Liste mit der richtigen Reihenfolge 
				quizElementKey.setText(newQuiz.getKeyFromIndex(index));
			}
		});
			
		
			
		//Wenn gedrï¿½ckt, wird der Index erhï¿½ht und das Label wird auf den neuen Index gesetzt	
		continueBtn.setOnAction((ActionEvent event) -> {
			if(index == newQuiz.getSize() - 1)
			{
				return;
			}
			if(quizElement.getChildren().contains(hideBtn))
			{
				quizElement.getChildren().remove(hideBtn);
				quizElement.getChildren().add(revealBtn);
			}
			
			index++;
			System.out.format("Index: %s", index);
			System.out.println();
	
			System.out.format("Liste: %s", newQuiz.getMap());
			System.out.println();
			//Die Liste ohne Randomizer
			quizElementKey.setText(newQuiz.getKeyFromIndex(index));
			quizElementNumberLabel.setText((index + 1) + "/" + newQuiz.getSize());
			
				
		});
			
		//Wenn gedrï¿½ckt, wird der Index verringert und das Label wird auf den neuen Index gesetzt
		back.setOnAction((ActionEvent event) -> {
			if(index == 0)
			{
				return;
			}
			if(quizElement.getChildren().contains(hideBtn))
			{
				quizElement.getChildren().remove(hideBtn);
				quizElement.getChildren().add(revealBtn);
			}
			
			index--;
			System.out.format("Index: %s", index);
			System.out.println();
			
			System.out.format("Liste: %s", newQuiz.getMap());
			System.out.println();
			//Die Liste ohne Randomizer
			quizElementKey.setText(newQuiz.getKeyFromIndex(index));
			quizElementNumberLabel.setText((index + 1) + "/" + newQuiz.getSize());
			
				
		});
		
		writeValueBtn.setOnAction(e ->{
			Write_Value_C writeValueBtn = new Write_Value_C();
			try {
				writeValueBtn.quizUebernehmen(stage, newQuiz);
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
		
		quizElementKey.setStyle("-fx-font-size: 30");
		abpruefungLabel.setStyle("-fx-font-size: 20");
		
		
		randomBtn.setPrefSize(250, 200);
		
		
		writeValueBtn.setPrefSize(250, 200);
		
		
		multipleChoiceBtn.setPrefSize(250, 200);
		
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
		
		
		
		
		
		
		leftvbox.getChildren().addAll(randomBtn, writeValueBtn, multipleChoiceBtn);
		bottomhbox.getChildren().addAll(back, quizElementNumberLabel,  continueBtn);
		quizElement.getChildren().addAll(quizElementKey, revealBtn);
		
		stage.getIcons().add(new Image("file:Spengerkarten\\\\src\\\\spengerkarten_logo.png"));
		scene = new Scene(pane, 1500, 720);
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
		gradeLabel.setText("Note: " + note);
	}
	
	public void refreshRandomizer()
	{
		//Score wird zurï¿½ckgesetzt
		this.correct = 0;
		this.wrong = 0;
		correctAnswersLabel.setText("Richtige antworten: " + this.correct + "/" + this.newQuiz.getSize());
		wrongAnswersLabel.setText("Falsche antworten: " + this.wrong + "/" + this.newQuiz.getSize());
		percentageCountLabel.setText("Richtig in %: -");
		gradeLabel.setText("Note: -");
		
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
		quizElementKey.setText(keys.get(0).toString());
		quizElementNumberLabel.setText((index+1) + "/" + newQuiz.getSize());
	}
}
