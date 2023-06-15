import java.sql.*;

public class TableSizeChecker {
	
	public static void main(String[] args) {

		Connection connection = null;
		Statement statement = null;

		String url = "jdbc:mariadb://140.127.74.226:3306/410872026";
		String user = "410872026";
		String pwd = "410872026";

		try {
			connection = DriverManager.getConnection(url, user, pwd);
		} catch (SQLException e) {
       // TODO Auto-generated catch block
			e.printStackTrace();

		}
		
		System.out.println("Successfully connected to database");
		
		try {
		    statement = connection.createStatement();
		    
		    String[] tableNames = {"customer", "product", "信用卡",  "倉庫", "合約", "實體店鋪", "庫存", "製造商", "訂單","訂單info","運輸公司"}; // 將要檢查大小的表名稱放在數組中
		    
		    for (String tableName : tableNames) {
		        String query = "SELECT COUNT(*) FROM " + tableName;
		        ResultSet result = statement.executeQuery(query);
		        if (result.next()) {
		            int count = result.getInt(1);
		            System.out.println("Table: " + tableName + ", Size: " + count);
		        }
		    }
		} catch (SQLException e) {
		    e.printStackTrace();
		} finally {
		    // 釋放資源
			try {
		        if (statement != null) {
		            statement.close();
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    try {
		        if (connection != null) {
		            connection.close();
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}
		
	}

}
