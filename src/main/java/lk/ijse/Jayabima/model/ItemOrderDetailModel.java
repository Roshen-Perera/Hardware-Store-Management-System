package lk.ijse.Jayabima.model;

import lk.ijse.Jayabima.db.DbConnection;
import lk.ijse.Jayabima.dto.tm.CustomerCartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ItemOrderDetailModel {
    public boolean saveOrderDetails(String orderId, List<CustomerCartTm> customerCartTmList) throws SQLException {
        for(CustomerCartTm tm : customerCartTmList) {
            if(!saveOrderDetails(orderId, tm)) {
                return false;
            }
        }
        return true;
    }

    private boolean saveOrderDetails(String orderId, CustomerCartTm tm) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO itemorder_detail VALUES(?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, orderId);
        pstm.setString(2, tm.getCode());
        pstm.setString(3, tm.getName());
        pstm.setString(4, tm.getDescription());
        pstm.setInt(5, tm.getQty());
        pstm.setDouble(6, tm.getUnitPrice());
        pstm.setDouble(7, tm.getTot());

        return pstm.executeUpdate() > 0;
    }
}
