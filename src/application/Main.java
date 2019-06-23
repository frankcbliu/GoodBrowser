package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;


public class Main extends Application {
	
	/**
	 * 初始化窗口
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/application/MainScene.fxml"));
//			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,954,657);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//			the code is to open a file
//			FileChooser fileChooser = new FileChooser();
//			fileChooser.setTitle("Open Resource File");
//			fileChooser.showOpenDialog(primaryStage);
//			primaryStage.setTitle("GoodBrowser");

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 主函数
	 * @author 刘灿彬
	 * @time 2019年6月22日
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
