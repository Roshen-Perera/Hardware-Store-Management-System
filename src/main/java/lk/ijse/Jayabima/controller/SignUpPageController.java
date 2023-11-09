package lk.ijse.Jayabima.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.Jayabima.dto.SignUpDto;
import lk.ijse.Jayabima.model.SignUpModel;

import java.io.IOException;
import java.sql.SQLException;

public class SignUpPageController {
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtMobile;
    @FXML
    private TextField txtUserName;
    @FXML
    private PasswordField txtRepeatPassword;

    @FXML
    private AnchorPane rootNode;

    public void initialize(){

    }
    private void loadAllUser() {
        var model = new SignUpModel();

    }
    private void clearFields() {
        txtUserName.setText("");
        txtMobile.setText("");
        txtPassword.setText("");
        txtRepeatPassword.setText("");
    }



    @FXML
    void btnSignInFormOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));
        Scene scene =new Scene(rootNode);
        Stage primaryStage = (Stage) this.rootNode.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Jayabima Hardware");
        primaryStage.show();
    }

    @FXML
    void btnSignUpOnAction(ActionEvent event) throws IOException {
        String name = txtUserName.getText();
        String mobile = txtMobile.getText();
        String password = txtPassword.getText();
        String repeatpassword = txtRepeatPassword.getText();




        var dto = new SignUpDto(name, mobile, password, repeatpassword);

        try {
            if(name.isEmpty() || mobile.isEmpty() || password.isEmpty() || repeatpassword.isEmpty()) {
                new Alert(Alert.AlertType.ERROR,"Empty").show();
                return;
            }

            if(!password.equals(repeatpassword)) {
                new Alert(Alert.AlertType.ERROR,"Password Do not Match").show();
                return;
            }
            boolean isSaved = SignUpModel.saveUser(dto);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"User Saved").show();
            }
        }catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
