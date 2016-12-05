package model;

import objectmodels.Artist;
import objectmodels.Director;
import objectmodels.Song;
import objectmodels.User;

import java.util.ArrayList;

/**
 * Created by timothy on 2016-11-28.
 */
public interface Query {
    String DATABASE = "labb1_databas";
    String SERVER = "jdbc:mysql://localhost:3306/" + DATABASE;

    void addDirector(String directorName) throws QueryException;
    void addMovie(String title, String genre, Director director, User user) throws QueryException;
    void addAlbum(String title, ArrayList<Song> songs, User user) throws QueryException;
    void addSong(String title, String genre, User user) throws QueryException;
    void addArtistToSong(Artist artist, Song song, User user) throws QueryException;
    int addMedia(String title, String genre, User user) throws QueryException;
    void addUser(String userName) throws QueryException;

    boolean loginUser(String userName) throws QueryException;

    Artist getArtistById(int id) throws QueryException;
    Song getSongById(int id) throws QueryException;
    User getUserById(int id) throws QueryException;
    User getUserByName(String userName) throws QueryException;
}
