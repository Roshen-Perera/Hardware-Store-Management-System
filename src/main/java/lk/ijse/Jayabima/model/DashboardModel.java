package lk.ijse.Jayabima.model;

import lk.ijse.Jayabima.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardModel {
    public void customerCount() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "select count(cus_id) from customer";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            int theCount = rs.getInt(1);
            System.out.println(theCount);
        }
    }
}
