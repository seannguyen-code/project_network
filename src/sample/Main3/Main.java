package sample.Main3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javafx.scene.image.Image;
import javafx.stage.StageStyle;

public class Main extends Application {
    private double x, y;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Opening.fxml"));
            AnchorPane rootStackPane = loader.load();
            Scene scene = new Scene(rootStackPane);
            primaryStage.setScene(scene);

            rootStackPane.setOnMousePressed(mouseEvent -> {
                x = mouseEvent.getSceneX();
                y = mouseEvent.getSceneY();
            });
            rootStackPane.setOnMouseDragged(mouseEvent -> {
                primaryStage.setX(mouseEvent.getScreenX() - x);
                primaryStage.setY(mouseEvent.getScreenY() - y);
            });

            primaryStage.show();
        }
    }


