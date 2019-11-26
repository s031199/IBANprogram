import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.util.Locale;
import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Controller.fxml"));
        Stage stageprisijungimas = new Stage();
        stageprisijungimas.setTitle("Program");
        stageprisijungimas.setScene(new Scene(root));
        stageprisijungimas.show();

    }
    public static void main(String[] args) {
        launch(args);
    }
}