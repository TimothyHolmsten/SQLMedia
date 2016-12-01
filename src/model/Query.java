package model;

import objectmodels.*;

import java.sql.SQLException;

/**
 * Created by timothy on 2016-11-28.
 */
public interface Query {
    String DATABASE = "labb1_databas";
    String SERVER = "jdbc:mysql://localhost:3306/" + DATABASE;


    void addMovie(Movie movie, Director director, User user);
    void addAlbum(Album album, User user);
    void addSong(Song song, User user);
    void addArtist(Artist artist);
    void addArtistToSong(Artist artist, Song song, User user);

    Artist getArtistById(int id);
    Song getSongById(int id);
    User getUserById(int id);
}
