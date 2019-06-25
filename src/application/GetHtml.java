package application;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.NumberFormat;

import org.apache.commons.io.FilenameUtils;

/**
 * 实验三 断点续传下载
 */
public class GetHtml {
	String path = System.getProperty("user.home") + "/Downloads/GoodBrowser/Downloads/";
	static String filename;
	
	boolean flag = true;
    int startIndex = 0;
    long downloadSize = 0;
    boolean downloadFinish = false;
    int totleSize = 0;
    
//	public static void main(String[] args) {
//		GetHtml getHtml = new GetHtml();
//		Scanner scanner = new Scanner(System.in);
//		System.out.print("请输入url：");
//		String url = scanner.next();
//		System.out.print("请输入保存的文件名：");
//		filename = scanner.next();
//		if (filename.substring(filename.lastIndexOf(".")) =="html") {
//			getHtml.downHtml(url);
//		}else {
//			getHtml.downCanbeStop(url);
//		}
//	}
//	
    /**
     * 下载文件
     * @param url
     */
	public static void down(URL url) {
		GetHtml getHtml = new GetHtml();
		filename = FilenameUtils.getName(url.getPath());
		if (filename.substring(filename.lastIndexOf(".")) =="html") {
			getHtml.downHtml(url.toString());
		}else {
			getHtml.downCanbeStop(url.toString());
		}
	}
	
	public void downHtml(String  urlString) {
		try {
			URL url = new URL(urlString);
			URLConnection connection = url.openConnection();
			try (InputStream raw = connection.getInputStream()){
				InputStream buffer = new BufferedInputStream(raw);
				Reader reader = new InputStreamReader(buffer,"utf-8");
				int c;
				File file =new File(path+filename);
				if(!file.exists()){
					file.createNewFile();
				}
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
			    BufferedWriter bw = new BufferedWriter(fw);
				while ((c=reader.read())!=-1) {
					bw.write((char)c);
				}
				bw.close();
			}
			}
		catch (MalformedURLException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();	
		}
	}
	
 
	/**
	 * 可断点续传
	 * @param urlString
	 * @return
	 */
    public boolean downCanbeStop(String  urlString) {
        //Check download progress 
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                     NumberFormat nt = NumberFormat.getPercentInstance();  
                     nt.setMinimumFractionDigits(1);  //set precision
                    while(!downloadFinish){
                        Thread.sleep(30000);// print progress per 30s
                        System.out.println("已下载大小"+getDownloadSize()+",进度"+nt.format(getDownloadSize()* 1.0 /totleSize));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
            }
        }).start();
        RandomAccessFile raf = null;
        InputStream in = null;
        
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            File tmpFile = new File(path+filename+"_tmp");
            if(flag){
                if(tmpFile.exists() && tmpFile.isFile()){
                    downloadSize = tmpFile.length();
                    startIndex = (int)downloadSize;
                }
                conn.setRequestProperty("Range", "bytes=" + startIndex + "-");
            }else{// download completed, then delete the temp_file
                if(tmpFile.exists() && tmpFile.isFile())
                    tmpFile.delete();
            }
            int status = conn.getResponseCode();
            totleSize = (int)downloadSize + conn.getContentLength();
            System.out.println("文件总大小"+totleSize+"，状态码："+status+",需要下载的大小"+(totleSize-downloadSize));
            if(status== 200 || status == 206 ){
                raf = new RandomAccessFile(tmpFile, "rwd");
                raf.seek(startIndex);
                in = conn.getInputStream();
                byte[] buffer = new byte[1024];
                int size = 0;
                while((size=in.read(buffer)) !=-1 ){
                    raf.write(buffer, 0, size);
                    downloadSize += size;
                }
                raf.close();
                in.close();
                File dest = new File(path+filename);
                return tmpFile.renameTo(dest);
            }
        } catch (Throwable e) {
        	e.printStackTrace();
        }finally {
            downloadFinish = true; //下载完成或中断
        }
        return false;
    }

    public long getDownloadSize() {
        return downloadSize;
    }
}
