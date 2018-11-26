import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnDb {
	
	
	// 定义连接所需的字符串
    // 192.168.0.X是本机地址(要改成自己的IP地址)，1521端口号，XE是精简版Oracle的默认数据库名
    private static String USERNAMR = "root";
    private static String PASSWORD = "";
    private static String DRVIER = "com.mysql.jdbc.Driver";
    private static String URL ="jdbc:mysql://localhost/test";
    
    // 创建一个数据库连接
    private Connection connection = null;
    // 创建预编译语句对象，一般都是用这个而不用Statement
    PreparedStatement pstm = null;
    // 创建一个结果集对象
    ResultSet rs = null;
	
	
  public Connection	getConnection()
  {
	  
	  try {
          Class.forName(DRVIER);
          connection = DriverManager.getConnection(URL, USERNAMR, PASSWORD);
          System.out.println("成功连接数据库");
          } catch (ClassNotFoundException e) {
        	  System.out.println(e+"++++++++++");
          throw new RuntimeException("class not find !", e);
      } catch (SQLException e) {
    	  System.out.println(e+"++++++++++");
          throw new RuntimeException("get connection error!", e);
       }

	  return connection;
  }
	

  
}
