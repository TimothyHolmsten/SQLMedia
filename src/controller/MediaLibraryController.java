package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.QueryException;
import model.SQLHandler;
import objectmodels.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MediaLibraryController implements Initializable {

    private SQLHandler sql;

    @FXML
    private ComboBox<String> comboBoxSearchFor, comboBoxSearchBy;

    @FXML
    private Button btnSearch, btnClearView;

    @FXML
    private TextField textSearch;

    @FXML
    private ListView<String> searchView;

    @FXML
    private MenuItem menuLogin;

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

            if (searchFor.equals("Media")) {

            }
            if (searchFor.equals("Movie")) {

            }
            if (searchFor.equals("Album")) {

            }
            if (searchFor.equals("Song")) {
                obj = sql.getSongById(search);
            }
            if (searchFor.equals("Artist")) {
                obj = sql.getArtistById(search);
            }
            if (searchFor.equals("Director")) {

            }
            if (searchFor.equals("User")) {
                obj = sql.getUserById(search);
            }
            if (obj != null)
                searchView.getItems().addAll(obj.toString());
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
                        } catch (QueryException e) {
                            showErrorMessage("Could not login");
                        }
                        finally {
                            stage.close();
                        }
                    }
                });

                VBox vbox = new VBox(new Text("Login as user"), txtUserName, btnLogin);
                vbox.setPadding(new Insets(10,10,10,10));
                stage.setScene(new Scene(vbox));
                stage.showAndWait();
            }
        });
    }

}
