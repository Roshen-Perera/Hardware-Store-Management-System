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
import lk.ijse.Jayabima.db.DbConnection;
import lk.ijse.Jayabima.dto.CustomerDto;
import lk.ijse.Jayabima.dto.tm.CustomerTm;
import lk.ijse.Jayabima.model.CustomerModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
    private TextField txtId;

    @FXML
    private TextField txtMobile;

    @FXML
    private TextField txtName;

    @FXML
    private TableView<CustomerTm> tblCustomer;
    private final CustomerModel customerModel = new CustomerModel();

    public void initialize() {
        setCellValueFactory();
        loadAllCustomer();
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
        txtId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtMobile.setText("");
    }

    @FXML
    void btnAddCustomerOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String mobile = txtMobile.getText();

        var dto = new CustomerDto(id, name, address, mobile);

        try {
            boolean isSaved = CustomerModel.saveCustomer(dto);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"Customer Saved").show();
                clearFields();
                loadAllCustomer();
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
    }

    @FXML
    void btnDeleteCustomerOnAction(ActionEvent event){
        String id  = txtId.getText();

        try {
            boolean isDeleted = CustomerModel.deleteCustomer(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Deleted").show();
                clearFields();
                loadAllCustomer();
            } else {
                new Alert(Alert.AlertType.ERROR, "Customer Not Deleted").show();
            }
        }catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnUpdateCustomerOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String mobile = txtMobile.getText();

        var dto  = new CustomerDto(id, name, address, mobile);

        try {
            boolean isUpdated = customerModel.updateCustomer(dto);
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
        String id = txtId.getText();
        try {
            CustomerDto customerDto = customerModel.searchCustomer(id);
            if (customerDto != null) {
                txtId.setText(customerDto.getId());
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
}
