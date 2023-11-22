package lk.ijse.Jayabima.model;

import lk.ijse.Jayabima.db.DbConnection;
import lk.ijse.Jayabima.dto.EmployeeDto;
import lk.ijse.Jayabima.dto.SalaryDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaryModel {
    public List<SalaryDto> getAllSalary() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "select * from Salary";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();

        ArrayList<SalaryDto> dtoList = new ArrayList<>();
        while (rs.next()) {
            dtoList.add(
                    new SalaryDto(
                            rs.getString(1),
                            rs.getString(2),
                            rs.getString(3)
                    )
            );
        }
        return dtoList;

    }

    public boolean updateStatus(SalaryDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "update salary set salary_amount = ?, salary_status = ? where emp_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getId());
        pstm.setString(2, dto.getSalary());
        pstm.setString(3, dto.getStatus());

        return pstm.executeUpdate() > 0;
    }
}
