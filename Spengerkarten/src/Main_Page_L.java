import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class Main_Page_L extends Application{

	public static final String PATH = "S";
	private GridPane gridPane;
	private int curCol = -1;
	private int curRow = 0;
	Label noQuizzesLabel = new Label("Du hast noch kein Quiz erstellt!");
	
	public static void main(String[] args) 
	{
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception 
	{
		curCol = -1;
		curRow = 0;
		MenuBar menuBar = new MenuBar();
		Menu home = new Menu("Home");
		home.setStyle("-fx-font-size: 14px");
		Label labelNew = new Label("New");
		Menu New = new Menu(null, labelNew);
		labelNew.setOnMouseClicked(e->{
			try {
				new New_Quiz_C().start(stage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		New.setStyle("-fx-font-size: 14px");
		Menu load = new Menu("Load");
		MenuItem Import = new MenuItem("Import");
		load.getItems().add(Import);
		load.setStyle("-fx-font-size: 14px");
		Import.setOnAction(e->{
			// import selected file(s) via filechooser
			FileChooser chooser = new FileChooser();
			chooser.getExtensionFilters().add(new ExtensionFilter("CSV Files", "*.csv"));
			List<File> files = chooser.showOpenMultipleDialog(null);
			if(files!=null) {
				// for each file
				for(File file : files) {
					try {
						// get content of quiz as String
						Scanner s = new Scanner(file);
						StringBuilder builder = new StringBuilder();
						while(s.hasNext()) {
							builder.append(s.nextLine());
							builder.append("\n");
						}
						s.close();
						
						// write quiz to path
						File newFile = new File(PATH+"\\"+file.getName());
						FileWriter writer = new FileWriter(newFile);
						writer.write(builder.toString());
						writer.close();
						
						// delete old quiz file
						file.delete();
						
						// refresh
						start(stage);
						
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			}
		});
		Import.setStyle("-fx-font-size: 14px");
		menuBar.getMenus().addAll(home, New, load);
		
		VBox root = new VBox(menuBar);
		ScrollPane scrollPane = new ScrollPane();
		gridPane = new GridPane();
		scrollPane.setContent(gridPane);
		scrollPane.setFitToWidth(true);
		BorderPane pane = new BorderPane();
		pane.setTop(root);
		pane.setCenter(scrollPane);
		
		noQuizzesLabel.setStyle("-fx-font-size: 25px");
		gridPane.add(noQuizzesLabel, 0, 0);
		
		Scene scene = new Scene(pane, 1500, 720);
		stage.setScene(scene);
		stage.show();
		setupGridPane();
		// get all quizzes and add them
		getQuizzesFromPath(stage);
	}
	
	public void quizUebergabe(Stage stage, Quiz quiz) throws Exception {
		start(stage);
		
		// add currently made quiz
		addQuiz(stage, quiz);
		
		// save currently made quiz
		saveQuiz(PATH, quiz);
	}

	
	private void getQuizzesFromPath(Stage stage) {
		File[] quizzes = new File(PATH).listFiles();
		for(File f : quizzes) {
			// check if it's a .csv file
			if(f.isFile()) {
				int index = f.getName().lastIndexOf(".");
				String extension = f.getName().substring(index+1);
				if(extension.equals("csv")) {
					// is .csv file
					// add quiz
					addQuiz(stage, getQuiz(f.getAbsolutePath()));
				}
			}
		}
	}
	
	public void setupGridPane() {
		gridPane.setPadding(new Insets(100, 100, 100, 100));
		gridPane.setVgap(100);
		gridPane.setHgap(100);
		gridPane.setAlignment(Pos.CENTER);
	} 
	
	private void addQuiz(Stage stage, Quiz quiz) {
		// remove noQuizzesLabel if not done already
		gridPane.getChildren().remove(noQuizzesLabel);
		// add Quiz
		gridPane.add(createQuizGUI(stage, quiz), curCol, curRow);
	}
	
	private Node createQuizGUI(Stage stage, Quiz quiz) {
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
	    
	    Text text = new Text(quiz.getName());
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
        
        layout.setUserData(quiz);
        layout.setOnMouseClicked(e->{
        	Quiz clickedQuiz = (Quiz) layout.getUserData();
        	new SelectedQuiz_D().quizUebernehmen(stage, clickedQuiz);
        });
        
	    return layout;
	}
	
	private void saveQuiz(String path, Quiz quiz) {
		StringBuilder sb = new StringBuilder();
		// add hashmap as String
		for(int i = 0; i<quiz.getSize(); i++) {
			String key = quiz.getKeyFromIndex(i);
			sb.append(key);
			sb.append(",");
			sb.append(quiz.getValue(key));
			sb.append("\n");
		}
		
		String content = sb.toString();
		
		// save content to path
		try {
			PrintWriter writer = new PrintWriter(path+"\\"+quiz.getName()+".csv");
			writer.write(content);
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Quiz getQuiz(String path) {
		String nameWithEnding = new File(path).getName();
		Quiz quiz = new Quiz(nameWithEnding.substring(0, nameWithEnding.indexOf(".")));
		Scanner s = null;
		try {
			s = new Scanner(new File(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(s.hasNextLine()) {
			String line = s.nextLine();
			String key = line.substring(0, line.indexOf(",")); 
			String value = line.substring(line.indexOf(",")+1);
			quiz.addVocab(key, value);
		}
		s.close();
		return quiz;
	}
}







