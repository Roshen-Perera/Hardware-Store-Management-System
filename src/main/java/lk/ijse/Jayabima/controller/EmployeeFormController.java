package lk.ijse.Jayabima.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.Jayabima.dto.CustomerDto;
import lk.ijse.Jayabima.dto.EmployeeDto;
import lk.ijse.Jayabima.dto.tm.CustomerTm;
import lk.ijse.Jayabima.dto.tm.EmployeeTm;
import lk.ijse.Jayabima.model.CustomerModel;
import lk.ijse.Jayabima.model.EmployeeModel;
import org.controlsfx.control.Notifications;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

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

    @FXML
    private Label lblId;

    public void initialize() {
        setCellValueFactory();
        loadAllCustomer();
        tableListener();
        generateNextCustomerID();
    }

    private boolean btnClearPressed = false;
    private void generateNextCustomerID(){
        try {
            String previousEmployeeID = lblId.getText();
            String employeeID = EmployeeModel.generateNextEmployee();
            lblId.setText(employeeID);
            clearFields();
            if (btnClearPressed){
                lblId.setText(previousEmployeeID);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void tableListener() {
        tblEmployee.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
//            System.out.println(newValue);
            setData(newValue);
        });
    }

    private void setData(EmployeeTm row) {
        lblId.setText(row.getId());
        txtName.setText(row.getName());
        txtRole.setText(row.getRole());
        txtAddress.setText(row.getAddress());
        txtSalary.setText(row.getSalary());
        txtMobile.setText(row.getMobile());
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
        String id = lblId.getText();
        String name = txtName.getText();
        String role = txtRole.getText();
        String address = txtAddress.getText();
        String salary = txtSalary.getText();
        String mobile = txtMobile.getText();

        try {
            if (!validateEmployeeDetails()) {
                return;
            }
            clearFields();
            var dto = new EmployeeDto(id, name, role, address, salary, mobile);
            boolean isSaved = EmployeeModel.saveEmployee(dto);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"Employee details Saved").show();
                clearFields();
                loadAllCustomer();
                generateNextCustomerID();
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
        generateNextCustomerID();
    }

    @FXML
    void btnDeleteEmployeeOnAction(ActionEvent event) {
        String id  = lblId.getText();

        try {
            boolean isDeleted = EmployeeModel.deleteEmployee(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee Deleted").show();
                clearFields();
                loadAllCustomer();
                generateNextCustomerID();
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
        String id = lblId.getText();
        String name = txtName.getText();
        String role = txtRole.getText();
        String address = txtAddress.getText();
        String salary = txtSalary.getText();
        String mobile = txtMobile.getText();

        try {
            if (!validateEmployeeDetails()) {
                return;
            }
            clearFields();
            var dto = new EmployeeDto(id, name, role, address, salary, mobile);
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
    public boolean validateEmployeeDetails() {
        boolean isValid = true;

        if (!Pattern.matches("[A-Za-z]{4,}", txtName.getText())) {
            showErrorNotification("Invalid Employee Name", "The employee name you entered is invalid");
            isValid = false;
        }

        if (!Pattern.matches("[A-Za-z]{4,}", txtRole.getText())) {
            showErrorNotification("Invalid Role", "The role you entered is invalid");
            isValid = false;
        }

        if (!Pattern.matches("[A-Za-z]{4,}", txtAddress.getText())) {
            showErrorNotification("Invalid address", "The address you entered is invalid");
            isValid = false;
        }

        if (!Pattern.matches("\\d{4,}", txtSalary.getText())) {
            showErrorNotification("Invalid Salary", "The salary you entered is invalid");
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
