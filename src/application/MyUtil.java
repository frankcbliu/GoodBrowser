package application;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

/**
 * 工具类
 * @author 刘灿彬
 * @time 2019年6月25日
 */
public class MyUtil {
	private static final String DIR = System.getProperty("user.home") + "/Downloads/GoodBrowser/"; 
	public static String HOME_URL;//主页
	public static String FILE_DOWNLOAD_SHOW;//下载页面信息存储文件夹路径
	public static String FILE_SETTING_SHOW;//设置页面信息存储文件夹路径
	private static final String downloadTXT = "";// index filename currentSize size   
	private static final String settingTXT = "0 0"; // homeurl
	public static String INDEX_MODE;//首页
	public static String COLOR_MODE;//颜色
	/**
	 * 初始化相关信息
	 */
	public static void initSetting() {
		FILE_DOWNLOAD_SHOW = DIR + "config/downloadSetting.txt";
		FILE_SETTING_SHOW = DIR + "config/setting.txt";
		try {//	防止文件不存在的情况
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
				saveFile(FILE_SETTING_SHOW, settingTXT);//初始化设置文件
			}
			String[] settings = readFile(FILE_SETTING_SHOW).split(" ");
			INDEX_MODE = settings[0];
			if(INDEX_MODE.equals("0")) {//设置首页为百度
				HOME_URL = "http://www.baidu.com";
			}else if (INDEX_MODE.equals("1")) {//设置首页为必应
				HOME_URL = "http://cn.bing.com";
			}
			COLOR_MODE = settings[1];//这是颜色模式
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 保存文件
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
	 * 读取文件
	 * @param filePath
	 * @return 文件内容
	 */
	public static String readFile(String filePath) {
		FileInputStream inputStream = null;
		StringBuilder content = new StringBuilder();
		try{
		    inputStream = new FileInputStream(filePath);
		    // 使用这种方法只需要 385ms
		    BufferedInputStream bis = new BufferedInputStream(inputStream);
		    BufferedReader reader = new BufferedReader(new InputStreamReader(bis,"utf-8"), 5*1024*1024);
		    String line = "";
		    while ((line= reader.readLine())!=null){
		        // 在此处对拿到的行进行处理
		        content.append(line);
		    }
		    inputStream.close();
		}catch (Exception e){ 
		    e.printStackTrace();
		}
		return content.toString();
	}
}
