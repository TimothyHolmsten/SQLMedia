import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("test.fxml"));
        primaryStage.setTitle("HelloWorld");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
        /*SQLHandler sql = null;
        try {
            sql = new SQLHandler();
            System.out.println(sql.getArtistById(1).getName());

            sql.addArtistToSong(sql.getArtistById(1), sql.getSongById(1), sql.getUserById(1));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (QueryException e) {
            e.printStackTrace();
        }*/
    }
}
