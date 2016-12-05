package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.QueryException;
import model.SQLHandler;
import objectmodels.Song;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Test implements Initializable {

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


    public Test() throws SQLException, ClassNotFoundException {
        sql = new SQLHandler();

    }

    private void search(String searchFor, String searchBy, String searchText) throws QueryException {
        if(searchFor == null || searchBy == null)
            // Add error window
            System.out.println("Cannot search");
        if(searchBy.equals("ID")) {
            if(searchFor.equals("Media")) {

            }
            if(searchFor.equals("Movie")) {

            }
            if(searchFor.equals("Album")) {

            }
            if(searchFor.equals("Song")) {
                Song song = sql.getSongById(Integer.parseInt(searchText));
                searchView.getItems().addAll(song.toString());
            }
            if(searchFor.equals("Artist")) {

            }
            if(searchFor.equals("Director")) {

            }
            if(searchFor.equals("User")) {

            }
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
                    e.printStackTrace();
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
                Stage dialogStage = new Stage();
                dialogStage.initModality(Modality.WINDOW_MODAL);

                VBox vbox = new VBox(new Text("Hi"), new Button("Ok."));
                vbox.setAlignment(Pos.CENTER);
                vbox.setPadding(new Insets(15));

                dialogStage.setScene(new Scene(vbox));
                dialogStage.show();
            }
        });
    }

}
