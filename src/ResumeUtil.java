
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
            Class.forName(prop.getProperty("driverName","com.mysql.jdbc.Driver")); // 加载MySQL数据库驱动
            String url = prop.getProperty("url","jdbc:mysql://localhost:3306/information_schema"); // 定义与连接数据库的url
            user = prop.getProperty("user","root"); // 定义连接数据库的用户名
            passWord = prop.getProperty("password","111"); // 定义连接数据库的密码
            con = DriverManager.getConnection(url, user, passWord); // 连接连接
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
    
    // 获取MySQL所有数据库方法
    public List getDatabase(Properties prop) {
        List list = new ArrayList(); // 定义List集合对象
        Connection con = getConnection(prop); // 获取数据库连接
        Statement st; // 定义Statement对象
        try {
            st = con.createStatement(); // 实例化Statement对象
            ResultSet rs = st.executeQuery("select schema_name from SCHEMATA"); // 指定查询所有数据库方法
            while (rs.next()) { // 循环遍历查询结果集
                list.add(rs.getString(1)); // 将查询数据添加到List集合中
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list; // 返回查询结果
    }

	public boolean mysqlresume(String database, String path) { // 恢复数据库
	    try {
    		String cmd = null;
	    	if("直接恢复".equals(database)) {
	    		cmd = String.format("mysql -u%s -p%s -B  information_schema < %s", user,passWord,path);
	    	}else {
	    		cmd = String.format("mysql -u%s -p%s %s < %s", user,passWord,database,path);
	    	}
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

    public static void main(String[] args) {
        ResumeUtil userDao = new ResumeUtil();
        boolean bool = userDao.mysqlresume("db_database21", "d:\\db.sql");
        System.out.println(bool);
    }
    
}
