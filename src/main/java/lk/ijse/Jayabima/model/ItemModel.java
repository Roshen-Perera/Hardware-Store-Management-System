package lk.ijse.Jayabima.model;

import lk.ijse.Jayabima.db.DbConnection;
import lk.ijse.Jayabima.dto.ItemDto;
import lk.ijse.Jayabima.dto.tm.CustomerCartTm;
import lk.ijse.Jayabima.dto.tm.StockCartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemModel {
    private String splitItemID(String currentItemID){
        if (currentItemID != null) {
            String[] split = currentItemID.split("[I]");

            int id = Integer.parseInt(split[1]);
            id++;
            return String.format("I%03d", id);
        } else {
            return "I001";
        }
    }

    public String generateNextItem() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT item_id FROM item ORDER BY item_id DESC LIMIT 1";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ResultSet resultSet = ptsm.executeQuery();
        if (resultSet.next()){
            return splitItemID(resultSet.getString(1));
        }
        return splitItemID(null);
    }

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
    public boolean updateItem(List<CustomerCartTm> customerCartTmList) throws SQLException {
        for(CustomerCartTm tm : customerCartTmList) {
            System.out.println("Item: " + tm);
            if(!updateQty(tm.getCode(), tm.getQty())) {
                return false;
            }
        }
        return true;
    }

    public boolean updateQty(String code, int qty) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE item SET item_qty = item_qty - ? WHERE item_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setInt(1, qty);
        pstm.setString(2, code);

        return pstm.executeUpdate() > 0; //false
    }

    public boolean updateQty1(String code, int qty) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE item SET item_qty = item_qty + ? WHERE item_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setInt(1, qty);
        pstm.setString(2, code);

        return pstm.executeUpdate() > 0; //false
    }

    public boolean updateItem1(List<StockCartTm> stockCartTmList) throws SQLException {
        for(StockCartTm tm : stockCartTmList) {
            System.out.println("Item: " + tm);
            if(!updateQty1(tm.getCode(), tm.getQty())) {
                return false;
            }
        }
        return true;
    }
}
