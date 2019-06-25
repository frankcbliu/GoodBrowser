package application;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.NodeList;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.html.HTMLAnchorElement;

import com.sun.media.jfxmedia.events.NewFrameEvent;

import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.PopupFeatures;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

public class MyTab extends Tab{
	private static final String BLANK = "_blank";
    private static final String TARGET = "target";
    private static final String CLICK = "click";
	private MainController controller;
	private final WebView webView;
	private static String HOME_URL = MyUtil.HOME_URL;
	
	private static List<WebView> views = new ArrayList<>();
	
	public MyTab(MainController controller) {
		this(HOME_URL,controller);
	}
	
	public MyTab(String url, MainController controller) {
		this.controller = controller;
		webView = new WebView();
		
//		页面内使用新窗口打开
		webView.getEngine().setCreatePopupHandler(new Callback<PopupFeatures, WebEngine>() {
	        @Override
	        public WebEngine call(PopupFeatures p) {
	        	MyTab newTab = new MyTab(controller);
	        	getTabPane().getTabs().add(newTab);
	        	getTabPane().getSelectionModel().select(newTab);
	            return newTab.webView.getEngine();
	        }
	    });
		
		views.add(webView);
		AnchorPane.setLeftAnchor(webView, 0D);
        AnchorPane.setRightAnchor(webView, 0D);
        AnchorPane.setBottomAnchor(webView, 0D);
        AnchorPane.setTopAnchor(webView, 0D);
        setContent(new AnchorPane(webView));
        webView.getEngine().load(url);
        addTab();
        
	}
	
	public void addTab() {
//		添加标签
		webView.getEngine().documentProperty().addListener((observable, ov, document) -> {
            if (document != null) {
            	NodeList list = document.getElementsByTagName("title");
            	StringBuilder title = new StringBuilder("");
            	if (list.getLength()>0) {
            		System.out.println(list.item(0).getBaseURI());
            		System.out.println(list.item(0).getTextContent());
//            		System.out.println(list.item(0).getUserData("innerText"));
            		 title.append(list.item(0).getTextContent());
				}
                NodeList nodeList = document.getElementsByTagName("a");
                for (int i = 0; i < nodeList.getLength(); i++) {
                    org.w3c.dom.Node node = nodeList.item(i);
                    setText(title.toString());
                    org.w3c.dom.Node targetNode = node.getAttributes().getNamedItem(TARGET);
                    if (targetNode != null) {
                        String target = targetNode.getTextContent();
                        if (BLANK.equals(target)) {//页面中target="_blank"的在新标签中打开
                            EventTarget eventTarget = (EventTarget) node;
                            eventTarget.addEventListener(CLICK, (Event evt) -> {
                            	HTMLAnchorElement anchorElement = (HTMLAnchorElement) evt.getCurrentTarget();
                                String href = anchorElement.getHref();
                                MyTab mt = new MyTab(href, controller);
                                mt.setText(title.toString());
                                getTabPane().getTabs().add(mt);
                                getTabPane().getSelectionModel().select(mt);
                                evt.preventDefault();
                            }, false);
                        }
                    }
                }
            }
        });
	}

	
	public static WebView getView(int index) {
		return views.get(index);
	}
}
