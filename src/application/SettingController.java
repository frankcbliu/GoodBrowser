package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

public class SettingController implements Initializable {

	@FXML
	private RadioButton baidu;

	@FXML
	private RadioButton bing;

	@FXML
	private RadioButton defaultColor;

	@FXML
	private RadioButton blueColor;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		ToggleGroup groupIndex = new ToggleGroup();// 初始化主页选择
		baidu.setUserData(0);
		bing.setUserData(1);
		baidu.setToggleGroup(groupIndex);
		bing.setToggleGroup(groupIndex);

		if (MyUtil.INDEX_MODE.equals("0")) {
			baidu.setSelected(true);
		} else if (MyUtil.INDEX_MODE.equals("1")) {
			bing.setSelected(true);
		}

		ToggleGroup groupColor = new ToggleGroup();// 初始化颜色选择
		defaultColor.setUserData(0);
		blueColor.setUserData(1);
		defaultColor.setToggleGroup(groupColor);
		defaultColor.setSelected(true);
		blueColor.setToggleGroup(groupColor);

		if (MyUtil.COLOR_MODE.equals("0")) {
			defaultColor.setSelected(true);
		} else if (MyUtil.COLOR_MODE.equals("1")) {
			blueColor.setSelected(true);
		}

		
		// 添加选中监听事件
		groupIndex.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
				if (groupIndex.getSelectedToggle() != null) {
					System.out.println("更新主页");
					int index = (int) groupIndex.getSelectedToggle().getUserData();
					MyUtil.INDEX_MODE = index+"";
					if(MyUtil.INDEX_MODE.equals("0")) {//设置首页为百度
						MyUtil.HOME_URL = "http://www.baidu.com";
					}else if (MyUtil.INDEX_MODE.equals("1")) {//设置首页为必应
						MyUtil.HOME_URL = "http://cn.bing.com";
					}
				}
			}
		});
		
		groupColor.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
				if (groupColor.getSelectedToggle() != null) {
					System.out.println("更新颜色");
					int index = (int) groupColor.getSelectedToggle().getUserData();
					MyUtil.COLOR_MODE = index+"";
				}
			}
		});
	}
}
