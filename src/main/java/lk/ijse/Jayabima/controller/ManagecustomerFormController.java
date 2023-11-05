package lk.ijse.Jayabima.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.Jayabima.dto.ManagecustomerDto;
import lk.ijse.Jayabima.model.ManagecustomerModel;

import java.sql.SQLException;

public class ManagecustomerFormController {

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtMobile;

    @FXML
    private TextField txtName;

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
        String mobile = txtAddress.getText();

        var dto = new ManagecustomerDto(id, name, address, mobile);

        try {
            boolean isSaved = ManagecustomerModel.saveCustomer(dto);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"Customer Saved");
                clearFields();
            }
        }catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnClearCustomerOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteCustomerOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateCustomerOnAction(ActionEvent event) {

    }
}
