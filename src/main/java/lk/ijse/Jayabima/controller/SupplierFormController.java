package lk.ijse.Jayabima.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.Jayabima.dto.SupplierDto;
import lk.ijse.Jayabima.dto.tm.CustomerTm;
import lk.ijse.Jayabima.dto.tm.SupplierTm;
import lk.ijse.Jayabima.model.SupplierModel;
import org.controlsfx.control.Notifications;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

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

    @FXML
    private Label lblId;


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
        generateNextSupplierID();
        tableListener();
    }

    private boolean btnClearPressed = false;

    private void  generateNextSupplierID(){
        try {
            String previousSupplierID = lblId.getText();
            String supplierID = supplierModel.generateNextSupplier();
            lblId.setText(supplierID);
            clearFields();
            if (btnClearPressed){
                lblId.setText(previousSupplierID);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void tableListener() {
        tblSupplier.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
//            System.out.println(newValue);
            setData(newValue);
        });
    }

    private void setData(SupplierTm row) {
        lblId.setText(row.getSupId());
        txtName.setText(row.getSupName());
        txtDescription.setText(row.getSupDesc());
        txtMobile.setText(row.getSupMobile());
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
        String id = lblId.getText();
        String name = txtName.getText();
        String desc = txtDescription.getText();
        String mobile = txtMobile.getText();

        try {
            if (!validateSupplierDetails()) {
                return;
            }
            clearFields();
            var dto = new SupplierDto(id, name, desc, mobile);
            boolean isSaved = supplierModel.saveSupplier(dto);
            if(isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier Saved !").show();
                clearFields();
                loadAllSupplier();
                generateNextSupplierID();
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
        generateNextSupplierID();
    }

    @FXML
    void btnDeleteSupplierOnAction(ActionEvent event) {
        String id = lblId.getText();

        try{
            boolean isDeleted = SupplierModel.deleteSupplier(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer deleted!").show();
                clearFields();
                loadAllSupplier();
                generateNextSupplierID();
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
        String id = lblId.getText();
        String name = txtName.getText();
        String desc = txtDescription.getText();
        String mobile = txtMobile.getText();

        try {
            if (!validateSupplierDetails()) {
                return;
            }
            clearFields();
            var dto = new SupplierDto(id, name, desc, mobile);
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

    public boolean validateSupplierDetails() {
        boolean isValid = true;

        if (!Pattern.matches("[A-Za-z]{4,}", txtName.getText())) {
            showErrorNotification("Invalid Supplier Name", "The supplier name you entered is invalid");
            isValid = false;

        }

        if (!Pattern.matches("[A-Za-z]{4,}", txtDescription.getText())) {
            showErrorNotification("Invalid Description", "The description you entered is invalid");
            isValid = false;

        }

        if (!Pattern.matches("\\d{10}", txtMobile.getText())) {
            showErrorNotification("Invalid Mobile Number", "The mobile number you entered is invalid");
            isValid = false;
        }
        return isValid;
    }

    private void showErrorNotification(String title, String text) {
        Notifications.create()
                .title(title)
                .text(text)
                .showError();
    }

}
