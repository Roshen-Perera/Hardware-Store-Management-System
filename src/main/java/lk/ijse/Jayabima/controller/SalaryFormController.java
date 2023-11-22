package lk.ijse.Jayabima.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import lk.ijse.Jayabima.dto.SalaryDto;
import lk.ijse.Jayabima.dto.tm.SalaryTm;
import lk.ijse.Jayabima.model.SalaryModel;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SalaryFormController {

    @FXML
    private TextField txtStatus;

    @FXML
    private Label lblEmployeeId;

    @FXML
    private Label lblSalary;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colSalary;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private TableView<SalaryTm> tblSalary;

    SalaryModel salaryModel = new SalaryModel();

    public void initialize(){
        setDateAndTime();
        setCellValueFactory();
        loadAllSalary();
        tableListener();
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
    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void loadAllSalary() {
        var model = new SalaryModel();

        ObservableList<SalaryTm> obList = FXCollections.observableArrayList();
        try{
            List<SalaryDto> dtoList = model.getAllSalary();

            for (SalaryDto dto : dtoList) {
                obList.add(
                        new SalaryTm(
                                dto.getId(),
                                dto.getSalary(),
                                dto.getStatus()
                        )
                );
            }
            tblSalary.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setData(SalaryTm row) {
        lblEmployeeId.setText(row.getId());
        lblSalary.setText(row.getSalary());
        txtStatus.setText("");
    }

    private void tableListener() {
        tblSalary.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
//            System.out.println(newValue);
            setData(newValue);
        });
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String empId = lblEmployeeId.getText();
        String salary = lblSalary.getText();
        String status = txtStatus.getText();

        var dto = new SalaryDto(empId, salary, status);
        try {
            boolean isSaved = salaryModel.updateStatus(dto);
            if(isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION,"Salary Status Saved").show();
//                clearFields();
                loadAllSalary();
            }else {
                new Alert(Alert.AlertType.ERROR, "Salary Status not saved").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
