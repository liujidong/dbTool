import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataBackup {
	Connection conn = null;
	public Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");//加载MySQL数据驱动
			String url = "jdbc:mysql://localhost:3306/mysqldemo";//定义与连接数据库的url
			String user = "root";//定义连接数据库的用户名
			String password = "123456";//定义连接数据库的密码
			conn = DriverManager.getConnection(url,user,password);//创建连接
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	//获取MySQL所有数据库方法
	public List getDatabase() {
		List list = new ArrayList();//定义List集合对象
		Connection con = getConnection();//获取数据库连接
		Statement st;//定义Statement
		try {
			st = con.createStatement();//实例化Statement对象
			ResultSet rs = st.executeQuery("select schema_name from SCHEMATA");
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
	public boolean mysqldump(String database,String path) {
		//备份数据库
		try {
			Process p = Runtime.getRuntime().exec("cmd.exe /c mysqldump -uroot -p111"+database+">"+path);
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
}
