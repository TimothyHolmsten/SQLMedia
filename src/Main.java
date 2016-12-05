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
       // Application.launch(args);
        SQLHandler sql = null;
        try {
            sql = new SQLHandler();
            ArrayList<Media> media= sql.getAllMedia();
            System.out.print(media.toString());
               System.out.println(sql.getArtistById(1).getName());
            Album album= sql.getAlbumById(1);
           // System.out.print(album.toString());
            //   sql.addArtistToSong(sql.getArtistById(1), sql.getSongById(1), sql.getUserById(1));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (QueryException e) {
            e.printStackTrace();
        }
    }
}
