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
import model.Handler;
import model.NoSQLHandler;
import model.QueryException;
import model.SQLHandler;
import objectmodels.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MediaLibraryController implements Initializable {

    private Handler handler;

    @FXML
    private ComboBox<String> comboBoxSearchFor, comboBoxSearchBy;

    @FXML
    private ComboBox<Integer> comboBoxReviewRating;

    @FXML
    private Button btnSearch, btnClearView, btnGetSongs,
            btnGetDirectors, btnGetArtists,
            btnGetMedia,
            btnUpdateArtists2, btnUpdateSongs2;
    @FXML
    private Button btnAddAlbum, btnAddMovie, btnAddSong, btnAddReview, btnAddArtistToSong;

    @FXML
    private TextField textSearch, textAlbumTitle,
            textMovieTitle, textMovieDirector, textMovieGenre,
            textSongTitle, textSongGenre, textSongArtist;

    @FXML
    private TextArea textReview;

    @FXML
    private ListView<String> searchView;

    @FXML
    private ListView<Song> songView, song2View;

    @FXML
    private ListView<Director> directorView;

    @FXML
    private ListView<Artist> artistView, artist2View;

    @FXML
    private ListView<Media> mediaView;

    @FXML
    private MenuItem menuLogin;

    @FXML
    private Tab addTab, reviewTab;

    private User client;


    public MediaLibraryController() throws ClassNotFoundException {
        handler = new NoSQLHandler();
    }

    public void closeConnection() {
        /*try {
            handler.closeConnection();
        } catch (QueryException e) {
            showErrorMessage(e.getMessage());
        }*/
    }

    public void updateUi(String message) {
        System.out.println(message);
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
                new Thread() {
                    public void run() {
                        try {
                            String msg = handler.getMovieById(search).getTitle();
                            javafx.application.Platform.runLater(
                                    new Runnable() {
                                        public void run() {
                                            updateUi(msg);
                                        }
                                    });
                        } catch (QueryException e) {
                            showErrorMessage(e.getMessage());
                        }
                    }
                }.start();
                obj = handler.getMovieById(search);
            }
            if (searchFor.equals("Album")) {
                new Thread() {
                    public void run() {
                        try {
                            String msg = handler.getAlbumById(search).getTitle();
                            javafx.application.Platform.runLater(
                                    new Runnable() {
                                        public void run() {
                                            updateUi(msg);
                                        }
                                    });
                        } catch (QueryException e) {
                            showErrorMessage(e.getMessage());
                        }
                    }
                }.start();
                obj = handler.getAlbumById(search);
            }
            if (searchFor.equals("Song")) {
                new Thread() {
                    public void run() {
                        try {
                            String msg = handler.getSongById(search).getTitle();
                            javafx.application.Platform.runLater(
                                    new Runnable() {
                                        public void run() {
                                            updateUi(msg);
                                        }
                                    });
                        } catch (QueryException e) {
                            showErrorMessage(e.getMessage());
                        }
                    }
                }.start();
                obj = handler.getSongById(search);
            }
            if (obj != null)
                searchView.getItems().addAll(obj.toString());
            else
                showErrorMessage("No match found");
        } else if (searchBy.equals("Title")) {
            ArrayList<Media> medias = new ArrayList<>();

            if (searchFor.equals("Movie")) {
                new Thread() {
                    public void run() {
                        try {
                            String msg = handler.getMoviesByTitle(searchText).get(0).getTitle();
                            javafx.application.Platform.runLater(
                                    new Runnable() {
                                        public void run() {
                                            updateUi(msg);
                                        }
                                    });
                        } catch (QueryException e) {
                            showErrorMessage(e.getMessage());
                        }
                    }
                }.start();
                medias.addAll(handler.getMoviesByTitle(searchText));
            }
            if (searchFor.equals("Album")) {
                new Thread() {
                    public void run() {
                        try {
                            String msg = handler.getAlbumsByTitle(searchText).get(0).getTitle();
                            javafx.application.Platform.runLater(
                                    new Runnable() {
                                        public void run() {
                                            updateUi(msg);
                                        }
                                    });
                        } catch (QueryException e) {
                            showErrorMessage(e.getMessage());
                        }
                    }
                }.start();
                medias.addAll(handler.getAlbumsByTitle(searchText));
            }
            if (searchFor.equals("Song")) {
                new Thread() {
                    public void run() {
                        try {
                            String msg = handler.getSongsByTitle(searchText).get(0).getTitle();
                            javafx.application.Platform.runLater(
                                    new Runnable() {
                                        public void run() {
                                            updateUi(msg);
                                        }
                                    });
                        } catch (QueryException e) {
                            showErrorMessage(e.getMessage());
                        }
                    }
                }.start();
                medias.addAll(handler.getSongsByTitle(searchText));
            }
            if (medias.size() > 0)
                for (Media media : medias)
                    searchView.getItems().addAll(media.toString());
            else
                showErrorMessage("No match found");
        } else if (searchBy.equals("Genre")) {
            ArrayList<Media> medias = new ArrayList<>();

            if (searchFor.equals("Movie")) {
                new Thread() {
                    public void run() {
                        try {
                            String msg = handler.getMoviesByGenre(searchText).get(0).getTitle();
                            javafx.application.Platform.runLater(
                                    new Runnable() {
                                        public void run() {
                                            updateUi(msg);
                                        }
                                    });
                        } catch (QueryException e) {
                            showErrorMessage(e.getMessage());
                        }
                    }
                }.start();
                medias.addAll(handler.getMoviesByGenre(searchText));
            }
            if (searchFor.equals("Album")) {
                new Thread() {
                    public void run() {
                        try {
                            String msg = handler.getAlbumsByGenre(searchText).get(0).getTitle();
                            javafx.application.Platform.runLater(
                                    new Runnable() {
                                        public void run() {
                                            updateUi(msg);
                                        }
                                    });
                        } catch (QueryException e) {
                            showErrorMessage(e.getMessage());
                        }
                    }
                }.start();
                medias.addAll(handler.getAlbumsByGenre(searchText));

            }
            if (searchFor.equals("Song")) {
                new Thread() {
                    public void run() {
                        try {
                            String msg = handler.getSongsByGenre(searchText).get(0).getTitle();
                            javafx.application.Platform.runLater(
                                    new Runnable() {
                                        public void run() {
                                            updateUi(msg);
                                        }
                                    });
                        } catch (QueryException e) {
                            showErrorMessage(e.getMessage());
                        }
                    }
                }.start();
                medias.addAll(handler.getSongsByGenre(searchText));

            }
            if (medias.size() > 0)
                for (Media media : medias)
                    searchView.getItems().addAll(media.toString());
            else
                showErrorMessage("No match found");

        } else if (searchBy.equals("Artist")) {
            ArrayList<Media> medias = new ArrayList<>();

            if (searchFor.equals("Album")) {
                new Thread() {
                    public void run() {
                        try {
                            String msg = handler.getAlbumsByArtist(searchText).get(0).getTitle();
                            javafx.application.Platform.runLater(
                                    new Runnable() {
                                        public void run() {
                                            updateUi(msg);
                                        }
                                    });
                        } catch (QueryException e) {
                            showErrorMessage(e.getMessage());
                        }
                    }
                }.start();
                medias.addAll(handler.getAlbumsByArtist(searchText));
            }
            if (searchFor.equals("Song")) {
                new Thread() {
                    public void run() {
                        try {
                            String msg = handler.getSongsByArtist(searchText).get(0).getTitle();
                            javafx.application.Platform.runLater(
                                    new Runnable() {
                                        public void run() {
                                            updateUi(msg);
                                        }
                                    });
                        } catch (QueryException e) {
                            showErrorMessage(e.getMessage());
                        }
                    }
                }.start();
                medias.addAll(handler.getSongsByArtist(searchText));

            }
            if (medias.size() > 0)
                for (Media media : medias)
                    searchView.getItems().addAll(media.toString());
            else
                showErrorMessage("No Match found");

        } else if (searchBy.equals("Director")) {
            ArrayList<Media> medias = new ArrayList<>();

            if (searchFor.equals("Movie")) {
                new Thread() {
                    public void run() {
                        try {
                            String msg = handler.getMoviesByDirector(searchText).get(0).getTitle();
                            javafx.application.Platform.runLater(
                                    new Runnable() {
                                        public void run() {
                                            updateUi(msg);
                                        }
                                    });
                        } catch (QueryException e) {
                            showErrorMessage(e.getMessage());
                        }
                    }
                }.start();
                medias.addAll(handler.getMoviesByDirector(searchText));
            }
            if (medias.size() > 0)
                for (Media media : medias)
                    searchView.getItems().addAll(media.toString());
            else
                showErrorMessage("No Match found");
        } else if (searchBy.equals("Rating")) {
            ArrayList<Media> medias = new ArrayList<>();

            if (searchFor.equals("Movie")) {
                new Thread() {
                    public void run() {
                        try {
                            String msg = handler.getMoviesByRating(Integer.parseInt(searchText)).get(0).getTitle();
                            javafx.application.Platform.runLater(
                                    new Runnable() {
                                        public void run() {
                                            updateUi(msg);
                                        }
                                    });
                        } catch (QueryException e) {
                            showErrorMessage(e.getMessage());
                        }
                    }
                }.start();
                medias.addAll(handler.getMoviesByRating(Integer.parseInt(searchText)));
            }
            if (searchFor.equals("Song")) {
                new Thread() {
                    public void run() {
                        try {
                            String msg = handler.getSongsByRating(Integer.parseInt(searchText)).get(0).getTitle();
                            javafx.application.Platform.runLater(
                                    new Runnable() {
                                        public void run() {
                                            updateUi(msg);
                                        }
                                    });
                        } catch (QueryException e) {
                            showErrorMessage(e.getMessage());
                        }
                    }
                }.start();
                medias.addAll(handler.getSongsByRating(Integer.parseInt(searchText)));
            }
            if (medias.size() > 0)
                for (Media media : medias)
                    searchView.getItems().addAll(media.toString());
            else
                showErrorMessage("No Match found");
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
                            client = handler.getUserByName(txtUserName.getText());
                            if (client == null)
                                showErrorMessage("User not found");
                            else {
                                addTab.setDisable(false);
                                reviewTab.setDisable(false);
                            }
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
                    songView.getItems().addAll(handler.getSongs());
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
                    handler.addAlbum(textAlbumTitle.getText(), songs, client);
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
                    directorView.getItems().addAll(handler.getDirectors());
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
                        director = new Director(handler.addDirector(textMovieDirector.getText()), textMovieDirector.getText());
                    else if (directorView.getSelectionModel().getSelectedItems() != null)
                        director = directorView.getSelectionModel().getSelectedItem();
                    else {
                        showErrorMessage("Could not add director");
                        return;
                    }

                    handler.addMovie(textMovieTitle.getText(), textMovieGenre.getText(), director, client);
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
                    artistView.getItems().addAll(handler.getArtists());
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
                        handler.addSong(textSongTitle.getText(), textSongGenre.getText(), client, textSongArtist.getText());
                    else if (artistView.getSelectionModel().getSelectedItems() != null) {
                        Artist artist = artistView.getSelectionModel().getSelectedItem();
                        handler.addSong(textSongTitle.getText(), textSongGenre.getText(), client, artist);
                    } else {
                        showErrorMessage("Could not add song with artist");
                    }

                } catch (QueryException e) {
                    showErrorMessage(e.getMessage());
                }
            }
        });

        btnGetMedia.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mediaView.getItems().clear();
                try {
                    mediaView.getItems().addAll(handler.getAllMedia());
                } catch (QueryException e) {
                    showErrorMessage(e.getMessage());
                }
            }
        });

        btnAddReview.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (comboBoxReviewRating.getValue() == null) {
                    showErrorMessage("Must have a rating");
                } else {
                    try {
                        if (handler instanceof SQLHandler)
                            handler.addReview(textReview.getText(), comboBoxReviewRating.getValue(), client, mediaView.getSelectionModel().getSelectedItem().getMediaId());
                        else if (handler instanceof NoSQLHandler)
                            handler.addReview(textReview.getText(), comboBoxReviewRating.getValue(), client, mediaView.getSelectionModel().getSelectedItem());
                    } catch (QueryException e) {
                        showErrorMessage(e.getMessage());
                    }
                }
            }
        });

        btnUpdateArtists2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                artist2View.getItems().clear();
                try {
                    artist2View.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                    artist2View.getItems().addAll(handler.getArtists());
                } catch (QueryException e) {
                    showErrorMessage(e.getMessage());
                }
            }
        });

        btnUpdateSongs2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                song2View.getItems().clear();
                try {
                    song2View.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                    song2View.getItems().addAll(handler.getSongs());
                } catch (QueryException e) {
                    showErrorMessage(e.getMessage());
                }
            }
        });

        btnAddArtistToSong.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (artist2View.getSelectionModel().getSelectedItems() == null || song2View.getSelectionModel().getSelectedItems() == null)
                    showErrorMessage("Must select both artist and song");
                else {
                    for (Artist artist : artist2View.getSelectionModel().getSelectedItems())
                        for (Song song : song2View.getSelectionModel().getSelectedItems())
                            try {
                                handler.addArtistToSong(artist, song, client);
                            } catch (QueryException e) {
                                showErrorMessage(e.getMessage());
                            }
                }
            }
        });
    }

}
