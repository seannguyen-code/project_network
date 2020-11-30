package sample.Main3;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

/* Opening screen for game */

public class OpeningController
{
	private RootController rootController;

	//private Controller mainApp;		// Reference to the main application

//	@FXML private Button ruleButton;
//	@FXML private Button startButton;
//	@FXML private Button quitButton;
//
//	/**
//	 * Initializes the controller class after the fxml file.
//	 */
//	@FXML private void initialize() {
//        ruleButton.setText("RULES");
//        startButton.setText("START GAME");
//        quitButton.setText("QUIT");
//    }

	/**
	 * Listeners, for when the user clicks a button
	 * @throws Exception when file not found
	 */
	@FXML
    public void startPress(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
		Pane pane = loader.load();
		SignUpController signUpController = loader.getController();
		signUpController.setRootController(rootController);
		rootController.setScreen(pane);
    }


	public void rulePress(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Rule.fxml"));
		Pane pane = loader.load();
		RuleController ruleController = loader.getController();
		ruleController.setRootController(rootController);
		rootController.setScreen(pane);
	}

	public void quitPress(ActionEvent event) throws IOException {
		System.exit(0);
	}

	public void setRootController(RootController rootController) {
		this.rootController = rootController;
	}


}