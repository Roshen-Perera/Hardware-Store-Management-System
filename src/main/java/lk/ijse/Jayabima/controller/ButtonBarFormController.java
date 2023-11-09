package lk.ijse.Jayabima.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.io.Serializable;

public class ButtonBarFormController implements Serializable {
    @FXML
    private JFXButton cusbtn;

    @FXML
    private JFXButton dashbtn;

    @FXML
    private JFXButton supbtn;

    @FXML
    private JFXButton empbtn;

    @FXML
    private JFXButton itembtn;

    @FXML
    private JFXButton orderbtn;

    @FXML
    private JFXButton placebtn;

    @FXML
    private AnchorPane rootHome;
    public void initialize() throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        rootHome.getChildren().clear();
        rootHome.getChildren().add(rootNode);
    }



    @FXML
    void btnDashBoardOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));
        this.rootHome.getChildren().clear();
        this.rootHome.getChildren().add(rootNode);
    }

    @FXML
    void btnManageCustomersOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/customer_form.fxml"));
        this.rootHome.getChildren().clear();
        this.rootHome.getChildren().add(rootNode);
    }

    @FXML
    void btnManageEmployees(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/employee_form.fxml"));
        this.rootHome.getChildren().clear();
        this.rootHome.getChildren().add(rootNode);
    }

    @FXML
    void btnManageItemsOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/item_form.fxml"));
        this.rootHome.getChildren().clear();
        this.rootHome.getChildren().add(rootNode);
    }

    @FXML
    void btnManageOrders(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/order_form.fxml"));
        this.rootHome.getChildren().clear();
        this.rootHome.getChildren().add(rootNode);
    }

    @FXML
    void btnManageSuppliersOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/supplier_form.fxml"));
        this.rootHome.getChildren().clear();
        this.rootHome.getChildren().add(rootNode);
    }

    @FXML
    void btnPlaceOrder(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/placeorder_form.fxml"));
        this.rootHome.getChildren().clear();
        this.rootHome.getChildren().add(rootNode);
    }
}
