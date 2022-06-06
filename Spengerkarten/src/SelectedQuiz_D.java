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
	
	
	private static int index = 0;
	
	
	
	public static void main(String[] args)
	{
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception
	{
		Quiz newQuiz = new Quiz("quizname");
		
		newQuiz.addVocab("Erste", "EErste");
		newQuiz.addVocab("Zweite", "ZZweite");
		newQuiz.addVocab("Dritte", "DDritte");
		newQuiz.addVocab("Vierte", "VVierte");
		newQuiz.addVocab("F�nfte", "FF�nfte");
		newQuiz.addVocab("Sechste", "SSechste");
		newQuiz.addVocab("Siebte", "SSiebte");
		
		VBox leftvbox = new VBox();
		HBox quizElement = new HBox();
		HBox bottomhbox = new HBox();
		
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
		
		leftvbox.setSpacing(15);
		
		
		
		this.back = new Button("Zurück");
		this.continueButton = new Button("Weiter");
		
		Label quizKey = new Label(newQuiz.getKeyFromIndex(index));
		continueButton.setOnAction((ActionEvent event) -> {
			index++;
			System.out.println(index);
			quizKey.setText(newQuiz.getKeyFromIndex(index)); 
		});
		
		back.setOnAction((ActionEvent event) -> {
			index--;
			System.out.println(index);
			quizKey.setText(newQuiz.getKeyFromIndex(index)); 
		});
		
		
		
		
		leftvbox.setPadding(new Insets(100, 0, 200, 50));
		
		pane.setTop(menuBar);
		pane.setLeft(leftvbox);
		pane.setCenter(quizElement);
		pane.setBottom(bottomhbox);
		
		
		
		leftvbox.getChildren().addAll(random, writeValue, multipleChoice);
		bottomhbox.getChildren().addAll(back, continueButton);
		quizElement.getChildren().addAll(quizKey);
		
		scene = new Scene(pane, 1000, 720);
		
		stage.setScene(scene);
		stage.show();
		
	}
	
	
}
