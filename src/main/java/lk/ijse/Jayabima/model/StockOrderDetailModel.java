package lk.ijse.Jayabima.model;

import lk.ijse.Jayabima.db.DbConnection;
import lk.ijse.Jayabima.dto.tm.StockCartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class StockOrderDetailModel {
    public boolean saveStockOrderDetails(String orderId, List<StockCartTm> stockCartTmList) throws SQLException {
        for(StockCartTm tm : stockCartTmList) {
            if(!saveStockOrderDetails(orderId, tm)) {
                return false;
            }
        }
        return true;
    }

    private boolean saveStockOrderDetails(String orderId, StockCartTm tm) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO stockOrder_detail  VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, orderId);
        pstm.setString(2, tm.getCode());
        pstm.setString(3, tm.getName());
        pstm.setString(4, tm.getDescription());
        pstm.setInt(5, tm.getQty());
        pstm.setString(6, tm.getSupName());

        return pstm.executeUpdate() > 0;
    }

}
