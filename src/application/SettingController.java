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
		ToggleGroup groupIndex = new ToggleGroup();// ��ʼ����ҳѡ��
		baidu.setUserData(0);
		bing.setUserData(1);
		baidu.setToggleGroup(groupIndex);
		bing.setToggleGroup(groupIndex);

		if (MyUtil.INDEX_MODE.equals("0")) {
			baidu.setSelected(true);
		} else if (MyUtil.INDEX_MODE.equals("1")) {
			bing.setSelected(true);
		}

		ToggleGroup groupColor = new ToggleGroup();// ��ʼ����ɫѡ��
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

		
		// ���ѡ�м����¼�
		groupIndex.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
				if (groupIndex.getSelectedToggle() != null) {
					System.out.println("������ҳ");
					int index = (int) groupIndex.getSelectedToggle().getUserData();
					MyUtil.INDEX_MODE = index+"";
					if(MyUtil.INDEX_MODE.equals("0")) {//������ҳΪ�ٶ�
						MyUtil.HOME_URL = "http://www.baidu.com";
					}else if (MyUtil.INDEX_MODE.equals("1")) {//������ҳΪ��Ӧ
						MyUtil.HOME_URL = "http://cn.bing.com";
					}
				}
			}
		});
		
		groupColor.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
				if (groupColor.getSelectedToggle() != null) {
					System.out.println("������ɫ");
					int index = (int) groupColor.getSelectedToggle().getUserData();
					MyUtil.COLOR_MODE = index+"";
				}
			}
		});
	}
}
