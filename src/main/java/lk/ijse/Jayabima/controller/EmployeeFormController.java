package lk.ijse.Jayabima.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.Jayabima.db.DbConnection;
import lk.ijse.Jayabima.dto.CustomerDto;
import lk.ijse.Jayabima.dto.EmployeeDto;
import lk.ijse.Jayabima.dto.SalaryDto;
import lk.ijse.Jayabima.dto.tm.CustomerTm;
import lk.ijse.Jayabima.dto.tm.EmployeeTm;
import lk.ijse.Jayabima.model.CustomerModel;
import lk.ijse.Jayabima.model.EmployeeModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    EmployeeModel employeeModel = new EmployeeModel();

    ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();

    public void initialize() {
        setCellValueFactory();
        loadAllCustomer();
        tableListener();
        generateNextCustomerID();
        setDateAndTime();
    }

    private void setDateAndTime(){
        Platform.runLater(() -> {
            lblDate.setText(String.valueOf(LocalDate.now()));

            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1), event -> {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");
                String timeNow = LocalTime.now().format(formatter);
                lblTime.setText(timeNow);
            }));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        });
    }

    private boolean btnClearPressed = false;
    private void generateNextCustomerID(){
        try {
            String previousEmployeeID = lblId.getText();
            String employeeID = employeeModel.generateNextEmployee();
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
            boolean isSaved = employeeModel.saveEmployee(dto);
            var dto1 = new SalaryDto(id, salary, "");
            employeeModel.saveSalary(dto1);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"Employee details Saved").show();
                clearFields();
                loadAllCustomer();
                generateNextCustomerID();
            } else {
                new Alert(Alert.AlertType.ERROR, "Employee details not saved").show();
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
            boolean isDeleted = employeeModel.deleteEmployee(id);
            employeeModel.deleteSalary(id);
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
            EmployeeDto employeeDto = employeeModel.searchEmployee(id);
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
            boolean isUpdated = employeeModel.updateEmployee(dto);
            var dto1 = new SalaryDto(id, salary, "");
            employeeModel.updateSalary(dto1);
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

        if (!Pattern.matches("^[a-zA-Z0-9\\s]*$", txtName.getText())) {
            showErrorNotification("Invalid Employee Name", "The employee name you entered is invalid");
            isValid = false;
        }

        if (!Pattern.matches("^[a-zA-Z0-9\\s]*$", txtRole.getText())) {
            showErrorNotification("Invalid Role", "The role you entered is invalid");
            isValid = false;
        }

        if (!Pattern.matches("^[A-Za-z0-9'\\/\\.\\,\\s]{5,}$", txtAddress.getText())) {
            showErrorNotification("Invalid address", "The address you entered is invalid");
            isValid = false;
        }

        if (!Pattern.matches("^[0-9]+\\.?[0-9]*$", txtSalary.getText())) {
            showErrorNotification("Invalid Salary", "The salary you entered is invalid");
            isValid = false;
        }

        if (!Pattern.matches("^(?:0|94|\\+94|0094)?(?:(11|21|23|24|25|26|27|31|32|33|34|35|36|37|38|41|45|47|51|52|54|55|57|63|65|66|67|81|91)(0|2|3|4|5|7|9)|7(0|1|2|4|5|6|7|8)\\d)\\d{6}$", txtMobile.getText())) {
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

    @FXML
    void btnSalaryDetailsOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/salary_form.fxml"));
        Scene scene = new Scene(anchorPane);

        Stage stage = new Stage();
        stage.setTitle("Employee Salary Details");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void btnGetReportOnAction(ActionEvent event) throws SQLException, JRException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/reports/EmployeeReport.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);

        JasperReport jasperReport = JasperCompileManager.compileReport(load);
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperReport,
                null,
                DbConnection.getInstance().getConnection()
        );
        JasperViewer.viewReport(jasperPrint, false);
    }

}
