import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Multiple_Choice_L {

	private int index = 0;
	private static Quiz quiz;
	private Label key;
	private Button leftTop, rightTop, rightBottom, leftBottom;
	private int wrong = 0, right = 0;
	private Label livestLabel, correctAnswLabel, wrongAnsLabel, percentaLabel, grade;
	
	
	public void start(Stage stage) throws Exception {
		MenuBar bar = new MenuBar();
		Label labelHome = new Label("Home");
		Menu home = new Menu(null, labelHome);
		home.setStyle("-fx-font-size: 14px");
		labelHome.setOnMouseClicked(e->{
			try {
				new Main_Page_L().start(stage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		bar.getMenus().add(home);
		BorderPane borderPane = new BorderPane();
		borderPane.setTop(bar);
		FlowPane pane = new FlowPane(Orientation.VERTICAL);
		pane.setPrefSize(500, 500);
		pane.setStyle("-fx-background-color: grey");
		HBox box = new HBox(pane);
		box.setStyle("-fx-background-color: lightgrey");
		box.setAlignment(Pos.CENTER);
		borderPane.setCenter(box);
		key = new Label("key");
		FlowPane paneForKey = new FlowPane();
		paneForKey.setAlignment(Pos.CENTER);
		paneForKey.getChildren().add(key);
		key.setAlignment(Pos.CENTER);
		key.setStyle("-fx-font-size: 30px");
		leftTop = new Button("1");
		leftTop.setPrefSize(175, 30);
		leftTop.setAlignment(Pos.CENTER);
		rightTop = new Button("2");
		rightTop.setPrefSize(175, 30);
		rightTop.setAlignment(Pos.CENTER);
		leftBottom = new Button("3");
		leftBottom.setPrefSize(175, 30);
		leftBottom.setAlignment(Pos.CENTER);
		rightBottom = new Button("4");
		rightBottom.setPrefSize(175, 30);
		rightBottom.setAlignment(Pos.CENTER);
		pane.getChildren().add(paneForKey);
		GridPane gPane = new GridPane();
		gPane.setStyle("-fx-font-size: 20px");
		gPane.add(leftTop, 0, 0);
		gPane.add(rightTop, 1, 0);
		gPane.add(leftBottom, 0, 1);
		gPane.add(rightBottom, 1, 1);
		gPane.setVgap(50);
		gPane.setHgap(50);
		FlowPane paneForAnswers = new FlowPane();
		pane.getChildren().add(paneForAnswers);
		paneForAnswers.getChildren().add(gPane);
		paneForAnswers.setAlignment(Pos.CENTER);
		pane.setVgap(100);
		pane.setAlignment(Pos.CENTER);
		
		leftTop.setOnAction(e->{
			// check if already done quiz
			if(index >= quiz.getSize()) {
				return;
			}
			String answer = leftTop.getText();
			if(answer.equals(quiz.getValue(quiz.getKeyFromIndex(index)))) {
				pressedRightAnswer(true);
			}else {
				pressedRightAnswer(false);
			}
		});
		rightTop.setOnAction(e->{
			// check if already done quiz
			if(index >= quiz.getSize()) {
				return;
			}
			String answer = rightTop.getText();
			if(answer.equals(quiz.getValue(quiz.getKeyFromIndex(index)))) {
				pressedRightAnswer(true);
			}else {
				pressedRightAnswer(false);
			}
		});
		leftBottom.setOnAction(e->{
			// check if already done quiz
			if(index >= quiz.getSize()) {
				return;
			}
			String answer = leftBottom.getText();
			if(answer.equals(quiz.getValue(quiz.getKeyFromIndex(index)))) {
				pressedRightAnswer(true);
			}else {
				pressedRightAnswer(false);
			}
		});
		rightBottom.setOnAction(e->{
			// check if already done quiz
			if(index >= quiz.getSize()) {
				return;
			}
			String answer = rightBottom.getText();
			if(answer.equals(quiz.getValue(quiz.getKeyFromIndex(index)))) {
				pressedRightAnswer(true);
			}else {
				pressedRightAnswer(false);
			}
		});
		
		livestLabel = new Label("Live stats: " + this.quiz.getName());
		correctAnswLabel = new Label("Richtige antworten: " + this.right + "/" + this.quiz.getSize());
		wrongAnsLabel = new Label("Falsche antworten: " + this.wrong + "/" + this.quiz.getSize());
		percentaLabel = new Label("Richtig in %: -");
		grade = new Label("Note: -");
		VBox livestats = new VBox();
		livestats.getChildren().addAll(livestLabel, 
				correctAnswLabel, wrongAnsLabel, percentaLabel, 
				grade);
		livestats.setStyle("-fx-font-size: 25px;-fx-background-color: #8cbdbc;");
		borderPane.setRight(livestats);
		
		Scene scene = new Scene(borderPane, 1500, 720);
		stage.setScene(scene);
		stage.show();
		
		// setup first question
		setupQuestion(index);
	}
	
	public void quizUebergabe(Stage stage, Quiz quiz) {
		this.quiz = quiz;
		index = 0;
		try {
			if(quiz.getSize()>=4) {
				start(stage);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static String[] getAnswerPossibilities(String key) {
		ArrayList<String> list = new ArrayList<>();
		// adding right value
		list.add(quiz.getValue(key));
		
		// adding other random values
		Random rand = new Random();
		for(int i=0;i<3;i++) {
			int index;
			while(true) {
				index = rand.nextInt(quiz.getSize());
				if(!list.contains(quiz.getValue(quiz.getKeyFromIndex(index)))) {
					break;
				}
			}
			list.add(quiz.getValue(quiz.getKeyFromIndex(index)));
		}
		
		// shuffle list
		Collections.shuffle(list);

		// return as array
		String[] returningArray = new String[list.size()];
		for(int i = 0;i<returningArray.length;i++) {
			returningArray[i] = list.get(i);
		}
		
		return returningArray;
	}
	
	private void setupQuestion(int index) {
		String key = quiz.getKeyFromIndex(index);
		String[] answerPossibilities = getAnswerPossibilities(key);
		
		this.key.setText(key);
		leftTop.setText(answerPossibilities[0]);
		rightTop.setText(answerPossibilities[1]);
		leftBottom.setText(answerPossibilities[2]);
		rightBottom.setText(answerPossibilities[3]);
	}
	
	private void pressedRightAnswer(boolean right) {
		index++;
		if(right){
			System.out.println("korrekt!");
			this.right++;
		}else {
			System.out.println("falsch!");
			this.wrong++;
		}
		refreshLiveStats();
		if(index < quiz.getSize()) {
			// setup next karteikarte
			setupQuestion(index);
		}else {
			// show results
			showResults();
		}
	}
	
	private void refreshLiveStats() {
		livestLabel.setText("Live stats: " + this.quiz.getName());
		correctAnswLabel.setText("Richtige antworten: " + this.right + "/" + this.quiz.getSize());
		wrongAnsLabel.setText("Falsche antworten: " + this.wrong + "/" + this.quiz.getSize());
	}
	
	private void showResults() {
		double percentage = (double)right/quiz.getSize()*100;
		int roundedPercentage = (int) percentage;
		
		percentaLabel.setText("Richtig in %: "+roundedPercentage+"%");
		
		// note bestimmen
		int grade = 0;
		if(roundedPercentage < 50) grade = 5;
		else if(roundedPercentage >= 50 && roundedPercentage < 65) grade = 4;
		else if(roundedPercentage >= 65 && roundedPercentage < 80) grade = 3;
		else if(roundedPercentage >= 80 && roundedPercentage < 90) grade = 2;
		else if(roundedPercentage >= 0.9) grade = 1;
		
		this.grade.setText("Note: "+grade);
		
		this.key.setText("Fertig! Rechts steht Ihr Ergebnis!");
	}

}





