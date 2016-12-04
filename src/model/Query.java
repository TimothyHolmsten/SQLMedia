package model;

import objectmodels.*;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by timothy on 2016-11-28.
 */
public interface Query {
    String DATABASE = "labb1_databas";
    String SERVER = "jdbc:mysql://localhost:3306/" + DATABASE;

    void addMovie(Movie movie, Director director, User user);
    void addAlbum(String title, ArrayList<Song> songs, User user) throws QueryException;
    void addSong(String title, String genre, User user) throws QueryException;
    void addArtistToSong(Artist artist, Song song, User user) throws QueryException;
    int addMedia(String title, String genre, User user) throws QueryException;

    Artist getArtistById(int id) throws QueryException;
    Song getSongById(int id) throws QueryException;
    User getUserById(int id) throws QueryException;
}
