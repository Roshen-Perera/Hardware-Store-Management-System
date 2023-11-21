package lk.ijse.Jayabima.model;

import lk.ijse.Jayabima.db.DbConnection;
import lk.ijse.Jayabima.dto.ItemDto;
import lk.ijse.Jayabima.dto.PlaceStockOrderDto;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class PlaceStockOrderModel {
    private StockOrderModel stockOrderModel = new StockOrderModel();
    private ItemModel itemModel = new ItemModel();
    private StockOrderDetailModel stockOrderDetailModel = new StockOrderDetailModel();

    public boolean placeStockOrder(PlaceStockOrderDto placeStockOrderDto) throws SQLException {
        System.out.println(placeStockOrderDto);

        String stockOrderId = placeStockOrderDto.getStockOrder_id();
        String supplierId = placeStockOrderDto.getSup_id();
        LocalDate date = placeStockOrderDto.getSupOrder_date();

        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isStockOrderSaved = stockOrderModel.saveStockOrder(stockOrderId, supplierId, date);
            if(isStockOrderSaved) {
                boolean isUpdated = itemModel.updateItem1(placeStockOrderDto.getStockCartTmList());
                if (isUpdated) {
                    boolean isStockOrderDetailSaved = stockOrderDetailModel.saveStockOrderDetails(placeStockOrderDto.getStockOrder_id(), placeStockOrderDto.getStockCartTmList());
                    if (isStockOrderDetailSaved) {
                        connection.commit();
                    }
                }
            }
            connection.rollback();
        }finally {
            connection.setAutoCommit(true);
        }
        return true;
    }
}
