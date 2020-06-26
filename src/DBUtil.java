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
			Class.forName(prop.getProperty("driverName","com.mysql.jdbc.Driver"));//加载MySQL数据驱动
			String url = prop.getProperty("url", "jdbc:mysql://localhost:3306/mysqldemo");//定义与连接数据库的url
			user = prop.getProperty("user", "root");//定义连接数据库的用户名
			passWord = prop.getProperty("password","123456");//定义连接数据库的密码
			conn = DriverManager.getConnection(url,user,passWord);//创建连接
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	//获取MySQL所有数据库方法
	public static List getDatabases(Properties prop) {
		List list = new ArrayList();//定义List集合对象
		Connection con = getConnection(prop);//获取数据库连接
		Statement st;//定义Statement
		try {
			st = con.createStatement();//实例化Statement对象
			//ResultSet rs = st.executeQuery("select schema_name from SCHEMATA")
			ResultSet rs = st.executeQuery("show databases");
			//遍历循环查询结果集
			while(rs.next()) {
				list.add(rs.getString(1));//将查询数据添加到List集合中
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	//备份数据库方法
	public static boolean mysqldump(String database,String path) {
		//备份数据库
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
	public static boolean mysqlresume(String database, String path) { // 恢复数据库
	    try {
    		String cmd = null;
	    	if("直接恢复".equals(database)) {
	    		cmd = String.format("mysql -u%s -p%s -B  information_schema < %s", user,passWord,path);
	    	}else {
	    		cmd = String.format("mysql -u%s -p%s %s < %s", user,passWord,database,path);
	    	}
	    	System.out.println(cmd);
	        Process p = Runtime.getRuntime().exec("cmd.exe /c "+cmd); // 执行恢复语句
	        StringBuffer out1 = new StringBuffer(); // 定义字符串缓冲对象
	        byte[] b = new byte[1024]; // 定义字节数组
	        for (int i; ((i = p.getInputStream().read(b)) != -1);) { // 将数据写入到指定文件中
	            out1.append(new String(b, 0, i)); // 向流中追加数据
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	    return true;
	}
}
