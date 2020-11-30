package sample.Main3;


import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.IOException;


public class QuizController
{
    private RootController rootController;


    @FXML private TextField answer;
    @FXML private Button submit;

    /**
     * Listeners, for when the user clicks a button
     * @throws Exception when file not found
     */



    @FXML
    public void submitAns(ActionEvent event) throws Exception {
        //rootController.loadMenuScreen();
        String ans = answer.getText();
        //nickname.setText("");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (ans.isEmpty())
            alert.setContentText("The answer field is empty");
        else {
            alert.setContentText("Server already received your answer. Please wait!");
            answer.setEditable(false);
            answer.setDisable(true);
        }
        // các trường hợp khi nhập answer
        alert.show();
    }

    public void setRootController(RootController rootController) {
        this.rootController = rootController;
    }



}
