import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;

public class Main_Page_L extends Application{

	private int curCol = -1;
	private int curRow = 0;
	
	public static void main(String[] args) 
	{
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception 
	{
		MenuBar menuBar = new MenuBar();
		Menu home = new Menu("Home");
		home.setStyle("-fx-font-size: 14px");
		Menu New = new Menu("New");
		New.setStyle("-fx-font-size: 14px");
		Menu load = new Menu("Load");
		load.setStyle("-fx-font-size: 14px");
		menuBar.getMenus().addAll(home, New, load);
		
		VBox root = new VBox(menuBar);
		ScrollPane scrollPane = new ScrollPane();
		GridPane gridPane = new GridPane();
		scrollPane.setContent(gridPane);
		scrollPane.setFitToWidth(true);
		BorderPane pane = new BorderPane();
		pane.setTop(root);
		pane.setCenter(scrollPane);
		
		Scene scene = new Scene(pane, 1920, 1080);
		stage.setScene(scene);
		stage.show();
		testGridPane(gridPane);
	}
	
	public void übergeben(Stage stage, Quiz quiz) throws Exception {
		start(stage);
	}

	
	public void testGridPane(GridPane pane) {
		pane.setPadding(new Insets(100, 100, 100, 100));
		pane.setVgap(100);
		pane.setHgap(100);
		pane.setAlignment(Pos.CENTER);
		
		
		addQuiz(pane, "1");
		addQuiz(pane, "2");
		addQuiz(pane, "3");
		addQuiz(pane, "1");
		addQuiz(pane, "2");
		addQuiz(pane, "3");
		addQuiz(pane, "1");
		addQuiz(pane, "2");
		addQuiz(pane, "3");
		addQuiz(pane, "1");
		addQuiz(pane, "2");
		addQuiz(pane, "3");
		addQuiz(pane, "keks");
		addQuiz(pane, "2");
		addQuiz(pane, "3");
		addQuiz(pane, "1");
		addQuiz(pane, "2");
		addQuiz(pane, "3");
	} 
	
	private void addQuiz(GridPane pane, String quizName) {
		pane.add(createQuizGUI(quizName), curCol, curRow);
	}
	
	private Node createQuizGUI(String quizName) {
		Rectangle rectangle = new Rectangle();  
	      
	    //Setting the properties of the rectangle 
	    rectangle.setX(150.0f); 
	    rectangle.setY(75.0f); 
	    rectangle.setWidth(350.0f); 
	    rectangle.setHeight(250.0f); 
	       
	    //Setting the height and width of the arc 
	    rectangle.setArcWidth(30.0); 
	    rectangle.setArcHeight(20.0);
	    rectangle.setFill(Color.GREY);
	    
	    Text text = new Text(quizName);
	    text.setBoundsType(TextBoundsType.VISUAL);
        text.setStyle(
                "-fx-font-family: \"Times New Roman\";" +
                "-fx-font-style: italic;" +
                "-fx-font-size: 30px;"
        );
        
        StackPane layout = new StackPane();
        layout.getChildren().addAll(rectangle, text);
	    
        // managing rows and columns
        curCol++;
        if(curCol>2) {
        	curCol=0;
        	curRow++;
        }
        
	    return layout;
	}
}







