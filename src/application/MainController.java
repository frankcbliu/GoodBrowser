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

	private List<String> urls;//存储所有url
	private int index;//存储当前url位置
	
//	自适应界面
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
	
	
//	定义组件
	@FXML
	private Button btn_go;//浏览按钮
	@FXML
	private TextField txtfld_url;//url文本框
	@FXML
	private Button preURL;//上一页
	@FXML
	private Button nextURL;//下一页
	@FXML
	private Button homeURL;//主页
	@FXML
	private WebView browserPage = new WebView();//渲染页面
	@FXML
	private Button changeSkinButton;
	
	private boolean isYoungTheme = false;//当前皮肤状态
	@Override
	public void initialize(URL location, ResourceBundle resources) {
//		初始化数据
		urls = new ArrayList<>();
		index = -1;
//		初始化控件
		String url = getClass().getResource("index.html").toExternalForm();
//		browser.load(url);
		browserPage.getEngine().load(url);
		System.out.println(resources);
		selfAdaption();//自适应界面
	}

	/**
	 * 更换皮肤
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
	 * 跳转到所输入的URL
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
	 * 跳转到首页
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
	 * 跳转到上一页
	 * @param event
	 */
	@FXML
	public void prePage(ActionEvent event) {
		if (index == -1) {
			System.out.println("没有上一页了");
			return;
		}
		if (index>0) {
			index--;
		}
		browserPage.getEngine().load(urls.get(index));
		System.out.println(urls.get(index));
	}
	
	/**
	 * 跳转到下一页
	 * @param event
	 */
	@FXML
	public void nextPage(ActionEvent event) {
		if (index == -1) {
			System.out.println("没有下一页了");
			return;
		}
		if (index < urls.size()-1) {
			index++;
		}
		browserPage.getEngine().load(urls.get(index));
		System.out.println(urls.get(index));
	}
	
	/**
	 * 自适应界面
	 */
	private void selfAdaption() {
//		宽度自适应
		vBox.prefWidthProperty().bind(pane.widthProperty());
		hBox.prefWidthProperty().bind(pane.widthProperty());
		tabPane.prefWidthProperty().bind(pane.widthProperty());
		acPane.prefWidthProperty().bind(tabPane.widthProperty());
		browserPage.prefWidthProperty().bind(acPane.widthProperty());
//		高度自适应
		vBox.prefHeightProperty().bind(pane.heightProperty());
		acPane.prefHeightProperty().bind(tabPane.heightProperty());
		browserPage.prefHeightProperty().bind(acPane.heightProperty());
	}
}
