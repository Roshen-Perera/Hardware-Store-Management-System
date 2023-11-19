package lk.ijse.Jayabima.model;

import lk.ijse.Jayabima.db.DbConnection;
import lk.ijse.Jayabima.dto.EmployeeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {

    private static String splitEmployeeID(String currentEmployeeID){
        if (currentEmployeeID != null){
            String [] split = currentEmployeeID.split("00");

            int id = Integer.parseInt(split[1]);
            id++;
            return "I00" + id;
        }else {
            return "I001";
        }
    }

    public static String generateNextEmployee() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT emp_id FROM employee ORDER BY emp_id DESC LIMIT 1";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ResultSet resultSet = ptsm.executeQuery();
        if (resultSet.next()){
            return splitEmployeeID(resultSet.getString(1));
        }
        return splitEmployeeID(null);
    }
    public static boolean saveEmployee(EmployeeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO Employee VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getId());
        pstm.setString(2, dto.getName());
        pstm.setString(3, dto.getRole());
        pstm.setString(4, dto.getAddress());
        pstm.setString(5, dto.getSalary());
        pstm.setString(6, dto.getMobile());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }
    public static boolean deleteEmployee(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "delete from Employee where emp_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }
    public static boolean updateEmployee(EmployeeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "update Employee set emp_name = ?, emp_role = ?, emp_address = ?, emp_salary = ?, emp_mobile = ? where emp_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getName());
        pstm.setString(2, dto.getRole());
        pstm.setString(3, dto.getAddress());
        pstm.setString(4, dto.getSalary());
        pstm.setString(5, dto.getMobile());
        pstm.setString(6, dto.getId());

        return pstm.executeUpdate() > 0;
    }

    public static EmployeeDto searchEmployee(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "select * from employee where emp_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        EmployeeDto dto = null;

        if (resultSet.next()) {
            String emp_id = resultSet.getString(1);
            String emp_name = resultSet.getString(2);
            String emp_role = resultSet.getString(3);
            String emp_address = resultSet.getString(4);
            String emp_salary = resultSet.getString(5);
            String emp_mobile = resultSet.getString(6);

            dto = new EmployeeDto(emp_id, emp_name, emp_role, emp_address, emp_salary, emp_mobile);
        }
        return dto;

    }
    public List<EmployeeDto> getAllEmployee() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "select * from Employee";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();

        ArrayList<EmployeeDto> dtoList = new ArrayList<>();
        while (rs.next()) {
            dtoList.add(
                    new EmployeeDto(
                            rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getString(6)
                    )
            );
        }
        return dtoList;

    }
}
