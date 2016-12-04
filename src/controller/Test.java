package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.SQLHandler;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Test implements Initializable {

    private SQLHandler sql;

    @FXML
    private ComboBox<String> comboBoxSearchFor, comboBoxSearchBy;

    @FXML
    private Button btnSearch;

    @FXML
    private TextField textSearch;

    @FXML
    private ListView<String> searchView;


    public Test() throws SQLException, ClassNotFoundException {
        sql = new SQLHandler();

    }

    private void search() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnSearch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                search();
            }
        });
    }

}
