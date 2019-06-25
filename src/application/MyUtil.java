package application;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

/**
 * ������
 * @author ���ӱ�
 * @time 2019��6��25��
 */
public class MyUtil {
	private static final String DIR = System.getProperty("user.home") + "/Downloads/GoodBrowser/"; 
	public static String HOME_URL;//��ҳ
	public static String FILE_DOWNLOAD_SHOW;//����ҳ����Ϣ�洢�ļ���·��
	public static String FILE_SETTING_SHOW;//����ҳ����Ϣ�洢�ļ���·��
	private static final String downloadTXT = "";// index filename currentSize size   
	private static final String settingTXT = "0 0"; // homeurl
	public static String INDEX_MODE;//��ҳ
	public static String COLOR_MODE;//��ɫ
	/**
	 * ��ʼ�������Ϣ
	 */
	public static void initSetting() {
		FILE_DOWNLOAD_SHOW = DIR + "config/downloadSetting.txt";
		FILE_SETTING_SHOW = DIR + "config/setting.txt";
		try {//	��ֹ�ļ������ڵ����
			File dir = (new File(DIR+"config"));
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File file =new File(FILE_DOWNLOAD_SHOW);
			if(!file.exists()){
				file.createNewFile();
			}
			file  =new File(FILE_SETTING_SHOW);
			if(!file.exists()){
				file.createNewFile();
				saveFile(FILE_SETTING_SHOW, settingTXT);//��ʼ�������ļ�
			}
			String[] settings = readFile(FILE_SETTING_SHOW).split(" ");
			INDEX_MODE = settings[0];
			if(INDEX_MODE.equals("0")) {//������ҳΪ�ٶ�
				HOME_URL = "http://www.baidu.com";
			}else if (INDEX_MODE.equals("1")) {//������ҳΪ��Ӧ
				HOME_URL = "http://cn.bing.com";
			}
			COLOR_MODE = settings[1];//������ɫģʽ
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	/**
	 * �����ļ�
	 */
	public static void saveFile(String filePath,String content) {
		try{
		    String line = "";
		    FileWriter fw = new FileWriter(filePath);
//		    FileReader fr = new FileReader(filePath);
//		    BufferedReader br = new BufferedReader(fr);
		    BufferedWriter bw = new BufferedWriter(fw);
		    bw.write(content);
		    bw.close();
		}catch (Exception e){
		    e.printStackTrace();
		}
	}
	
	/**
	 * ��ȡ�ļ�
	 * @param filePath
	 * @return �ļ�����
	 */
	public static String readFile(String filePath) {
		FileInputStream inputStream = null;
		StringBuilder content = new StringBuilder();
		try{
		    inputStream = new FileInputStream(filePath);
		    // ʹ�����ַ���ֻ��Ҫ 385ms
		    BufferedInputStream bis = new BufferedInputStream(inputStream);
		    BufferedReader reader = new BufferedReader(new InputStreamReader(bis,"utf-8"), 5*1024*1024);
		    String line = "";
		    while ((line= reader.readLine())!=null){
		        // �ڴ˴����õ����н��д���
		        content.append(line);
		    }
		    inputStream.close();
		}catch (Exception e){ 
		    e.printStackTrace();
		}
		return content.toString();
	}
}
