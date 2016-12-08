import controller.MediaLibraryController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.NoSQLHandler;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("mediaLibrary.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("mediaLibrary.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Media Library");
        primaryStage.setScene(new Scene(root));
        MediaLibraryController controller = loader.getController();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.out.println("Closing connection...");
                controller.closeConnection();
            }
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        //Application.launch(args);
        NoSQLHandler noSQLHandler = new NoSQLHandler();
        try {
            int temp = noSQLHandler.addDirector("George Lucas");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
