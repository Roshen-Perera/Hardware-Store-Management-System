package lk.ijse.Jayabima.model;

import lk.ijse.Jayabima.db.DbConnection;
import lk.ijse.Jayabima.dto.ItemDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemModel {

    public ItemDto searchItem(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "select * from item where item_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        ResultSet rs = pstm.executeQuery();

        ItemDto dto = null;

        if(rs.next()){
            String item_id = rs.getString(1);
            String item_name = rs.getString(2);
            String item_unitPrice = rs.getString(3);
            String item_sellingPrice = rs.getString(4);

            dto = new ItemDto(item_id,item_name,item_unitPrice,item_sellingPrice);

        }
        return dto;
    }

    public boolean saveCustomer(ItemDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "insert into item values (?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getItemCode());
        pstm.setString(2, dto.getItemName());
        pstm.setString(3,dto.getItemUnitPrice());
        pstm.setString(4, dto.getItemSellingPrice());

        int affectedRows = pstm.executeUpdate();

        return affectedRows > 0;
    }

    public boolean deleteCustomer(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "delete from item where item_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,id);

        int affectedRows = pstm.executeUpdate();

        return affectedRows > 0;
    }


    public boolean updateItem(ItemDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "update item set item_name = ?, item_unitPrice = ?, item_sellingPrice = ? where item_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(4, dto.getItemCode());
        pstm.setString(1, dto.getItemName());
        pstm.setString(2, dto.getItemUnitPrice());
        pstm.setString(3, dto.getItemSellingPrice());

        int isUpdated = pstm.executeUpdate();

        return isUpdated > 0;
    }
}
