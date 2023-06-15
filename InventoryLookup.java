import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class InventoryLookup {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mariadb://140.127.74.226:3306/410872026", "410872026", "410872026");
            System.out.println("資料庫連線成功！");

            Scanner scanner = new Scanner(System.in);
            boolean continueQuery = true;

            while (continueQuery) {
                // 接收客服輸入的產品名稱或編號
                String userInput = getUserInput(scanner);

                // 構建 SQL 查詢語句
                String query = "SELECT p.name, i.status, i.store_inventory, i.storeID FROM product p "
                        + "JOIN 庫存 i ON p.productID = i.product_ID "
                        + "WHERE p.name = ? OR p.productID = ?";

                // 建立預備語句
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, userInput);
                statement.setString(2, userInput);

                // 執行查詢
                ResultSet resultSet = statement.executeQuery();

                // 處理查詢結果
                while (resultSet.next()) {
                    String productName = resultSet.getString("name");
                    String storeID = resultSet.getString("storeID");
                    String status = resultSet.getString("status");
                    int inventory = resultSet.getInt("store_inventory");
                    System.out.println("產品名稱: " + productName);
                    System.out.println("門市編號: " + storeID);
                    System.out.println("庫存狀態: " + status);
                    System.out.println("庫存數量: " + inventory);

                    // 執行店面資訊的查詢
                    String storeQuery = "SELECT 地區, address, phone FROM 實體店鋪 WHERE storeID = ?";

                    // 建立預備語句
                    PreparedStatement storeStatement = connection.prepareStatement(storeQuery);
                    storeStatement.setString(1, storeID);

                    // 執行查詢
                    ResultSet storeResultSet = storeStatement.executeQuery();

                    // 處理店面資訊的查詢結果
                    if (storeResultSet.next()) {
                        String region = storeResultSet.getString("地區");
                        String address = storeResultSet.getString("address");
                        String phone = storeResultSet.getString("phone");

                        System.out.println("門市地區：" + region);
                        System.out.println("門市地址：" + address);
                        System.out.println("門市電話：" + phone);
                        System.out.println("--------------------------");
                    }

                    // 釋放店面資訊的查詢資源
                    storeResultSet.close();
                    storeStatement.close();
                }

                // 釋放資源
                resultSet.close();
                statement.close();

                // 詢問使用者是否繼續查詢
                System.out.print("是否要繼續查詢？(Y/N): ");
                String continueInput = scanner.nextLine();
                continueQuery = continueInput.equalsIgnoreCase("Y");
            }

            connection.close();
            scanner.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String getUserInput(Scanner scanner) {
        System.out.print("請輸入想查找的產品名稱或編號：");
        return scanner.nextLine();
    }
}

