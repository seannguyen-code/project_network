package sample.Main3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class Controller {
    @FXML
    private TextField Height;

    public void Submit(ActionEvent actionEvent) {
        String height = Height.getText();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Chiều cao của bạn là " + height + " cm");
        alert.show();
    }
}
