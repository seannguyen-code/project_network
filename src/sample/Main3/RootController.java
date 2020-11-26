package sample.Main3;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class RootController {

    @FXML
    private StackPane rootStackPane;

    public void initialize() throws IOException {
        loadMenuScreen();
    }

    public void loadMenuScreen() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Opening.fxml"));
        Pane pane = loader.load();
        OpeningController menuController = loader.getController();
        menuController.setRootController(this);
        setScreen(pane);
    }

    public void setScreen(Pane pane) {
        rootStackPane.getChildren().clear();
        rootStackPane.getChildren().add(pane);
    }
}


