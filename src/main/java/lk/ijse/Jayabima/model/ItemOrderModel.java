package lk.ijse.Jayabima.model;

import lk.ijse.Jayabima.db.DbConnection;

import java.sql.*;
import java.time.LocalDate;

public class ItemOrderModel {
    public String generateNextOrderId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitOrderId(resultSet.getString(1));
        }
        return splitOrderId(null);
    }

    private String splitOrderId(String currentOrderId) {
        if (currentOrderId != null && currentOrderId.matches("IO\\d{3}")) {
            String[] split = currentOrderId.split("IO");

            try {
                int id = Integer.parseInt(split[1]);
                id++;
                return String.format("IO%03d", id);
            } catch (NumberFormatException e) {
                // Handle the case where the numeric part is not a valid integer
                e.printStackTrace();  // You may want to log or handle the exception appropriately
            }
        }

        // Default case if input is null or does not match the expected format
        return "IO001";
    }

    public boolean saveOrder(String order_id, String cus_id, String cus_name, double totalPrice, LocalDate date) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO orders VALUES(?, ?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, order_id);
        pstm.setString(2, cus_id);
        pstm.setString(3, cus_name);
        pstm.setString(4, String.valueOf(totalPrice));
        pstm.setDate(5, Date.valueOf(date));

        return pstm.executeUpdate() > 0;
    }
}
