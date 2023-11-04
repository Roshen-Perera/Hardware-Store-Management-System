package lk.ijse.Jayabima;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Launcher extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));
        Scene scene = new Scene(parent);
        Image image = new Image("image/jayabimaicon.png");

        stage.setScene(scene);
        stage.setTitle("Jayabima Hardware");
        stage.show();
        stage.getIcons().add(image);
    }
}
