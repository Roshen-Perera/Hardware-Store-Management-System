package lk.ijse.Jayabima.model;

import lk.ijse.Jayabima.db.DbConnection;
import lk.ijse.Jayabima.dto.EmployeeDto;
import lk.ijse.Jayabima.dto.StockOrderDetailDto;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        if (currentOrderId != null && currentOrderId.matches("SO\\d{3}")) {
            String[] split = currentOrderId.split("SO");

            try {
                int id = Integer.parseInt(split[1]);
                id++;
                return String.format("SO%03d", id);
            } catch (NumberFormatException e) {
                // Handle the case where the numeric part is not a valid integer
                e.printStackTrace();  // You may want to log or handle the exception appropriately
            }
        }

        // Default case if input is null or does not match the expected format
        return "SO001";
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

