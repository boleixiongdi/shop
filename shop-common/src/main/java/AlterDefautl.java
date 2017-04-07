import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class AlterDefautl {

	public static void main(String[] args){

        // 驱动程序名
        String driver = "com.mysql.jdbc.Driver";

        // URL指向要访问的数据库名scutcs
        String url = "jdbc:mysql://127.0.0.1:3306/testshop";

        // MySQL配置时的用户名
        String user = "root"; 

        // MySQL配置时的密码
        String password = "root";

        try { 
         // 加载驱动程序
         Class.forName(driver);

         // 连续数据库
         Connection conn = DriverManager.getConnection(url, user, password);

         if(!conn.isClosed()) 
          System.out.println("Succeeded connecting to the Database!");

         // statement用来执行SQL语句
         Statement statement = conn.createStatement();

         // 要执行的SQL语句
         String sql = "show tables";

         // 结果集
         ResultSet rs = statement.executeQuery(sql);

        
         String name = null;

         while(rs.next()) {
 
          // 选择sname这列数据
          name = rs.getString(1);
 String sql1="alter table "+name+" ALTER column deleteStatus set default 0 ;";
 System.out.println(sql1);
          // 输出结果
// Statement statement1 = conn.createStatement();
// statement1.executeUpdate(sql1);
         }

        

        } catch(ClassNotFoundException e) {


         System.out.println("Sorry,can`t find the Driver!"); 
         e.printStackTrace();


        } catch(SQLException e) {


         e.printStackTrace();


        } catch(Exception e) {


         e.printStackTrace();


        } 
} 
}
