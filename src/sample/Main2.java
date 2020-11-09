package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.*;

public class Main2 extends Application {
    Stage window;
    Scene scene1, scene2;

    public static void main(String[] args) {launch(args);}

    @Override
    public void start(Stage stage) {
        window = stage;

        Label label = new Label("Scene1");
        Button button1 = new Button("To scene2");
        button1.setOnAction(e -> {
            window.setScene(scene2);
        });

        VBox layout1 = new VBox();
        layout1.getChildren().addAll(label, button1);
        scene1 = new Scene(layout1, 300, 200);

        Button button2 = new Button("To scene1");
        button2.setOnAction(e -> {
            window.setScene(scene1);
        });
        StackPane layout2 = new StackPane();
        layout2.getChildren().add(button2);
        scene2 = new Scene(layout2, 200, 300);

        window.setScene(scene1);
        window.show();
    }
}
