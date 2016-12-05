package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.QueryException;
import model.SQLHandler;
import objectmodels.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MediaLibraryController implements Initializable {

    private SQLHandler sql;

    @FXML
    private ComboBox<String> comboBoxSearchFor, comboBoxSearchBy;

    @FXML
    private ComboBox<Integer> comboBoxReviewRating;

    @FXML
    private Button btnSearch, btnClearView, btnGetSongs,
            btnGetDirectors, btnGetArtists,
            btnGetMedia;
    @FXML
    private Button btnAddAlbum, btnAddMovie, btnAddSong, btnAddReview;

    @FXML
    private TextField textSearch, textAlbumTitle,
            textMovieTitle, textMovieDirector, textMovieGenre,
            textSongTitle, textSongGenre, textSongArtist;

    @FXML
    private TextArea textReview;

    @FXML
    private ListView<String> searchView;

    @FXML
    private ListView<Song> songView;

    @FXML
    private ListView<Director> directorView;

    @FXML
    private ListView<Artist> artistView;

    @FXML
    private ListView<Media> mediaView;

    @FXML
    private MenuItem menuLogin;

    @FXML
    private Tab addTab;

    private User client;


    public MediaLibraryController() throws ClassNotFoundException {
        try {
            sql = new SQLHandler();
        } catch (SQLException e) {
            showErrorMessage("Could not connect to database");
            System.exit(e.getErrorCode());
        }
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }

    private void search(String searchFor, String searchBy, String searchText) throws QueryException {
        if (searchFor == null || searchBy == null) {
            // Add error window
            showErrorMessage("Can not search for null");
            return;
        }
        Object obj = null;

        if (searchBy.equals("ID")) {
            int search = Integer.parseInt(searchText);

            if (searchFor.equals("Movie")) {
                obj = sql.getMovieById(search);
            }
            if (searchFor.equals("Album")) {
                obj = sql.getAlbumById(search);
            }
            if (searchFor.equals("Song")) {
                obj = sql.getSongById(search);
            }
            if (obj != null)
                searchView.getItems().addAll(obj.toString());
            else
                showErrorMessage("No match found");
        } else if (searchBy.equals("Title")) {
            ArrayList<Media> medias = new ArrayList<>();

            if (searchFor.equals("Movie")) {
                medias.addAll(sql.getMoviesByTitle(searchText));
            }
            if (searchFor.equals("Album")) {
                //obj = sql.getAlbumById(searchText);
            }
            if (searchFor.equals("Song")) {
                //obj = sql.getSongById(searchText);
            }
            if (medias.size() > 0)
                for(Media media: medias)
                    searchView.getItems().addAll(media.toString());
            else
                showErrorMessage("No match found");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnSearch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    search(comboBoxSearchFor.getValue(), comboBoxSearchBy.getValue(), textSearch.getText());
                } catch (QueryException e) {
                    showErrorMessage(e.getMessage());
                }
            }
        });
        btnClearView.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                searchView.getItems().clear();
            }
        });

        menuLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = new Stage();
                stage.initModality(Modality.WINDOW_MODAL);

                TextField txtUserName = new TextField();
                txtUserName.setPromptText("Username");

                Button btnLogin = new Button("Login");

                btnLogin.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            client = sql.getUserByName(txtUserName.getText());
                            if (client == null)
                                showErrorMessage("User not found");
                            else
                                addTab.setDisable(false);
                        } catch (QueryException e) {
                            showErrorMessage("Could not login");
                        } finally {
                            stage.close();
                        }
                    }
                });

                VBox vbox = new VBox(new Text("Login as user"), txtUserName, btnLogin);
                vbox.setPadding(new Insets(10, 10, 10, 10));
                stage.setScene(new Scene(vbox));
                stage.showAndWait();
            }
        });
        btnGetSongs.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    songView.getItems().clear();
                    songView.getItems().addAll(sql.getSongs());
                } catch (QueryException e) {
                    showErrorMessage(e.getMessage());
                }
            }
        });

        btnAddAlbum.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ArrayList<Song> songs = new ArrayList<>();
                songs.addAll(songView.getSelectionModel().getSelectedItems());
                try {
                    sql.addAlbum(textAlbumTitle.getText(), songs, client);
                } catch (QueryException e) {
                    showErrorMessage(e.getMessage());
                }
            }
        });

        btnGetDirectors.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                directorView.getItems().clear();
                try {
                    directorView.getItems().addAll(sql.getDirectors());
                } catch (QueryException e) {
                    showErrorMessage(e.getMessage());
                }
            }
        });

        btnAddMovie.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Director director;
                try {
                    if (textMovieDirector.getText() != null)
                        director = new Director(sql.addDirector(textMovieDirector.getText()), textMovieDirector.getText());
                    else if (directorView.getSelectionModel().getSelectedItems() != null)
                        director = directorView.getSelectionModel().getSelectedItem();
                    else {
                        showErrorMessage("Could not add director");
                        return;
                    }

                    sql.addMovie(textMovieTitle.getText(), textMovieGenre.getText(), director, client);
                } catch (QueryException e) {
                    showErrorMessage(e.getMessage());
                }
            }
        });

        btnGetArtists.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                artistView.getItems().clear();
                try {
                    artistView.getItems().addAll(sql.getArtists());
                } catch (QueryException e) {
                    showErrorMessage(e.getMessage());
                }
            }
        });

        btnAddSong.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if (textSongArtist.getText() != null)
                        sql.addSong(textSongTitle.getText(), textSongGenre.getText(), client, textSongArtist.getText());
                    else if (artistView.getSelectionModel().getSelectedItems() != null) {
                        Artist artist = artistView.getSelectionModel().getSelectedItem();
                        sql.addSong(textSongTitle.getText(), textSongGenre.getText(), client, artist);
                    } else {
                        showErrorMessage("Could not add song with artist");
                    }

                } catch (QueryException e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                    showErrorMessage(e.getMessage());
                }
            }
        });

        btnGetMedia.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mediaView.getItems().clear();
                try {
                    mediaView.getItems().addAll(sql.getAllMedia());
                } catch (QueryException e) {
                    showErrorMessage(e.getMessage());
                }
            }
        });

    }

}
