import java.sql.*;

public class Q2 {

    public static void main(String[] args) {
        String url = "jdbc:mariadb://140.127.74.226:3306/410872026";
        String user = "410872026";
        String password = "410872026";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(getTopSalesQuery())) {

            while (resultSet.next()) {
                int productID = resultSet.getInt("productID");
                String name = resultSet.getString("name");
                double totalSales = resultSet.getDouble("total_sales");

                System.out.println("Product ID: " + productID);
                System.out.println("Name: " + name);
                System.out.println("Total Sales: " + totalSales);
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String getTopSalesQuery() {
        return "SELECT p.productID, p.name, SUM(oi.amount * o.value) AS total_sales " +
                "FROM product p " +
                "JOIN 訂單info oi ON p.productID = oi.productID " +
                "JOIN 訂單 o ON oi.orderID = o.orderID " +
                "WHERE o.orderDate >= DATE_SUB(CURRENT_DATE(), INTERVAL 1 YEAR) " +
                "GROUP BY p.productID, p.name " +
                "ORDER BY total_sales DESC " +
                "LIMIT 2";
    }
}
