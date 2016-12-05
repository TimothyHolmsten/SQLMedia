import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.QueryException;
import model.SQLHandler;

import java.sql.SQLException;
import java.util.ArrayList;

import objectmodels.*;
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("mediaLibrary.fxml"));
        primaryStage.setTitle("Media Library");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
       Application.launch(args);

    }
}
