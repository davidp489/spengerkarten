import java.text.NumberFormat;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Write_Value_C extends Application{
	Quiz newQuiz;
	int index = 0;
	int correct = 0;
	int wrong = 0;
	int alreadyMade = 0;
	int note = 0;
	float percentage;
	
	public static void main(String[] args)
	{
		launch(args);
	}
	
	//Bin nicht mehr dazu gekommen wegen dem edit Button. Das war echt schwer
	public void start(Stage stage) throws Exception
	{
		BorderPane pane = new BorderPane();
		VBox vBoxCenter = new VBox(); //VBox center
		VBox vBoxRight = new VBox(); //VBox rechts
		HBox hBoxCenter = new HBox();
		
		
		Label labelHome = new Label("Home");
		MenuBar menuBar = new MenuBar();
		Menu home = new Menu("", labelHome);
		menuBar.getMenus().add(home);
		labelHome.setStyle("-fx-font-size: 14");
		Label showText = new Label();
		TextField writeAnswer = new TextField();
		Button checkButton = new Button("Check");
		Button okButton = new Button("Ok");
		
		//Stats
		Label liveStatLabel = new Label("Live stats: " + this.newQuiz.getName());
		Label correctAnswers = new Label("Richtige antworten: " + this.correct + "/" + this.newQuiz.getSize());
		Label wrongAnswers = new Label("Falsche antworten: " + this.wrong + "/" + this.newQuiz.getSize());
		Label percentageCount = new Label("Richtig in %: -");
		Label grade = new Label("Note: -");
		Set<String> keyList = new HashSet<String>();
		
		
		getRandKey(keyList, showText);
		okButton.setVisible(false);
		checkButton.setOnAction(checkEvent -> {
			
			if(newQuiz.getValue(showText.getText()).equalsIgnoreCase(writeAnswer.getText())) //Checkt ob der Key aus dem Label mit dem Value aus dem eingegebenen Text zusammen passt
			{
				getRandKey(keyList, showText);
				writeAnswer.setText("");
				correct++;
				correctAnswers.setText("Richtige antworten: " + this.correct + "/" + this.newQuiz.getSize());
				if(index == newQuiz.getSize() - 1)
				{
					calculateGrade(percentageCount, grade);
				}
				else
				{
				index++;
				}
		}
			else
			{
				if(index == newQuiz.getSize() - 1)
				{
					okButton.setVisible(true);
					writeAnswer.setText("");
					writeAnswer.setDisable(true);
					wrong++;
					wrongAnswers.setText("Falsche antworten: " + this.wrong + "/" + this.newQuiz.getSize());
					index++;
					showText.setText(showText.getText() + " = " + newQuiz.getValue(showText.getText()));
					calculateGrade(percentageCount, grade);
					checkButton.setDisable(true);
					okButton.setOnAction(okEvent ->{
						checkButton.setDisable(true);
						showText.setText("Du bist am Ende angelangt, rechts siehst du dein Ergebnis");
					});
				}
				else
				{
					okButton.setVisible(true);
					checkButton.setDisable(true);
					writeAnswer.setText("");
					wrong++;
					wrongAnswers.setText("Falsche antworten: " + this.wrong + "/" + this.newQuiz.getSize());
					index++;
					showText.setText(showText.getText() + " = " + newQuiz.getValue(showText.getText()));
					okButton.setOnAction(okEvent -> {
						getRandKey(keyList, showText);
						checkButton.setDisable(false);
						okButton.setVisible(false);
					});
				}
			}
		});
		
		
		
		
		
		//Main
		pane.setCenter(vBoxCenter);
		pane.setRight(vBoxRight);
		
		//Top
		pane.setTop(menuBar);
		
		//Center
		vBoxCenter.getChildren().addAll(showText, writeAnswer);
		vBoxCenter.getChildren().add(hBoxCenter);
		hBoxCenter.getChildren().addAll(checkButton, okButton);
		
		//Right
		vBoxRight.getChildren().addAll(liveStatLabel, correctAnswers, wrongAnswers, percentageCount, grade);
		
		//Styling
		showText.setStyle("-fx-font-size: 14");
		writeAnswer.setStyle("-fx-font-size: 14");
		writeAnswer.setPadding(new Insets(10, 10, 10, 10));
		checkButton.setStyle("-fx-font-size: 14");
		vBoxCenter.setPadding(new Insets(70, 10, 10, 150));
		vBoxRight.setStyle("-fx-background-color: #75c9ea");
		showText.setStyle("-fx-font-size: 20");
		showText.setAlignment(Pos.CENTER);
		
		
		//Test
		Scene scene = new Scene(pane, 2000, 1000);
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
	
	private void getRandKey(Set<String> keyList, Label showText)
	{
		Random rand = new Random();
		while(true)
		{
			String randKey = newQuiz.getKeyFromIndex(rand.nextInt(newQuiz.getSize()));
			if(!keyList.contains(randKey))
			{
				keyList.add(randKey);
				showText.setText(randKey);
				break;
			}
			else
			{
				randKey = newQuiz.getKeyFromIndex(rand.nextInt(newQuiz.getSize()));
			}
		}
	}
	
	private void calculateGrade(Label percentageCount, Label grade)
	{
		this.percentage = (float) correct / newQuiz.getSize();
		
		//Formattiert die Dezimalzahl in Prozent
		NumberFormat defaultFormat = NumberFormat.getPercentInstance();
		String percentageFormatted = defaultFormat.format(percentage);
		
		percentageCount.setText("Richtig in %: " + percentageFormatted);
		
		//Bestimmung der Note
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
	
	
	
}
