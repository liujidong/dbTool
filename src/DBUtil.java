import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DBUtil {
	static Connection conn = null;
    private static String user="root";
    private static String passWord = "111";
	public static Connection getConnection(Properties prop) {
		if(null == prop) {return null;}
		try {
			Class.forName(prop.getProperty("driverName","com.mysql.jdbc.Driver"));//����MySQL��������
			String url = prop.getProperty("url", "jdbc:mysql://localhost:3306/mysqldemo");//�������������ݿ��url
			user = prop.getProperty("user", "root");//�����������ݿ���û���
			passWord = prop.getProperty("password","123456");//�����������ݿ������
			conn = DriverManager.getConnection(url,user,passWord);//��������
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	//��ȡMySQL�������ݿⷽ��
	public static List getDatabases(Properties prop) {
		List list = new ArrayList();//����List���϶���
		Connection con = getConnection(prop);//��ȡ���ݿ�����
		Statement st;//����Statement
		try {
			st = con.createStatement();//ʵ����Statement����
			//ResultSet rs = st.executeQuery("select schema_name from SCHEMATA")
			ResultSet rs = st.executeQuery("show databases");
			//����ѭ����ѯ�����
			while(rs.next()) {
				list.add(rs.getString(1));//����ѯ������ӵ�List������
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	//�������ݿⷽ��
	public static boolean mysqldump(String database,String path) {
		//�������ݿ�
		try {
			String cmd = String.format("mysqldump -u%s -p%s %s > %s", user,passWord,database,path);
			System.out.println(cmd);
			Process p = Runtime.getRuntime().exec("cmd.exe /c "+cmd);
			StringBuffer out1 = new StringBuffer();
			byte[] b = new byte[1024];
			for(int i;((i=p.getInputStream().read(b)) != -1);) {
				out1.append(new String(b,0,i));
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public static boolean mysqlresume(String database, String path) { // �ָ����ݿ�
	    try {
    		String cmd = null;
	    	if("ֱ�ӻָ�".equals(database)) {
	    		cmd = String.format("mysql -u%s -p%s -B  information_schema < %s", user,passWord,path);
	    	}else {
	    		cmd = String.format("mysql -u%s -p%s %s < %s", user,passWord,database,path);
	    	}
	    	System.out.println(cmd);
	        Process p = Runtime.getRuntime().exec("cmd.exe /c "+cmd); // ִ�лָ����
	        StringBuffer out1 = new StringBuffer(); // �����ַ����������
	        byte[] b = new byte[1024]; // �����ֽ�����
	        for (int i; ((i = p.getInputStream().read(b)) != -1);) { // ������д�뵽ָ���ļ���
	            out1.append(new String(b, 0, i)); // ������׷������
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	    return true;
	}
}
