package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class MainController implements Initializable {

	private List<String> urls;//�洢����url
	private int index;//�洢��ǰurlλ��
	
//	����Ӧ����
	@FXML
	private AnchorPane pane;
	@FXML
	private VBox vBox;
	@FXML
	private ToolBar toolBar; 
	@FXML
	private HBox hBox;
	@FXML
	private TabPane tabPane;
	@FXML
	private AnchorPane acPane;
	
	
//	�������
	@FXML
	private Button btn_go;//�����ť
	@FXML
	private TextField txtfld_url;//url�ı���
	@FXML
	private Button preURL;//��һҳ
	@FXML
	private Button nextURL;//��һҳ
	@FXML
	private Button homeURL;//��ҳ
	@FXML
	private WebView browserPage = new WebView();//��Ⱦҳ��
	@FXML
	private Button changeSkinButton;
	
	private boolean isYoungTheme = false;//��ǰƤ��״̬
	@Override
	public void initialize(URL location, ResourceBundle resources) {
//		��ʼ������
		urls = new ArrayList<>();
		index = -1;
//		��ʼ���ؼ�
		String url = getClass().getResource("index.html").toExternalForm();
//		browser.load(url);
		browserPage.getEngine().load(url);
		System.out.println(resources);
		selfAdaption();//����Ӧ����
	}

	/**
	 * ����Ƥ��
	 */
	public void changeSkin() {
		isYoungTheme = !isYoungTheme;
		if (isYoungTheme) {
			Main.scene.getStylesheets().add(getClass().getResource("youngTheme.css").toExternalForm());
		}else {
			Main.scene.getStylesheets().remove(getClass().getResource("youngTheme.css").toExternalForm());
		}
		
	}
	/**
	 * ��ת���������URL
	 * @param event
	 */
	@FXML
	public void goToURL(ActionEvent event) {
		String targetURL = txtfld_url.getText();
		if (targetURL.equals("")) {
			targetURL = "about:blank";
		}
		urls.add(targetURL);
		index++;
		browserPage.getEngine().load(urls.get(index));
		System.out.println(urls.get(index));
	}
	
	/**
	 * ��ת����ҳ
	 * @param event
	 */
	@FXML
	public void homePage(ActionEvent event) {
		String homeURL = "http://www.baidu.com";
		if (index == -1) {
			index++;
			urls.add(homeURL);
		}
		if (!urls.get(index).equals(homeURL)) {
			urls.add(homeURL);
			index = urls.size()-1;
		}
		browserPage.getEngine().load(urls.get(index));
		System.out.println(urls.get(index));
	}
	
	/**
	 * ��ת����һҳ
	 * @param event
	 */
	@FXML
	public void prePage(ActionEvent event) {
		if (index == -1) {
			System.out.println("û����һҳ��");
			return;
		}
		if (index>0) {
			index--;
		}
		browserPage.getEngine().load(urls.get(index));
		System.out.println(urls.get(index));
	}
	
	/**
	 * ��ת����һҳ
	 * @param event
	 */
	@FXML
	public void nextPage(ActionEvent event) {
		if (index == -1) {
			System.out.println("û����һҳ��");
			return;
		}
		if (index < urls.size()-1) {
			index++;
		}
		browserPage.getEngine().load(urls.get(index));
		System.out.println(urls.get(index));
	}
	
	/**
	 * ����Ӧ����
	 */
	private void selfAdaption() {
//		�������Ӧ
		vBox.prefWidthProperty().bind(pane.widthProperty());
		hBox.prefWidthProperty().bind(pane.widthProperty());
		tabPane.prefWidthProperty().bind(pane.widthProperty());
		acPane.prefWidthProperty().bind(tabPane.widthProperty());
		browserPage.prefWidthProperty().bind(acPane.widthProperty());
//		�߶�����Ӧ
		vBox.prefHeightProperty().bind(pane.heightProperty());
		acPane.prefHeightProperty().bind(tabPane.heightProperty());
		browserPage.prefHeightProperty().bind(acPane.heightProperty());
	}
}
