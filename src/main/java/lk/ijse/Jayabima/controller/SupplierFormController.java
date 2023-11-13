package lk.ijse.Jayabima.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.Jayabima.dto.SupplierDto;
import lk.ijse.Jayabima.dto.tm.SupplierTm;
import lk.ijse.Jayabima.model.SupplierModel;

import java.sql.SQLException;
import java.util.List;

public class SupplierFormController {

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colMobile;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private AnchorPane rootHome;

    @FXML
    private TableView<SupplierTm> tblSupplier;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtMobile;

    @FXML
    private TextField txtName;

    private final SupplierModel supplierModel =  new SupplierModel();

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtDescription.setText("");
        txtMobile.setText("");
    }

    public void initialize() {
        setCellValueFactory();
        loadAllSupplier();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("supId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("supName"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("supDesc"));
        colMobile.setCellValueFactory(new PropertyValueFactory<>("supMobile"));
    }

    private void loadAllSupplier() {
        var model = new SupplierModel();

        ObservableList<SupplierTm> obList = FXCollections.observableArrayList();

        try {
            List<SupplierDto> dtoList = model.getAllSupplier();

            for (SupplierDto dto : dtoList) {
                obList.add(
                        new SupplierTm(
                                dto.getSupId(),
                                dto.getSupName(),
                                dto.getSupDesc(),
                                dto.getSupMobile()
                        )
                );
            }
            tblSupplier.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void btnAddSupplierOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String desc = txtDescription.getText();
        String mobile = txtMobile.getText();

        var dto = new SupplierDto(id, name, desc, mobile);

        try {
            boolean isSaved = supplierModel.saveSupplier(dto);
            if(isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier Saved !").show();
                clearFields();
                loadAllSupplier();
            }else {
                new Alert(Alert.AlertType.ERROR, "Customer details not saved").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnClearSupplierOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteSupplierOnAction(ActionEvent event) {
        String id = txtId.getText();

        try{
            boolean isDeleted = SupplierModel.deleteSupplier(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer deleted!").show();
                clearFields();
                loadAllSupplier();
            } else {
                new Alert(Alert.AlertType.CONFIRMATION, "customer not deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String id = txtId.getText();

        try {
            SupplierDto supplierDto = SupplierModel.searchSupplier(id);
            if (supplierDto != null) {
                txtId.setText(supplierDto.getSupId());
                txtName.setText(supplierDto.getSupName());
                txtDescription.setText(supplierDto.getSupDesc());
                txtMobile.setText(supplierDto.getSupMobile());
            } else {
                new Alert(Alert.AlertType.ERROR, "customer not found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateSupplierOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String desc = txtDescription.getText();
        String mobile = txtMobile.getText();

        var dto = new SupplierDto(id, name, desc, mobile);

        try {
            boolean isSaved = SupplierModel.updateSupplier(dto);
            if(isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier Saved !").show();
                clearFields();
                loadAllSupplier();
            }else {
                new Alert(Alert.AlertType.ERROR, "Customer details not saved").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

}
