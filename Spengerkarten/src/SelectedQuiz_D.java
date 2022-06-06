import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SelectedQuiz_D extends Application
{
	BorderPane pane;
	Scene scene;
	
	Button random;
	Button writeValue;
	Button multipleChoice;
	Button back;
	Button continueButton;
	
	private List keys;
	
	private boolean checkIfRand = false;
	
	private static int index = 0;
	
	
	
	public static void main(String[] args)
	{
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception
	{
		
		//Test Quiz Objekt
		Quiz newQuiz = new Quiz("quizname");
		
		newQuiz.addVocab("Erste", "EErste");
		newQuiz.addVocab("Zweite", "ZZweite");
		newQuiz.addVocab("Dritte", "DDritte");
		newQuiz.addVocab("Vierte", "VVierte");
		newQuiz.addVocab("Fuenfte", "FFuenfte");
		newQuiz.addVocab("Sechste", "SSechste");
		newQuiz.addVocab("Siebte", "SSiebte");
		
		//Die Buttons f�r die Art, wie man das Quiz angehen will
		VBox leftvbox = new VBox();
		//Ein Element des Quizes wird hier abgebildet
		HBox quizElement = new HBox();
		//Hier befinden sich die Buttons 'Weiter' und 'Zurueck'
		HBox bottomhbox = new HBox();
		//Zeigt den live-stand der bisherigen Antworten
		//VBox livestats = new VBox();
		
		pane = new BorderPane();
		MenuBar menuBar = new MenuBar();
		Menu home = new Menu("Home");
		menuBar.getMenus().add(home);
		
		this.random = new Button("Random");
		this.writeValue = new Button("Starten");
		this.multipleChoice = new Button("Multiple Choice");
		
		this.writeValue.setPrefSize(100, 30);
		this.random.setPrefSize(100, 30);
		this.multipleChoice.setPrefSize(100, 30);
		
		
		this.back = new Button("Zurueck");
		Label quizname = new Label(newQuiz.getName());
		this.continueButton = new Button("Weiter");
		
		/*
		Label livestatlable = new Label("Live stats:");
		Label correctAnswers = new Label("Richtige antworten: ");
		Label wrongAnswers = new Label("Falsche antworten: ");
		Label percentage = new Label("Richtig in %: ");
		Label grade = new Label("Note: ");
		*/
		
		//Inhalt des Elementes
		Label quizKey = new Label(newQuiz.getKeyFromIndex(index));
		
		random.setOnAction((ActionEvent event) -> {
			this.checkIfRand = true;
			index = 0;
			keys = new ArrayList(newQuiz.getMap().keySet());
			Collections.shuffle(keys);
			quizKey.setText(keys.get(0).toString());
		});
		
		//Wenn gedr�ckt, wird der Index erh�ht und das Label wird auf den neuen Index gesetzt
		
			
		
			
			
			continueButton.setOnAction((ActionEvent event) -> {
				if(index == newQuiz.getSize() - 1)
				{
					return;
				}
				if(checkIfRand)
				{
					index++;
					System.out.println(index);
					System.out.println(keys);
					quizKey.setText(keys.get(index).toString());
				} else
				{
					index++;
					System.out.println(index);
					quizKey.setText(newQuiz.getKeyFromIndex(index)); 
				}
				
			});
			
			//Wenn gedr�ckt, wird der Index verringert und das Label wird auf den neuen Index gesetzt
			back.setOnAction((ActionEvent event) -> {
				if(index == 0)
				{
					return;
				}
				if(checkIfRand)
				{
					index--;
					System.out.println(index);
					System.out.println(keys);
					quizKey.setText(keys.get(index).toString());
				} else
				{
					index--;
					System.out.println(index);
					quizKey.setText(newQuiz.getKeyFromIndex(index)); 
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
		//pane.setRight(livestats);
		
		
		
		leftvbox.getChildren().addAll(random, writeValue, multipleChoice);
		bottomhbox.getChildren().addAll(back, quizname,  continueButton);
		quizElement.getChildren().addAll(quizKey);
		//livestats.getChildren().addAll(livestatlable, correctAnswers, wrongAnswers, percentage, grade);
		
		scene = new Scene(pane, 1000, 720);
		
		stage.setScene(scene);
		stage.show();
		
	}
	
	
}
