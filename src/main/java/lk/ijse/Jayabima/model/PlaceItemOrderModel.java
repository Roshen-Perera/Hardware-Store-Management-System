package lk.ijse.Jayabima.model;

import lk.ijse.Jayabima.db.DbConnection;
import lk.ijse.Jayabima.dto.PlaceItemOrderDto;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class PlaceItemOrderModel {
    private ItemOrderModel itemOrderModel = new ItemOrderModel();
    private ItemModel itemModel = new ItemModel();
    private ItemOrderDetailModel itemOrderDetailModel = new ItemOrderDetailModel();

    public boolean placeOrder(PlaceItemOrderDto placeItemOrderDto) throws SQLException {
        System.out.println(placeItemOrderDto);

        String orderId = placeItemOrderDto.getOrderId();
        String customerId = placeItemOrderDto.getCustomerId();
        double totalPrice = Double.parseDouble(placeItemOrderDto.getTotalPrice());
        LocalDate date = placeItemOrderDto.getDate();

        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isOrderSaved = itemOrderModel.saveOrder(orderId, customerId, totalPrice, date);
            if (isOrderSaved) {
                boolean isUpdated = itemModel.updateItem(placeItemOrderDto.getCustomerCartTmList());
                if (isUpdated) {
                    boolean isOrderDetailSaved = itemOrderDetailModel.saveOrderDetails(placeItemOrderDto.getOrderId(), placeItemOrderDto.getCustomerCartTmList());
                    if (isOrderDetailSaved) {
                        connection.commit();
                    }
                }
            }
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
        return true;
    }
}
