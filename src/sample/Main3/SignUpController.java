package sample.Main3;

import sample.Main3.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class SignUpController
{
	private RootController rootController;
	boolean success = false;

	@FXML private TextField nickname;
	@FXML private Button submit;

	/**
	 * Listeners, for when the user clicks a button
	 * @throws Exception when file not found
	 */


	public SignUpController() throws IOException {
		Client.socket = new Socket(InetAddress.getLocalHost(), 15797);
		Client.dos = new DataOutputStream(Client.socket.getOutputStream());
		Client.dis = new DataInputStream(Client.socket.getInputStream());

	}

	@FXML
	public void backToMenu(ActionEvent event) throws IOException {
		rootController.loadMenuScreen();
	}


    public void submitName(ActionEvent event) throws Exception {
    	//rootController.loadMenuScreen();
    	String name = nickname.getText();
		//nickname.setText("");

		Alert alert = new Alert(Alert.AlertType.INFORMATION);

		if (name.isEmpty()) {
			alert.setContentText("Please enter a nickname to join!");
			alert.show();
			return;
		}
		else if (!name.matches("^([A-Za-z][A-Za-z0-9_]*)$")) {
			alert.setContentText("Wrong format, Try again!");
			alert.show();
			return;
		}
		else if (name.length() > 10) {
			alert.setContentText("Name is too long, Try again!");
			alert.show();
			return;
		}

		Client.dos.writeUTF(nickname.getText());
		String sms = Client.dis.readUTF();

		if (sms.contains("Ten da ton tai, doi ten di")) {
			alert.setContentText("Name already exists!");
			alert.show();
			return;
		}

		alert.setContentText("Registration Completed Successfully");
		alert.show();

		Client.name = name;
		nickname.setEditable(false);
		nickname.setDisable(true);
		success = true;
		//chỗ này nhập các trường hợp input nickname
		if (success) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Quiz.fxml"));
			Pane pane = loader.load();
			QuizController quizController = loader.getController();
			quizController.setRootController(rootController);
			rootController.setScreen(pane);
		}
    }

	public void setRootController(RootController rootController) {
		this.rootController = rootController;
	}
}