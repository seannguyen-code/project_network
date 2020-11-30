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



public class RuleController
{
    private RootController rootController;

    /**
     * Listeners, for when the user clicks a button
     * @throws Exception when file not found
     */
    @FXML
    public void backPress(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Opening.fxml"));
        Pane pane = loader.load();
        OpeningController openingController = loader.getController();
        openingController.setRootController(rootController);
        rootController.setScreen(pane);
    }


    public void setRootController(RootController rootController) {
        this.rootController = rootController;
    }


}


