package model;

import objectmodels.*;

import java.util.ArrayList;

/**
 * Created by timothy on 2016-11-28.
 */
public interface Query {
    String DATABASE = "labb1_databas";
    String SERVER = "jdbc:mysql://localhost:3306/" + DATABASE;

    int addDirector(String directorName) throws QueryException;

    void addMovie(String title, String genre, Director director, User user) throws QueryException;

    void addAlbum(String title, ArrayList<Song> songs, User user) throws QueryException;

    void addSong(String title, String genre, User user, Artist artist) throws QueryException;

    void addSong(String title, String genre, User user, String artistName) throws QueryException;

    void addArtistToSong(Artist artist, Song song, User user) throws QueryException;

    int addMedia(String title, String genre, User user) throws QueryException;

    void addUser(String userName) throws QueryException;

    void addReview(String reviewText, int rating, User user, int mediaId) throws QueryException;

    void addReview(String reviewText, int rating, User user, Media media) throws QueryException;

    Movie getMovieById(int id) throws QueryException;

    Director getDirectorById(int id) throws QueryException;

    Artist getArtistById(int id) throws QueryException;

    Song getSongById(int id) throws QueryException;

    User getUserById(int id) throws QueryException;

    Album getAlbumById(int id) throws QueryException;

    ArrayList<Director> getDirectors() throws QueryException;

    ArrayList<Artist> getSongArtists(int songId) throws QueryException;

    ArrayList<Artist> getArtists() throws QueryException;

    ArrayList<Media> getAllMedia() throws QueryException;

    ArrayList<Song> getSongs() throws QueryException;

    ArrayList<Movie> getMovies() throws QueryException;

    ArrayList<Album> getAlbums() throws QueryException;

    ArrayList<Movie> getMoviesByTitle(String title) throws QueryException;

    ArrayList<Song> getSongsByTitle(String title) throws QueryException;

    ArrayList<Album> getAlbumsByTitle(String title) throws QueryException;

    ArrayList<Movie> getMoviesByGenre(String genre) throws QueryException;

    ArrayList<Song> getSongsByGenre(String genre) throws QueryException;

    ArrayList<Album> getAlbumsByGenre(String genre) throws QueryException;

    ArrayList<Movie> getMoviesByDirector(String directorName) throws QueryException;

    ArrayList<Song> getSongsByArtist(String artistName) throws QueryException;

    ArrayList<Album> getAlbumsByArtist(String artistName) throws QueryException;

    ArrayList<Song> getSongsByRating(int rating) throws QueryException;

    ArrayList<Movie> getMoviesByRating(int rating) throws QueryException;

    User getUserByName(String userName) throws QueryException;

    ArrayList<Album> getAlbumsByRating(int rating) throws QueryException;


}
