import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Multiple_Choice_L extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
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
		Label key = new Label("key");
		key.setStyle("-fx-font-size: 30px");
		Button leftTop = new Button("1");
		leftTop.setAlignment(Pos.TOP_LEFT);
		Button rightTop = new Button("2");
		rightTop.setAlignment(Pos.TOP_RIGHT);
		Button leftBottom = new Button("3");
		leftBottom.setAlignment(Pos.BOTTOM_LEFT);
		Button rightBottom = new Button("4");
		rightBottom.setAlignment(Pos.BOTTOM_RIGHT);
		pane.getChildren().add(key);
		AnchorPane aPane = new AnchorPane();
		aPane.getChildren().addAll(leftTop, rightTop, 
				leftBottom, rightBottom);
		pane.setVgap(50);
		pane.setHgap(50);
		pane.setAlignment(Pos.BASELINE_CENTER);
		
		Scene scene = new Scene(borderPane, 1500, 720);
		stage.setScene(scene);
		stage.show();
	}

}





