import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.JFileChooser;

public class ConfigUtil {
	public static Properties prop = new Properties();
	public static final String DB_DEFAULT="db-default.properties";

	public static void propertyOut(File file) {
		Properties prop = new Properties();
		prop.setProperty("driverName", "com.mysql.jdbc.Driver");
		prop.setProperty("url", "jdbc:mysql://localhost:3306/information_schema");
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
			//Properties prop = new Properties();
			try {
				InputStream in = new BufferedInputStream(new FileInputStream(fileChooser.getSelectedFile()));
				prop.load(in);     ///加载属性列表
				in.close();
			}catch (Exception e) {
				System.out.println(e);

			}
		}
		return prop;
	}
	public static Properties getProperties() {
		File file = new File(DB_DEFAULT);
		if(file.exists()) {
			//Properties prop = new Properties();
			try {
				InputStream in = new BufferedInputStream(new FileInputStream(file));
				prop.load(in);     ///加载属性列表
				in.close();
			}catch (Exception e) {
				System.out.println(e);

			}
		}
		return prop;
	}
}
