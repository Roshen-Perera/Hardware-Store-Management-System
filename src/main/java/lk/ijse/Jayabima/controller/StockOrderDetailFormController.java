package lk.ijse.Jayabima.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import lk.ijse.Jayabima.dto.StockOrderDetailDto;
import lk.ijse.Jayabima.dto.tm.StockOrderDetailTm;
import lk.ijse.Jayabima.model.StockOrderModel;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class StockOrderDetailFormController {

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colsupId;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private TableView<StockOrderDetailTm> tblStockDetails;


    public void initialize(){
        setDateAndTime();
        setCellValueFactory();
        loadAllCustomer();
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
        colId.setCellValueFactory(new PropertyValueFactory<>("stockId"));
        colsupId.setCellValueFactory(new PropertyValueFactory<>("supId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    private void loadAllCustomer() {
        var model = new StockOrderModel();

        ObservableList<StockOrderDetailTm> obList = FXCollections.observableArrayList();
        try{
            List<StockOrderDetailDto> dtoList = model.getAllStockDetails();

            for (StockOrderDetailDto dto : dtoList) {
                obList.add(
                        new StockOrderDetailTm(
                                dto.getStockId(),
                                dto.getSupId(),
                                dto.getDate()
                        )
                );
            }
            tblStockDetails.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
