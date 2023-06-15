import java.sql.*;

public class Q4 {

    public static void main(String[] args) {

        Connection connection = null;
        Statement statement = null;

        String url = "jdbc:mariadb://140.127.74.226:3306/410872026";
        String user = "410872026";
        String pwd = "410872026";

        try {
            connection = DriverManager.getConnection(url, user, pwd);
            System.out.println("Successfully connected to database");

            statement = connection.createStatement();

            String query = "SELECT * FROM 訂單 WHERE status = 'expired'";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int orderID = resultSet.getInt("orderID");
                String status = resultSet.getString("status");
                Timestamp orderDate = resultSet.getTimestamp("orderDate");
                int value = resultSet.getInt("value");
                String destination = resultSet.getString("destination");
                Date expectedDate = resultSet.getDate("expectedDate");
                Date arrivedDate = resultSet.getDate("arrivedDate");
                String delivery = resultSet.getString("delivery");
                int customerID = resultSet.getInt("customerID");

                // 在這裡處理查詢結果
                System.out.println("Order ID: " + orderID);
                System.out.println("Status: " + status);
                System.out.println("Order Date: " + orderDate);
                System.out.println("Value: " + value);
                System.out.println("Destination: " + destination);
                System.out.println("Expected Date: " + expectedDate);
                System.out.println("Arrived Date: " + arrivedDate);
                System.out.println("Delivery: " + delivery);
                System.out.println("Customer ID: " + customerID);
                System.out.println();
            }

            // 關閉連線
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
