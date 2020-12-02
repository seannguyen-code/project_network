package sample.Main3;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class QuizController
{
    private RootController rootController;

    public static Thread th;

    @FXML private TextArea chatlog;
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
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                // Update UI here.
                                answer.setText("");
                                question.setText(sms);
                                answer.setEditable(true);
                                answer.setDisable(false);
                                submit.setDisable(false);
                            }
                        });
                    }
                    else if (sms.contains("WIN")){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                // Update UI here.
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("Win.fxml"));
                                Pane pane = null;
                                try {
                                    pane = loader.load();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                WinController winController = loader.getController();
                                winController.setRootController(rootController);
                                rootController.setScreen(pane);
                            }
                        });
                    }
                    else if (sms.contains("LOSE")) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                // Update UI here.
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("Lose.fxml"));
                                Pane pane = null;
                                try {
                                    pane = loader.load();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                LoseController loseController = loader.getController();
                                loseController.setRootController(rootController);
                                rootController.setScreen(pane);
                            }
                        });
                    }
                    else if (sms.equals("Time left: 1")) {
                        chatlog.appendText("Server: " + sms + "\n");
                        Client.dos.writeUTF("999999999");
                    }
                    else {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                // Update UI here.
                                chatlog.appendText(sms + "\n");
                            }
                        });
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
        String ans = answer.getText();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (ans.isEmpty())
            alert.setContentText("The answer field is empty");
        else {
            Client.dos.writeUTF(ans);

            alert.setContentText("Server already received your answer. Please wait!");
            answer.setEditable(false);
            answer.setDisable(true);
            submit.setDisable(true);
        }
        alert.show();
    }

    public void setRootController(RootController rootController) {
        this.rootController = rootController;
    }

}
