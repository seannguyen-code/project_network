package sample.Main3;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

/* Opening screen for game */

public class SignUpController
{
	private RootController rootController;

	//private Controller mainApp;		// Reference to the main application
//	@FXML private TextField nickname;
//	@FXML private Button submit;
//	//@FXML private Button quitButton;
//
//	/**
//	 * Initializes the controller class after the fxml file.
//	 */
//	@FXML private void initialize() {
//        nickname.setText("Enter your nickname:");
//        submit.setText("Submit");
//    }
//
//	/**
//	 * Called by the main application to give a reference back to itself.
//	 * @param mainApp is Mork, the controller itself
//	 */
////	//public void setMainApp(Controller mainApp) {
////		this.mainApp = mainApp;
////	}
//
//	/**
//	 * Listeners, for when the user clicks a button
//	 * @throws Exception when file not found
//	 */



	@FXML
	public void backToMenu(ActionEvent event) throws IOException {
		rootController.loadMenuScreen();
	}
//    public void enterName(ActionEvent event) throws Exception {
//    	//rootController.loadMenuScreen();
////    	String name = nickname.getText().trim();
////
////        FXMLLoader loader = new FXMLLoader();
////        loader.setLocation(getClass().getResource("/Main3/SignUp.fxml"));
//    }

	public void setRootController(RootController rootController) {
		this.rootController = rootController;
	}



}