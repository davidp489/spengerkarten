import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Write_Value_C extends Application{
	Quiz newQuiz;
	int index = 0;
	private int correct = 0;
	private int wrong = 0;
	private float percentage;
	
	public static void main(String[] args)
	{
		launch(args);
	}
	
	public void start(Stage stage) throws Exception
	{
		BorderPane pane = new BorderPane();
		VBox vBoxCenter = new VBox(); //VBox center
		VBox vBoxRight = new VBox(); //VBox rechts
		
		Label showText = new Label();
		TextField writeAnswer = new TextField();
		Button check = new Button("Check");
		
		Label stats = new Label();
		
		//Main
		pane.setCenter(vBoxCenter);
		pane.setRight(vBoxRight);
		
		//Center
		vBoxCenter.getChildren().addAll(showText, writeAnswer, check);
		
		//Right
		vBoxRight.getChildren().add(stats);
		
		//Styling
		showText.setStyle("-fx-font-size: 14");
		writeAnswer.setStyle("-fx-font-size: 14");
		writeAnswer.setPadding(new Insets(10, 10, 10, 10));
		check.setStyle("-fx-font-size: 14");
		
		
		Scene scene = new Scene(pane, 500, 500);
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
}
