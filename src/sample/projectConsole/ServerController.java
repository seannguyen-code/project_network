package sample.projectConsole;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;


public class ServerController {
    @FXML

    public ScrollPane sp;

    @FXML
    private void initialize()
    {
        Text txt = new Text("The look and feel of JavaFX applications "
                + "can be customized. Cascading Style Sheets (CSS) separate "
                + "appearance and style from implementation so that developers can "
                + "concentrate on coding. Graphic designers can easily "
                + "customize the appearance and style of the application "
                + "through the CSS. If you have a web design background,"
                + " or if you would like to separate the user interface (UI) "
                + "and the back-end logic, then you can develop the presentation"
                + " aspects of the UI in the FXML scripting language and use Java "
                + "code for the application logic. If you prefer to design UIs "
                + "without writing code, then use JavaFX Scene Builder. As you design the UI, "
                + "Scene Builder creates FXML markup that can be ported to an Integrated Development "
                + "Environment (IDE) so that developers can add the business logic.");
        txt.wrappingWidthProperty().bind(sp.widthProperty());
        sp.setContent(txt);
    }



}
