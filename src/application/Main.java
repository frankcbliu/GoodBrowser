package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * ���������
 * @author ���ӱ�
 * @time 2019��6��23��
 */
public class Main extends Application {
	
	public static Scene scene;
	private static Stage stage;
	/**
	 * ��ʼ������
	 */
	@Override
	public void start(Stage primaryStage) {
		stage = primaryStage;
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/application/MainScene.fxml"));

			scene = new Scene(root,954,657);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			if (MyUtil.COLOR_MODE.equals("1")) {
				scene.getStylesheets().add(getClass().getResource("youngTheme.css").toExternalForm());
			}
//			the code is to open a file
//			FileChooser fileChooser = new FileChooser();
//			fileChooser.setTitle("Open Resource File");
//			fileChooser.showOpenDialog(primaryStage);
			
//			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.setTitle("GoodBrowser");//���ó������
			primaryStage.getIcons().add(new Image("./image/avatar.jpg"));//���ó���ͼ��
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * ������
	 * @author ���ӱ�
	 * @time 2019��6��22��
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	public static Stage getStage() {
		return stage;
	}
}
