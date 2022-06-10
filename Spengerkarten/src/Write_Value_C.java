import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Write_Value_C {
	Quiz newQuiz;
	int index = 0;
	BorderPane bp = new BorderPane();
	
	public void start(Stage stage) throws Exception
	{
		System.out.println("TEST!!!");
		
		
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
