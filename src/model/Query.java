package model;

import objectmodels.*;

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
    void addReview(String reviewText, int rate , User user) throws QueryException;

    Movie getMovieById(int id) throws QueryException;
    Director getDirectorById(int id) throws QueryException;
    Artist getArtistById(int id) throws QueryException;
    ArrayList<Artist> getSongArtists(int songId) throws QueryException;
    Song getSongById(int id) throws QueryException;
    User getUserById(int id) throws QueryException;
    Album getAlbumById(int id) throws QueryException;
    ArrayList<Song> getSongs() throws QueryException;
    User getUserByName(String userName) throws QueryException;

}
