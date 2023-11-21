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
        if(currentOrderId != null) {
            String[] split = currentOrderId.split("O0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            return "O00" + id;
        } else {
            return "O001";
        }
    }

    public boolean saveOrder(String order_id, String cus_id, double totalPrice, LocalDate date) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO orders VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, order_id);
        pstm.setString(2, cus_id);
        pstm.setString(3, String.valueOf(totalPrice));
        pstm.setDate(4, Date.valueOf(date));

        return pstm.executeUpdate() > 0;
    }
}
