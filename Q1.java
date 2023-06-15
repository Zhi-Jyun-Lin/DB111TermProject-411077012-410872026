import java.sql.*;

public class Q1 {
   
    public static void main(String[] args) {
    	 String url = "jdbc:mariadb://140.127.74.226:3306/410872026";
         String user = "410872026";
         String password = "410872026";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(getTopSpenderQuery())) {

            if (rs.next()) {
                int customerID = rs.getInt("customerID");
                String name = rs.getString("name");
                double totalSpent = rs.getDouble("total_spent");

                System.out.println("Top Spender:");
                System.out.println("Customer ID: " + customerID);
                System.out.println("Name: " + name);
                System.out.println("Total Spent: " + totalSpent);
            } else {
                System.out.println("No data found.");
            }
        } catch (SQLException e) {
            System.out.println("資料庫錯誤：" + e.getMessage());
        }
    }

    private static String getTopSpenderQuery() {
        return "SELECT c.customerID, c.name, SUM(o.value) AS total_spent " +
                "FROM customer c " +
                "JOIN 訂單 o ON c.customerID = o.customerID " +
                "WHERE o.orderDate >= DATE_SUB(CURRENT_DATE(), INTERVAL 1 YEAR) " +
                "GROUP BY c.customerID, c.name " +
                "ORDER BY total_spent DESC " +
                "LIMIT 1";
    }
}