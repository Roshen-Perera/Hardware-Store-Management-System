package lk.ijse.Jayabima.model;

import lk.ijse.Jayabima.db.DbConnection;
import lk.ijse.Jayabima.dto.ItemDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemModel {

    public List<ItemDto> getAllItems() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "select * from item";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<ItemDto> itemList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            itemList.add(new ItemDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4),
                    resultSet.getDouble(5),
                    resultSet.getString(6)
            ));
        }
        return itemList;
    }

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
            String item_desc = rs.getString(3);
            int item_qty = rs.getInt(4);
            double item_unitPrice = rs.getDouble(5);
            String sup_id = rs.getString(6);

            dto = new ItemDto(item_id, item_name, item_desc, item_qty, item_unitPrice, sup_id);
        }
        return dto;
    }

    public boolean saveCustomer(ItemDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "insert into item values (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getItemCode());
        pstm.setString(2, dto.getItemName());
        pstm.setString(3, dto.getItemDesc());
        pstm.setInt(4,dto.getItemQty());
        pstm.setDouble(5, dto.getItemUnitPrice());
        pstm.setString(6, dto.getSupplierId());

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

        String sql = "update item set item_name = ?, item_desc = ?, item_qty = ?, item_unitPrice = ?, sup_id = ? where item_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getItemName());
        pstm.setString(2, dto.getItemDesc());
        pstm.setInt(3,dto.getItemQty());
        pstm.setDouble(4, dto.getItemUnitPrice());
        pstm.setString(5, dto.getSupplierId());
        pstm.setString(6, dto.getItemCode());

        int isUpdated = pstm.executeUpdate();

        return isUpdated > 0;
    }
}
