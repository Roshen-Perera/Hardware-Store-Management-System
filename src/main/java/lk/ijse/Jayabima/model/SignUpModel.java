package lk.ijse.Jayabima.model;

import lk.ijse.Jayabima.db.DbConnection;
import lk.ijse.Jayabima.dto.SignUpDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignUpModel {
    public static boolean saveUser(SignUpDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

//        String sql = "INSERT INTO user VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement("INSERT INTO user VALUES(?, ?, ?, ?");

        pstm.setString(1, dto.getUsername());
        pstm.setString(2, dto.getMobile());
        pstm.setString(3, dto.getPassword());
        pstm.setString(4, dto.getRepeatPassword());

        return pstm.executeUpdate() > 0;
    }
}
