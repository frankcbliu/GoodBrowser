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
 * @author ���ӱ�
 * @time 2019��6��23��
 */
public class MainController implements Initializable {
	private static final String BLANK = "_blank";
    private static final String TARGET = "target";
    private static final String CLICK = "click";
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
//	@FXML
//	private WebView browserPage = new WebView();//��Ⱦҳ��
	@FXML
	private Button changeSkinButton;//����Ƥ����ť
	private boolean isYoungTheme = false;//��ǰƤ��״̬
	
	private WebHistory history;
	private ObservableList<Entry> his;
	
	private WebView browserPage;
	@FXML
	private Button testButton;//����ר�ð�ť
	
	private static Stage primaryStage;
	
	private Tab currentTab;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
//		��ʼ���ؼ�
//		String url = getClass().getResource("index.html").toExternalForm();
		homePage();//��ʼ����ҳ
		currentTab = tabPane.getTabs().get(0);//��ʼ����ǰ��ǩ
		tabPane.setTabClosingPolicy(TabClosingPolicy.ALL_TABS);//��ǩ��ӹرչ���
		

		// tab�л�������
		tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
			@Override
			public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
				currentTab = newValue;
				update(newValue);//���½���
			};
		});
		
		
		primaryStage = Main.getStage();
		selfAdaption();//����Ӧ����

		txtfld_url.setOnMouseClicked((event) -> {//����ȫѡurl
            if (txtfld_url.getText().length() == txtfld_url.getCaretPosition()) {
            	txtfld_url.selectAll();
            }
        });
	}

	/**
	 * ���Ժ���
	 */
	public void test() {
		System.out.println("--------------������----------------");
		
		System.out.println("------------------------------------");
	}
	
	/**
	 * �ı�tab��ǩ����Ӧ�ı���ʾ����
	 * @param newValue
	 */
	private void update(Tab newValue) {
		System.out.println("selected changed");
		int index = tabPane.getTabs().indexOf(newValue);
//		System.out.println("index"+index);
		browserPage = MyTab.getView(index);//����webview ����
		txtfld_url.setText(browserPage.getEngine().getLocation());//����URL��ַ
//		System.out.println("url:"+browserPage.getEngine().getLocation());
		history = browserPage.getEngine().getHistory();//������ʷ��¼
		his = history.getEntries();//������ʷ��¼
	}

	public void setHistory(WebHistory history) {
		this.history = history;
	}
	/**
	 *���±�ǩҳ 
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
*							����ɵĹ���
*
***************************************************************************/	
	/**
	 * ��װ���ط�����ͬʱ���µ�ַ��
	 * @param url
	 */
	private void load(String url) {
		browserPage.getEngine().load(url);//����url
		txtfld_url.setText(url);//����url��ַ��
		updateTabName();//���±�ǩ��
	}
	
	/**
	 * ��ת���������URL
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
	 * ��ת����ҳ
	 */
	@FXML
	public void homePage() {
		MyTab homeTab = new MyTab(this);
		homeTab.setText("�ٶ�һ��");
		tabPane.getTabs().add(homeTab);
		tabPane.getSelectionModel().select(homeTab);
		update(homeTab);
	}
	
	/**
	 * ��ת����һҳ
	 */
	@FXML
	public void prePage() {
		int index = history.getCurrentIndex();
		if (index == 0) {
			System.out.println("û����һҳ��");
			return;
		}
		if (index>0) {
			history.go(-1);//��һҳ
			txtfld_url.setText(his.get(history.getCurrentIndex()).getUrl());//����url��ַ��
		}
	}
	
	/**
	 * ��ת����һҳ
	 */
	@FXML
	public void nextPage() {
		int size = history.getEntries().size();
		int index = history.getCurrentIndex();
		if (index >= size-1) {
			System.out.println("û����һҳ��");
			return;
		}else{
			history.go(1);//��һҳ
			txtfld_url.setText(his.get(history.getCurrentIndex()).getUrl());//����url��ַ��
		}
	}
	
	/**
	 * ����Ӧ����
	 */
	private void selfAdaption() {
//		�������Ӧ
  		vBox.prefWidthProperty().bind(pane.widthProperty());
  		hBox.prefWidthProperty().bind(pane.widthProperty());
  		tabPane.prefWidthProperty().bind(pane.widthProperty());
//  		acPane.prefWidthProperty().bind(tabPane.widthProperty());
//  		browserPage.prefWidthProperty().bind(acPane.widthProperty());
//  		�߶�����Ӧ
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
////		  		�������Ӧ
//		  		vBox.prefWidthProperty().bind(pane.widthProperty());
//		  		hBox.prefWidthProperty().bind(pane.widthProperty());
//		  		tabPane.prefWidthProperty().bind(pane.widthProperty());
//		  		acPane.prefWidthProperty().bind(tabPane.widthProperty());
//		  		browserPage.prefWidthProperty().bind(acPane.widthProperty());
////		  		vBox.setPrefHeight(pane.getHeight());
////		  		acPane.setPrefHeight(tabPane.getHeight());
////		  		browserPage.setPrefHeight(acPane.getHeight());
////		  		�߶�����Ӧ
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
	
	
}
