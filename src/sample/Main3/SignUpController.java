package sample.Main3;


import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.IOException;


public class SignUpController
{
	private RootController rootController;


	@FXML private TextField nickname;
	@FXML private Button submit;

	/**
	 * Listeners, for when the user clicks a button
	 * @throws Exception when file not found
	 */



	@FXML
	public void backToMenu(ActionEvent event) throws IOException {
		rootController.loadMenuScreen();
	}


    public void submitName(ActionEvent event) throws Exception {
    	//rootController.loadMenuScreen();
    	String name = nickname.getText();
		//nickname.setText("");

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		if (name.isEmpty())
			alert.setContentText("Please enter nickname to join!");
		else {
			alert.setContentText("Registration Completed Successfully");
			nickname.setEditable(false);
			nickname.setDisable(true);
		}
		//chỗ này nhập các trường hợp input nickname
		alert.show();
    }

	public void setRootController(RootController rootController) {
		this.rootController = rootController;
	}
}