
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Administrator
 */
public class ResumeUtil {
    
    private Connection con = null;
    private String user="root";
    private String passWord = "111";
    
    
    public Connection getConnection(Properties prop) {
        try {
            Class.forName(prop.getProperty("driverName","com.mysql.jdbc.Driver")); // ����MySQL���ݿ�����
            String url = prop.getProperty("url","jdbc:mysql://localhost:3306/information_schema"); // �������������ݿ��url
            user = prop.getProperty("user","root"); // �����������ݿ���û���
            passWord = prop.getProperty("password","111"); // �����������ݿ������
            con = DriverManager.getConnection(url, user, passWord); // ��������
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
    
    // ��ȡMySQL�������ݿⷽ��
    public List getDatabase(Properties prop) {
        List list = new ArrayList(); // ����List���϶���
        Connection con = getConnection(prop); // ��ȡ���ݿ�����
        Statement st; // ����Statement����
        try {
            st = con.createStatement(); // ʵ����Statement����
            ResultSet rs = st.executeQuery("select schema_name from SCHEMATA"); // ָ����ѯ�������ݿⷽ��
            while (rs.next()) { // ѭ��������ѯ�����
                list.add(rs.getString(1)); // ����ѯ������ӵ�List������
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list; // ���ز�ѯ���
    }

	public boolean mysqlresume(String database, String path) { // �ָ����ݿ�
	    try {
    		String cmd = null;
	    	if("ֱ�ӻָ�".equals(database)) {
	    		cmd = String.format("mysql -u%s -p%s -B  information_schema < %s", user,passWord,path);
	    	}else {
	    		cmd = String.format("mysql -u%s -p%s %s < %s", user,passWord,database,path);
	    	}
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

    public static void main(String[] args) {
        ResumeUtil userDao = new ResumeUtil();
        boolean bool = userDao.mysqlresume("db_database21", "d:\\db.sql");
        System.out.println(bool);
    }
    
}
