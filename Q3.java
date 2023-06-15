import java.sql.*;

public class Q3 {

    public static void main(String[] args) {
        String url = "jdbc:mariadb://140.127.74.226:3306/410872026";
        String user = "410872026";
        String password = "410872026";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(getQuery())) {

            while (resultSet.next()) {
                int productID = resultSet.getInt("productID");
                String name = resultSet.getString("name");

                System.out.println("Product ID: " + productID);
                System.out.println("Name: " + name);
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String getQuery() {
        return "SELECT p.productID, p.name " +
                "FROM product p " +
                "WHERE NOT EXISTS (" +
                "    SELECT * " +
                "    FROM 實體店鋪 s " +
                "    WHERE s.地區 = 'Kaohsiung' AND s.storeID IN (" +
                "        SELECT 實體店鋪.storeID " +
                "        FROM 實體店鋪 " +
                "        LEFT JOIN 庫存 ON 實體店鋪.storeID = 庫存.storeID AND 庫存.product_ID = p.productID " +
                "        WHERE 庫存.status = 'In Stock' " +
                "    )" +
                ")";
    }
}
