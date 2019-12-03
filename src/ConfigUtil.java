import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.JFileChooser;

public class ConfigUtil {

	public static void propertyOut(File file) {
		Properties prop = new Properties();
		prop.setProperty("driverName", "com.mysql.jdbc.Driver");
		prop.setProperty("url", "jdbc:mysql://localhost:3306/mysqldemo");
		prop.setProperty("user", "root");
		prop.setProperty("password", "123456");
		
		try {
			FileOutputStream oFile = new FileOutputStream(file, true);//true表示追加打开
			prop.store(oFile, "The New properties file");
			oFile.close();
		}catch (Exception e) {
			System.out.println(e);
		}
	}
	public static Properties getProperties(JFileChooser fileChooser) {
		if(fileChooser.getSelectedFile()!=null) {
			Properties prop = new Properties();
			try {
				InputStream in = new BufferedInputStream(new FileInputStream(fileChooser.getSelectedFile()));
				prop.load(in);     ///加载属性列表
				in.close();
			}catch (Exception e) {
				System.out.println(e);

			}
		}
		return null;
	}
}
