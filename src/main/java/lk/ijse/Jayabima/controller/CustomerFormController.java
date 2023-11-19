package lk.ijse.Jayabima.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.Jayabima.db.DbConnection;
import lk.ijse.Jayabima.dto.CustomerDto;
import lk.ijse.Jayabima.dto.tm.CustomerTm;
import lk.ijse.Jayabima.dto.tm.ItemTm;
import lk.ijse.Jayabima.model.CustomerModel;
import org.controlsfx.control.Notifications;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class CustomerFormController {

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colMobile;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtMobile;

    @FXML
    private TextField txtName;

    @FXML
    private Label lblCustomerId;

    @FXML
    private TableView<CustomerTm> tblCustomer;
    private final CustomerModel customerModel = new CustomerModel();

    public void initialize() {
        setCellValueFactory();
        loadAllCustomer();
        tableListener();
        generateNextCustomerID();
    }

    private boolean btnClearPressed = false;

    private void  generateNextCustomerID(){
        try {
            String previousCustomerID = lblCustomerId.getText();
            String customerID = customerModel.generateNextCustomer();
            lblCustomerId.setText(customerID);
            clearFields();
            if (btnClearPressed){
                lblCustomerId.setText(previousCustomerID);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void tableListener() {
        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
//            System.out.println(newValue);
            setData(newValue);
        });
    }

    private void setData(CustomerTm row) {
        lblCustomerId.setText(row.getId());
        txtName.setText(row.getName());
        txtAddress.setText(String.valueOf(row.getAddress()));
        txtMobile.setText(String.valueOf(row.getMobile()));
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colMobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
    }


    private void loadAllCustomer() {
        var model = new CustomerModel();

        ObservableList<CustomerTm> obList = FXCollections.observableArrayList();
        try{
            List<CustomerDto> dtoList = model.getAllCustomer();

            for (CustomerDto dto : dtoList) {
                obList.add(
                        new CustomerTm(
                                dto.getId(),
                                dto.getName(),
                                dto.getAddress(),
                                dto.getMobile()
                        )
                );
            }
            tblCustomer.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void clearFields() {
        txtSearch.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtMobile.setText("");
    }

    @FXML
    void btnAddCustomerOnAction(ActionEvent event) {
        String id = lblCustomerId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String mobile = txtMobile.getText();


        try {
            if (!validateCustomerDetails()) {
                return;
            }
            clearFields();
            var dto = new CustomerDto(id, name, address, mobile);
            boolean isSaved = CustomerModel.saveCustomer(dto);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"Customer Saved").show();
                clearFields();
                loadAllCustomer();
                generateNextCustomerID();
            } else {
                new Alert(Alert.AlertType.ERROR, "Customer details not saved").show();;
            }
        }catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }
    @FXML
    void btnClearCustomerOnAction(ActionEvent event) {
        clearFields();
        generateNextCustomerID();
    }

    @FXML
    void btnDeleteCustomerOnAction(ActionEvent event){
        String id  = lblCustomerId.getText();

        try {
            boolean isDeleted = CustomerModel.deleteCustomer(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Deleted").show();
                clearFields();
                loadAllCustomer();
                generateNextCustomerID();
            } else {
                new Alert(Alert.AlertType.ERROR, "Customer Not Deleted").show();
            }
        }catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnUpdateCustomerOnAction(ActionEvent event) {
        String id = lblCustomerId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String mobile = txtMobile.getText();
        
        try {
            if (!validateCustomerDetails()) {
                return;
            }
            clearFields();
            var dto = new CustomerDto(id, name, address, mobile);
            boolean isUpdated = CustomerModel.updateCustomer(dto);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer details updated").show();;
                clearFields();
                loadAllCustomer();
            } else {
                new Alert(Alert.AlertType.ERROR, "Customer details not updated").show();;
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            clearFields();
        }

    }

    public void btnSearchOnAction(ActionEvent actionEvent) throws SQLException {
        String id = txtSearch.getText();
        try {

            CustomerDto customerDto = customerModel.searchCustomer(id);
            if (customerDto != null) {
                txtSearch.setText(customerDto.getId());
                txtName.setText(customerDto.getName());
                txtAddress.setText(customerDto.getAddress());
                txtMobile.setText(customerDto.getMobile());
            } else {
                new Alert(Alert.AlertType.INFORMATION, "customer not found !").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    public boolean validateCustomerDetails() {
        boolean isValid = true;

        if (!Pattern.matches("[A-Za-z]{4,}", txtName.getText())) {
            showErrorNotification("Invalid Customer Name", "The customer name you entered is invalid");
            isValid = false;
        }

        if (!Pattern.matches("[A-Za-z]{4,}", txtAddress.getText())) {
            showErrorNotification("Invalid Address", "The address you entered is invalid");
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
