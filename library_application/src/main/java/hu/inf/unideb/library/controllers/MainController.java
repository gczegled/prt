package hu.inf.unideb.library.controllers;

import hu.inf.unideb.library.models.EntityManagement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import hu.inf.unideb.library.view.Main;
import org.hibernate.service.spi.ServiceException;

import java.util.HashMap;
import java.util.Map;

public class MainController {

    @FXML
    private TextField userName;

    @FXML
    private PasswordField password;

    @FXML
    public void login() throws Exception {
        try {
            Map<String, String> properties = new HashMap<>();
            properties.put("javax.persistence.jdbc.user", userName.getText());
            properties.put("javax.persistence.jdbc.password", password.getText());
            new EntityManagement().createEntityManager("library", properties);
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/library.fxml"));
            Stage stage = new Main().getStage();
            stage.setScene(new Scene(root, 800, 600));
            stage.setY(stage.getY() - 50);
            stage.setX(stage.getX() - 100);
            stage.show();
        }
        catch (ServiceException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hiba történt a bejelentkezés során");
            alert.setHeaderText(null);
            alert.setContentText("Hiba történt az adatbázishoz való kapcsolódás során.");
            alert.show();
        }
    }

    @FXML
    public void closeApp() {
        new Main().getStage().close();
    }
}
