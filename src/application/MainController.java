package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.web.WebHistory.Entry;
import javafx.stage.Stage;

import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.html.HTMLAnchorElement;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
/**
 * controller
 * @author 刘灿彬
 * @time 2019年6月23日
 */
public class MainController implements Initializable {
	private static final String BLANK = "_blank";
    private static final String TARGET = "target";
    private static final String CLICK = "click";
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
//	@FXML
//	private WebView browserPage = new WebView();//渲染页面
	@FXML
	private Button changeSkinButton;//更换皮肤按钮
	private boolean isYoungTheme = false;//当前皮肤状态
	
	private WebHistory history;
	private ObservableList<Entry> his;
	
	private WebView browserPage;
	@FXML
	private Button testButton;//测试专用按钮
	
	private static Stage primaryStage;
	
	private Tab currentTab;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
//		初始化控件
//		String url = getClass().getResource("index.html").toExternalForm();
		homePage();//初始化首页
		currentTab = tabPane.getTabs().get(0);//初始化当前标签
		tabPane.setTabClosingPolicy(TabClosingPolicy.ALL_TABS);//标签添加关闭功能
		

		// tab切换监听器
		tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
			@Override
			public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
				currentTab = newValue;
				update(newValue);//更新界面
			};
		});
		
		
		primaryStage = Main.getStage();
		selfAdaption();//自适应界面

		txtfld_url.setOnMouseClicked((event) -> {//设置全选url
            if (txtfld_url.getText().length() == txtfld_url.getCaretPosition()) {
            	txtfld_url.selectAll();
            }
        });
	}

	/**
	 * 测试函数
	 */
	public void test() {
		System.out.println("--------------测试中----------------");
		
		System.out.println("------------------------------------");
	}
	
	/**
	 * 改变tab标签，相应改变显示内容
	 * @param newValue
	 */
	private void update(Tab newValue) {
		System.out.println("selected changed");
		int index = tabPane.getTabs().indexOf(newValue);
//		System.out.println("index"+index);
		browserPage = MyTab.getView(index);//更新webview 内容
		txtfld_url.setText(browserPage.getEngine().getLocation());//更新URL地址
//		System.out.println("url:"+browserPage.getEngine().getLocation());
		history = browserPage.getEngine().getHistory();//更新历史记录
		his = history.getEntries();//更新历史记录
	}

	public void setHistory(WebHistory history) {
		this.history = history;
	}
	/**
	 *更新标签页 
	 */
	private void updateTabName() {
		browserPage.getEngine().documentProperty().addListener((observable, ov, document) -> {
			if (document != null) {
            	String title = document.getElementsByTagName("title").item(0).getTextContent();
                NodeList nodeList = document.getElementsByTagName("a");
                for (int i = 0; i < nodeList.getLength(); i++) {
                    org.w3c.dom.Node node = nodeList.item(i);
                    EventTarget t = (EventTarget) node;
                    t.addEventListener(CLICK, (Event evt) -> {
                    	currentTab.setText(title);
                    }, false);
                }
                currentTab.setText(title);
            }
        });
	}
/**************************************************************************
*
*							已完成的功能
*
***************************************************************************/	
	/**
	 * 封装加载方法，同时更新地址栏
	 * @param url
	 */
	private void load(String url) {
		browserPage.getEngine().load(url);//加载url
		txtfld_url.setText(url);//更新url地址栏
		updateTabName();//更新标签名
	}
	
	/**
	 * 跳转到所输入的URL
	 */
	@FXML
	public void goToURL() {
		String targetURL = txtfld_url.getText();
		if (targetURL.equals("")) {
			targetURL = "about:blank";
		}
		load(targetURL);
	}
	
	/**
	 * 跳转到首页
	 */
	@FXML
	public void homePage() {
		MyTab homeTab = new MyTab(this);
		homeTab.setText("百度一下");
		tabPane.getTabs().add(homeTab);
		tabPane.getSelectionModel().select(homeTab);
		update(homeTab);
	}
	
	/**
	 * 跳转到上一页
	 */
	@FXML
	public void prePage() {
		int index = history.getCurrentIndex();
		if (index == 0) {
			System.out.println("没有上一页了");
			return;
		}
		if (index>0) {
			history.go(-1);//上一页
			txtfld_url.setText(his.get(history.getCurrentIndex()).getUrl());//更新url地址栏
		}
	}
	
	/**
	 * 跳转到下一页
	 */
	@FXML
	public void nextPage() {
		int size = history.getEntries().size();
		int index = history.getCurrentIndex();
		if (index >= size-1) {
			System.out.println("没有下一页了");
			return;
		}else{
			history.go(1);//下一页
			txtfld_url.setText(his.get(history.getCurrentIndex()).getUrl());//更新url地址栏
		}
	}
	
	/**
	 * 自适应界面
	 */
	private void selfAdaption() {
//		宽度自适应
  		vBox.prefWidthProperty().bind(pane.widthProperty());
  		hBox.prefWidthProperty().bind(pane.widthProperty());
  		tabPane.prefWidthProperty().bind(pane.widthProperty());
//  		acPane.prefWidthProperty().bind(tabPane.widthProperty());
//  		browserPage.prefWidthProperty().bind(acPane.widthProperty());
//  		高度自适应
  		vBox.prefHeightProperty().bind(pane.heightProperty());
//  		acPane.prefHeightProperty().bind(tabPane.heightProperty());
//  		browserPage.prefHeightProperty().bind(acPane.heightProperty());
//		// create a listener
//		final ChangeListener<Number> listener = new ChangeListener<Number>()
//		{
//		  final Timer timer = new Timer(); // uses a timer to call your resize method
//		  TimerTask task = null; // task to execute after defined delay
//		  final long delayTime = 200; // delay that has to pass in order to consider an operation done
//
//		  @Override
//		  public void changed(ObservableValue<? extends Number> observable, Number oldValue, final Number newValue)
//		  {
//		    if (task != null)
//		    { // there was already a task scheduled from the previous operation ...
//		      task.cancel(); // cancel it, we have a new size to consider
//		    }
//
//		    task = new TimerTask() // create new task that calls your resize operation
//		    {
//		      @Override
//		      public void run()
//		      { 
////		    	  vBox.setPrefWidth(pane.getWidth());
////		    	  hBox.setPrefWidth(pane.getWidth());
////		    	  tabPane.setPrefWidth(pane.getWidth());
////		    	  acPane.setPrefWidth(tabPane.getWidth());
////		  		  browserPage.setPrefWidth(acPane.getWidth());
////		  		宽度自适应
//		  		vBox.prefWidthProperty().bind(pane.widthProperty());
//		  		hBox.prefWidthProperty().bind(pane.widthProperty());
//		  		tabPane.prefWidthProperty().bind(pane.widthProperty());
//		  		acPane.prefWidthProperty().bind(tabPane.widthProperty());
//		  		browserPage.prefWidthProperty().bind(acPane.widthProperty());
////		  		vBox.setPrefHeight(pane.getHeight());
////		  		acPane.setPrefHeight(tabPane.getHeight());
////		  		browserPage.setPrefHeight(acPane.getHeight());
////		  		高度自适应
//		  		vBox.prefHeightProperty().bind(pane.heightProperty());
//		  		acPane.prefHeightProperty().bind(tabPane.heightProperty());
//		  		browserPage.prefHeightProperty().bind(acPane.heightProperty());
//		        // here you can place your resize code
//		        System.out.println("resize to " + primaryStage.getWidth() + " " + primaryStage.getHeight());
//		      }
//		    };
//		    // schedule new task
//		    timer.schedule(task, delayTime);
//		  }
//		};
//
//		// finally we have to register the listener
//		primaryStage.widthProperty().addListener(listener);
//		primaryStage.heightProperty().addListener(listener);
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
	
	
}
