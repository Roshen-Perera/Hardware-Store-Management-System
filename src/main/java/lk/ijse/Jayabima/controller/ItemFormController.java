package lk.ijse.Jayabima.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.Jayabima.dto.ItemDto;
import lk.ijse.Jayabima.dto.tm.ItemTm;
import lk.ijse.Jayabima.model.ItemModel;

import java.sql.SQLException;
import java.util.List;

public class ItemFormController {

    @FXML
    private AnchorPane rootHome;
    @FXML
    private TextField txtItemDesc;

    @FXML
    private TextField txtItemName;

    @FXML
    private TextField txtItemQty;

    @FXML
    private TextField txtItemSupplierId;

    @FXML
    private TextField txtItemUnitPrice;

    @FXML
    private TextField txtItemCode;

    @FXML
    private TableView<ItemTm> tblItem;

    @FXML
    private TableColumn<?, ?> colBrand;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colQuantity;

    @FXML
    private TableColumn<?, ?> colSupplierId;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    ItemModel itemModel = new ItemModel();

    public void initialize() {
        loadAllItems();
        setCellValueFactory();
    }

    private void clearFields() {
        txtItemCode.setText("");
        txtItemName.setText("");
        txtItemDesc.setText("");
        txtItemQty.setText("");
        txtItemUnitPrice.setText("");
        txtItemSupplierId.setText("");
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("itemDesc"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("itemUnitPrice"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("itemQty"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
    }

    private void loadAllItems() {
//        var model = new ItemModel();
        ObservableList<ItemTm> obList = FXCollections.observableArrayList();
        try {
            List<ItemDto> dtoList = itemModel.getAllItems();

            for (ItemDto dto : dtoList) {
                obList.add(new ItemTm(
                        dto.getItemCode(),
                        dto.getItemName(),
                        dto.getItemDesc(),
                        dto.getItemQty(),
                        dto.getItemUnitPrice(),
                        dto.getSupplierId()
                ));
            }
            tblItem.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @FXML
    void btnAddItemOnAction(ActionEvent event) {
        String item_code = txtItemCode.getText();
        String item_name = txtItemName.getText();
        String item_desc = txtItemDesc.getText();
        int item_qty = Integer.parseInt(txtItemQty.getText());
        double item_unitPrice = Double.parseDouble(txtItemUnitPrice.getText());
        String supplierId = txtItemSupplierId.getText();


        var dto = new ItemDto(item_code, item_name, item_desc, item_qty, item_unitPrice, supplierId);

        try {
            boolean isSaved = itemModel.saveCustomer(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item Saved").show();
            }
            loadAllItems();
            clearFields();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void btnClearItemOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteItemOnAction(ActionEvent event) {
        String item_code = txtItemCode.getText();
        try {
            boolean isDeleted = itemModel.deleteCustomer(item_code);
            if(isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"Item Deleted").show();
                loadAllItems();
                clearFields();
            } else {
                new Alert(Alert.AlertType.CONFIRMATION,"Item Not Deleted").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String id = txtItemCode.getText();
        try {
            ItemDto itemDto = itemModel.searchItem(id);
            if (itemDto != null) {
                txtItemCode.setText(itemDto.getItemCode());
                txtItemName.setText(itemDto.getItemName());
                txtItemDesc.setText(itemDto.getItemDesc());
                txtItemQty.setText(String.valueOf(itemDto.getItemQty()));
                txtItemUnitPrice.setText(String.valueOf(itemDto.getItemUnitPrice()));
                txtItemSupplierId.setText(itemDto.getSupplierId());
            }else {
                new Alert(Alert.AlertType.INFORMATION, "Customer Saved").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    @FXML
    void btnUpdateItemOnAction(ActionEvent event) {
        String item_code = txtItemCode.getText();
        String item_name = txtItemName.getText();
        String item_desc = txtItemDesc.getText();
        int item_qty = Integer.parseInt(txtItemQty.getText());
        double item_unitPrice = Double.parseDouble(txtItemUnitPrice.getText());
        String supplierId = txtItemSupplierId.getText();


        var dto = new ItemDto(item_code, item_name, item_desc, item_qty, item_unitPrice, supplierId);

        try {
            boolean isUpdated = itemModel.updateItem(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Items are updated").show();
                loadAllItems();
                clearFields();
            }else {
                new Alert(Alert.AlertType.CONFIRMATION, "Items are not updated").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

}
