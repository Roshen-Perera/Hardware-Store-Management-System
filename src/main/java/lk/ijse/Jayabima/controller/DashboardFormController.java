package lk.ijse.Jayabima.controller;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;
import lk.ijse.Jayabima.db.DbConnection;
import lk.ijse.Jayabima.dto.CustomerDto;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class DashboardFormController {


    @FXML
    private Label lblCustomer;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblEmployee;

    @FXML
    private Label lblItems;

    @FXML
    private Label lblItemOrders;

    @FXML
    private Label lblStockOrders;

    @FXML
    private Label lblSupplier;

    @FXML
    private Label lblTime;



    public void initialize() throws SQLException {
        setDateAndTime();
        countCustomer();
        countEmployee();
        countSupplier();
        countItem();
        countStockOrder();
        countItemOrder();
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

    private void countCustomer() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        Statement stmt = connection.createStatement();
        //Query to get the number of rows in a table
        String query = "select count(*) from customer";
        //Executing the query
        ResultSet rs = stmt.executeQuery(query);
        //Retrieving the result
        rs.next();
        int count = rs.getInt(1);
        lblCustomer.setText(String.valueOf(count));

    }

    private void countEmployee() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        Statement stmt = connection.createStatement();
        //Query to get the number of rows in a table
        String query = "select count(*) from employee";
        //Executing the query
        ResultSet rs = stmt.executeQuery(query);
        //Retrieving the result
        rs.next();
        int count = rs.getInt(1);
        lblEmployee.setText(String.valueOf(count));
    }

    private void countSupplier() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        Statement stmt = connection.createStatement();
        //Query to get the number of rows in a table
        String query = "select count(*) from supplier";
        //Executing the query
        ResultSet rs = stmt.executeQuery(query);
        //Retrieving the result
        rs.next();
        int count = rs.getInt(1);
        lblSupplier.setText(String.valueOf(count));
    }

    private void countItem() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        Statement stmt = connection.createStatement();
        //Query to get the number of rows in a table
        String query = "select sum(item_qty) from item";
        //Executing the query
        ResultSet rs = stmt.executeQuery(query);
        //Retrieving the result
        rs.next();
        int count = rs.getInt(1);
        lblItems.setText(String.valueOf(count));
    }

    private void countStockOrder() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        Statement stmt = connection.createStatement();
        //Query to get the number of rows in a table
        String query = "select count(*) from stock_order";
        //Executing the query
        ResultSet rs = stmt.executeQuery(query);
        //Retrieving the result
        rs.next();
        int count = rs.getInt(1);
        lblStockOrders.setText(String.valueOf(count));
    }

    private void countItemOrder() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        Statement stmt = connection.createStatement();
        //Query to get the number of rows in a table
        String query = "select count(*) from orders";
        //Executing the query
        ResultSet rs = stmt.executeQuery(query);
        //Retrieving the result
        rs.next();
        int count = rs.getInt(1);
        lblItemOrders.setText(String.valueOf(count));
    }
}
