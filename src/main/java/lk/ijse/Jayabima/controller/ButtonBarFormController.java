package lk.ijse.Jayabima.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.Jayabima.dto.SignUpDto;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.io.Serializable;

public class ButtonBarFormController implements Serializable {
    @FXML
    private Label lbluser;

    static Label staticLabel;

    @FXML
    private JFXButton cusbtn;

    @FXML
    private JFXButton dashbtn;

    @FXML
    private JFXButton salarybtn;

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

    private SignUpDto signUpDto;


    @FXML
    private AnchorPane rootNode;
    public void initialize() throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        rootHome.getChildren().clear();
        rootHome.getChildren().add(rootNode);
        Image correct = new Image("/image/icons8-correct-64.png");
        Notifications.create()
                .title("Login Successfull")
                .text("Welcome to Jayabima Hardware Management System")
                .graphic(new ImageView(correct))
                .hideAfter(Duration.seconds(2))
                .show();
        staticLabel = lbluser;
    }

    private void selectCss(JFXButton mainFormBtn) {
        this.dashbtn.getStyleClass().remove("select_button");
        this.cusbtn.getStyleClass().remove("select_button");
        this.supbtn.getStyleClass().remove("select_button");
        this.itembtn.getStyleClass().remove("select_button");
        this.empbtn.getStyleClass().remove("select_button");
        this.orderbtn.getStyleClass().remove("select_button");
        this.placebtn.getStyleClass().remove("select_button");

        this.dashbtn.getStyleClass().add("default_button");
        this.cusbtn.getStyleClass().add("default_button");
        this.supbtn.getStyleClass().add("default_button");
        this.itembtn.getStyleClass().add("default_button");
        this.empbtn.getStyleClass().add("default_button");
        this.orderbtn.getStyleClass().add("default_button");
        this.placebtn.getStyleClass().add("default_button");

        mainFormBtn.getStyleClass().remove("default_button");
        mainFormBtn.getStyleClass().add("select_button");
    }

    @FXML
    void btnDashBoardOnAction(ActionEvent event) throws IOException {
        selectCss(dashbtn);
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));
        this.rootHome.getChildren().clear();
        this.rootHome.getChildren().add(rootNode);
    }

    @FXML
    void btnManageCustomersOnAction(ActionEvent event) throws IOException {
        selectCss(cusbtn);
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/customer_form.fxml"));
        this.rootHome.getChildren().clear();
        this.rootHome.getChildren().add(rootNode);
    }

    @FXML
    void btnManageEmployees(ActionEvent event) throws IOException {
        selectCss(dashbtn);
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/employee_form.fxml"));
        this.rootHome.getChildren().clear();
        this.rootHome.getChildren().add(rootNode);
    }

    @FXML
    void btnManageItemsOnAction(ActionEvent event) throws IOException {
        selectCss(itembtn);
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/item_form.fxml"));
        this.rootHome.getChildren().clear();
        this.rootHome.getChildren().add(rootNode);
    }

    @FXML
    void btnManageOrders(ActionEvent event) throws IOException {
        selectCss(orderbtn);
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/stockorder_form.fxml"));
        this.rootHome.getChildren().clear();
        this.rootHome.getChildren().add(rootNode);
    }

    @FXML
    void btnManageSuppliersOnAction(ActionEvent event) throws IOException {
        selectCss(supbtn);
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/supplier_form.fxml"));
        this.rootHome.getChildren().clear();
        this.rootHome.getChildren().add(rootNode);
    }

    @FXML
    void btnPlaceOrder(ActionEvent event) throws IOException {
        selectCss(placebtn);
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/placeorder_form.fxml"));
        this.rootHome.getChildren().clear();
        this.rootHome.getChildren().add(rootNode);
    }
    @FXML
    void btnLogoutOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));
        Scene scene =new Scene(rootNode);
        Stage primaryStage = (Stage) this.rootNode.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Jayabima Hardware");
    }
}
