package lk.ijse.Jayabima.model;

import lk.ijse.Jayabima.db.DbConnection;

import java.sql.*;
import java.time.LocalDate;

public class StockOrderModel {
    public String generateNextStockOrderId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT stockOrder_id FROM stock_order ORDER BY stockOrder_id DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitStockOrderId(resultSet.getString(1));
        }
        return splitStockOrderId(null);
    }

    private String splitStockOrderId(String currentOrderId) {
        if(currentOrderId != null) {
            String[] split = currentOrderId.split("O0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            return "SO00" + id;
        } else {
            return "SO001";
        }
    }
    public boolean saveStockOrder(String order_id, String cus_id, LocalDate date) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO stock_order VALUES(?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, order_id);
        pstm.setString(2, cus_id);
        pstm.setDate(3, Date.valueOf(date));

        return pstm.executeUpdate() > 0;
    }
}
