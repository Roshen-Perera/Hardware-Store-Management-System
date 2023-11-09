package lk.ijse.Jayabima.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.Jayabima.dto.ItemDto;
import lk.ijse.Jayabima.model.ItemModel;

import java.sql.SQLException;

public class ItemFormController {

    @FXML
    private AnchorPane rootHome;

    @FXML
    private TextField txtItemName;

    @FXML
    private TextField txtItemSellingPrice;

    @FXML
    private TextField txtitemCode;

    @FXML
    private TextField txtitemUnitPrice;

    ItemModel itemModel = new ItemModel();

    private void clearFields() {
        txtitemCode.setText("");
        txtItemName.setText("");
        txtitemUnitPrice.setText("");
        txtItemSellingPrice.setText("");
    }

    @FXML
    void btnAddItemOnAction(ActionEvent event) {
        String item_code = txtitemCode.getText();
        String item_name = txtItemName.getText();
        String item_unitPrice = txtitemUnitPrice.getText();
        String item_sellingPrice = txtItemSellingPrice.getText();

        var dto = new ItemDto(item_code, item_name, item_unitPrice, item_sellingPrice);

        try {
            boolean isSaved = itemModel.saveCustomer(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item Saved").showAndWait();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Item Not Saved").showAndWait();
        }
    }

    @FXML
    void btnClearItemOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteItemOnAction(ActionEvent event) {
        String item_code = txtitemCode.getText();
        try {
            boolean isDeleted = itemModel.deleteCustomer(item_code);
            if(isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"Item Deleted").showAndWait();
            } else {
                new Alert(Alert.AlertType.CONFIRMATION,"Item Not Deleted").showAndWait();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String id = txtitemCode.getText();
        try {
            ItemDto itemDto = itemModel.searchItem(id);
            if (itemDto != null) {
                txtitemCode.setText(itemDto.getItemCode());
                txtItemName.setText(itemDto.getItemName());
                txtitemUnitPrice.setText(itemDto.getItemUnitPrice());
                txtItemSellingPrice.setText(itemDto.getItemSellingPrice());
            }else {
                new Alert(Alert.AlertType.INFORMATION, "Customer Saved").showAndWait();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    @FXML
    void btnUpdateItemOnAction(ActionEvent event) {
        String item_code = txtitemCode.getText();
        String item_name = txtItemName.getText();
        String item_unitPrice = txtitemUnitPrice.getText();
        String item_sellingPrice = txtItemSellingPrice.getText();

        var dto = new ItemDto(item_code, item_name, item_unitPrice, item_sellingPrice);

        try {
            boolean isUpdated = itemModel.updateItem(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Items are updated").showAndWait();
            }else {
                new Alert(Alert.AlertType.CONFIRMATION, "Items are not updated").showAndWait();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

}
