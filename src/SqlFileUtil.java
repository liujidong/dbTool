import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

public class SqlFileUtil {
	private static final String PATTERN_STR = "^CREATE\\s+DATABASE\\s+.*";
	private static final Pattern TEST_PATTERN = Pattern.compile(PATTERN_STR,Pattern.CASE_INSENSITIVE);
	public static String checkSqlFile(File sql) {
		String errMsg = null;
		try (BufferedReader br = new BufferedReader(new FileReader(sql));) {
		        String line = null;
		        while ((line = br.readLine()) != null) {
		            if(TEST_PATTERN.matcher(line).find()) {
			            System.out.println(line);
		            	return line;
		            }
		        }

		  } catch (IOException e) {
		    e.printStackTrace();
		    errMsg="读取sql文件出错！";
		}
		return errMsg;
	}
	public static void main(String[] args) {
		String sql = "CREATE DATABASE /*!32312 IF NOT EXISTS*/`d18207_erp` /*!40100 DEFAULT CHARACTER SET utf8 */;";
		boolean isMatch = Pattern.matches(PATTERN_STR, sql);
		System.out.println(isMatch);
	}
}
