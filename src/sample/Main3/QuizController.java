package sample.Main3;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class QuizController
{
    private RootController rootController;

    public static Thread th;


    @FXML private TextField answer;
    @FXML private Button submit;
    @FXML private TextArea question;
    /**
     * Listeners, for when the user clicks a button
     * @throws Exception when file not found
     */

    public QuizController() {


        th = new Thread(() -> {
            try {
                while(true) {
                    String sms = Client.dis.readUTF();
                    System.out.println(sms);

                    if (sms.contains("Calculate: ")) {
                        question.setText(sms);
                    }

                    else {
                        System.out.println("abc");
                    }
                }
            } catch(Exception E) {
                E.printStackTrace();
            }

        });

        th.start();

    }

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
