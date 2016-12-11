import controller.MediaLibraryController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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
        Application.launch(args);

        try {
            /*
            int temp = noSQLHandler.addDirector("George Lucas");
            Director director = new Director("Lucas");

            ArrayList<Director>directors=noSQLHandler.getDirectors();
            System.out.println(directors.toString());

            User user = new User("Douglas");

            noSQLHandler.addMovie("Star War","Scifi",director,user);
            ArrayList<Song> songs = new ArrayList<Song>();
            songs.add(new Song("song 1","rock",new User("Douglas")));
            songs.add(new Song("song 2","rock",new User("Stefan")));

            noSQLHandler.addAlbum("High way to hell",songs,new User("Douglas"));

            ArrayList<Movie>  movies = noSQLHandler.getMovies();

            for (Movie m: movies) {
                System.out.println(m.getDirector().getName());
            }
            */

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
