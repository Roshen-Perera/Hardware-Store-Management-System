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
import lk.ijse.Jayabima.dto.CustomerDto;
import lk.ijse.Jayabima.dto.EmployeeDto;
import lk.ijse.Jayabima.dto.tm.EmployeeTm;
import lk.ijse.Jayabima.model.CustomerModel;
import lk.ijse.Jayabima.model.EmployeeModel;

import java.sql.SQLException;
import java.util.List;

public class EmployeeFormController {

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colMobile;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colRole;

    @FXML
    private TableColumn<?, ?> colSalary;

    @FXML
    private AnchorPane rootHome;

    @FXML
    private TableView<EmployeeTm> tblEmployee;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtMobile;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtRole;

    @FXML
    private TextField txtSalary;

    public void initialize() {
        setCellValueFactory();
        loadAllCustomer();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colMobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));

    }


    private void loadAllCustomer() {
        var model = new EmployeeModel();

        ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();
        try{
            List<EmployeeDto> dtoList = model.getAllEmployee();

            for (EmployeeDto dto : dtoList) {
                obList.add(
                        new EmployeeTm(
                                dto.getId(),
                                dto.getName(),
                                dto.getRole(),
                                dto.getAddress(),
                                dto.getSalary(),
                                dto.getMobile()
                        )
                );
            }
            tblEmployee.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtRole.setText("");
        txtAddress.setText("");
        txtSalary.setText("");
        txtMobile.setText("");
    }

    @FXML
    void btnAddEmployeeOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String role = txtRole.getText();
        String address = txtAddress.getText();
        String salary = txtSalary.getText();
        String mobile = txtMobile.getText();

        var dto = new EmployeeDto(id, name, role, address, salary, mobile);

        try {
            boolean isSaved = EmployeeModel.saveEmployee(dto);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"Employee details Saved").show();
                clearFields();
                loadAllCustomer();
            } else {
                new Alert(Alert.AlertType.ERROR, "Employee details not saved").show();;
            }
        }catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnClearEmployeeOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteEmployeeOnAction(ActionEvent event) {
        String id  = txtId.getText();

        try {
            boolean isDeleted = EmployeeModel.deleteEmployee(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee Deleted").show();
                clearFields();
                loadAllCustomer();
            } else {
                new Alert(Alert.AlertType.ERROR, "Employee Not Deleted").show();
            }
        }catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String id = txtId.getText();
        try {
            EmployeeDto employeeDto = EmployeeModel.searchEmployee(id);
            if (employeeDto != null) {
                txtId.setText(employeeDto.getId());
                txtName.setText(employeeDto.getName());
                txtRole.setText(employeeDto.getRole());
                txtAddress.setText(employeeDto.getAddress());
                txtSalary.setText(employeeDto.getSalary());
                txtMobile.setText(employeeDto.getMobile());
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Employee not found !").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateEmployeeOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String role = txtRole.getText();
        String address = txtAddress.getText();
        String salary = txtSalary.getText();
        String mobile = txtMobile.getText();

        var dto = new EmployeeDto(id, name, role, address, salary, mobile);


        try {
            boolean isUpdated = EmployeeModel.updateEmployee(dto);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee details updated").show();;
                clearFields();
                loadAllCustomer();
            } else {
                new Alert(Alert.AlertType.ERROR, "Employee details not updated").show();;
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            clearFields();
        }
    }
}
