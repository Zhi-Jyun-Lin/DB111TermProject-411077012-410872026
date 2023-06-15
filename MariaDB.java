package test;

import java.sql.*;





public class MariaDB {

	public static void main(String[] args) {
		Connection connection = null;
		String url = "jdbc:mariadb://140.127.74.226:3306/410872026";
		String user ="410872026";
		String pwd = "410872026";
		
		
		
		//connection
		
		try {
			connection = DriverManager.getConnection(url, user, pwd);
			
			
			String customerQuery = "SELECT c.name, c.phone, c.address FROM 訂單 o JOIN customer c ON o.customerID = c.customerID WHERE o.delivery = 'USPS' AND o.orderID = '123456'";
			Statement customerStatement = connection.createStatement();
			ResultSet rs = customerStatement.executeQuery(customerQuery);
			
			String name = "";
			String phone = "";
			String address = "";
			if (rs.next()) {
				name = rs.getString("name");
				phone = rs.getString("phone");
				address = rs.getString("address");
			}
			

			int newOrderID = generateNewOrderID(connection);
			
			
			customerQuery = "SELECT * FROM 訂單 o WHERE o.delivery = 'USPS' AND o.orderID = '123456'";
			customerStatement = connection.createStatement();
			rs = customerStatement.executeQuery(customerQuery);
			
			
			if(rs.next()) {
				System.out.println("get order successfully");
				String insertQuery = "INSERT INTO 訂單 (orderID, status, orderDate, value, destination, expectedDate, arrivedDate, delivery, customerID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
				insertStatement.setInt(1, newOrderID);
				insertStatement.setString(2, "Pending");  // Set the appropriate status
				insertStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
				insertStatement.setInt(4, rs.getInt("value"));  // Set the appropriate value
				insertStatement.setString(5, rs.getString("address"));
				insertStatement.setDate(6, Date.valueOf("2023-12-31"));  // Set the appropriate expected date
				insertStatement.setNull(7, Types.DATE);  // Set arrived date as null
				insertStatement.setString(8, rs.getString("delivery"));  // Set the appropriate delivery
				insertStatement.setInt(9, rs.getInt("customerID"));  // Set the appropriate customer ID
				
				
				int rowsInserted = insertStatement.executeUpdate();
				if (rowsInserted > 0) {
					System.out.println("New order created with ID: " + newOrderID);
				}
			}
			
			//新增訂單info
			customerQuery = "SELECT * FROM 訂單info o WHERE o.orderID = '123456'";
			customerStatement = connection.createStatement();
			rs = customerStatement.executeQuery(customerQuery);
			
			
			if(rs.next()) {
				int newinfoID = generateNewOrderInfoID(connection);
				
				System.out.println("get order successfully");
				String insertQuery = "INSERT INTO 訂單info (infoID,orderID,productID,amount) VALUES (?, ?, ?, ?)";
				PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
				insertStatement.setInt(1, newinfoID);
				insertStatement.setInt(2, rs.getInt("orderID")); 
				insertStatement.setInt(2, rs.getInt("productID"));
				insertStatement.setInt(2, rs.getInt("amount"));
				
				int rowsInserted = insertStatement.executeUpdate();
				if (rowsInserted > 0) {
					System.out.println("New orderInfo created with ID: " + newinfoID);
				}
			}
			
			
			} catch (SQLException e) {
				e.printStackTrace();
			}
			


			
			
			
			
			
		//disconnection
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	private static int generateNewOrderID(Connection connection) throws SQLException {
        String maxIDQuery = "SELECT MAX(orderID) AS maxID FROM 訂單";
        Statement maxIDStatement = connection.createStatement();
        ResultSet maxIDResult = maxIDStatement.executeQuery(maxIDQuery);

        int maxID = 0;
        if (maxIDResult.next()) {
            maxID = maxIDResult.getInt("maxID");
        }

        // Generate a new order ID by incrementing the maximum ID
        int newOrderID = maxID + 1;

        return newOrderID;
    }
	private static int generateNewOrderInfoID(Connection connection) throws SQLException {
        String maxIDQuery = "SELECT MAX(infoID) AS maxID FROM 訂單";
        Statement maxIDStatement = connection.createStatement();
        ResultSet maxIDResult = maxIDStatement.executeQuery(maxIDQuery);

        int maxID = 0;
        if (maxIDResult.next()) {
            maxID = maxIDResult.getInt("maxID");
        }

        // Generate a new order ID by incrementing the maximum ID
        int newOrderID = maxID + 1;

        return newOrderID;
    }

}


