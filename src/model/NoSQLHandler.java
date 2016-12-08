package model;

import objectmodels.*;

import java.util.ArrayList;

/**
 * Created by timothy on 2016-11-29.
 */
public class NoSQLHandler implements Query {
    @Override
    public int addDirector(String directorName) throws QueryException {
        return 0;
    }

    @Override
    public void addMovie(String title, String genre, Director director, User user) throws QueryException {

    }

    @Override
    public void addAlbum(String title, ArrayList<Song> songs, User user) throws QueryException {

    }

    @Override
    public void addSong(String title, String genre, User user, Artist artist) throws QueryException {

    }

    @Override
    public void addSong(String title, String genre, User user, String artistName) throws QueryException {

    }

    @Override
    public void addArtistToSong(Artist artist, Song song, User user) throws QueryException {

    }

    @Override
    public int addMedia(String title, String genre, User user) throws QueryException {
        return 0;
    }

    @Override
    public void addUser(String userName) throws QueryException {

    }

    @Override
    public void addReview(String reviewText, int rating, User user, int mediaId) throws QueryException {

    }

    @Override
    public Movie getMovieById(int id) throws QueryException {
        return null;
    }

    @Override
    public Director getDirectorById(int id) throws QueryException {
        return null;
    }

    @Override
    public Artist getArtistById(int id) throws QueryException {
        return null;
    }

    @Override
    public Song getSongById(int id) throws QueryException {
        return null;
    }

    @Override
    public User getUserById(int id) throws QueryException {
        return null;
    }

    @Override
    public Album getAlbumById(int id) throws QueryException {
        return null;
    }

    @Override
    public ArrayList<Director> getDirectors() throws QueryException {
        return null;
    }

    @Override
    public ArrayList<Artist> getSongArtists(int songId) throws QueryException {
        return null;
    }

    @Override
    public ArrayList<Artist> getArtists() throws QueryException {
        return null;
    }

    @Override
    public ArrayList<Media> getAllMedia() throws QueryException {
        return null;
    }

    @Override
    public ArrayList<Song> getSongs() throws QueryException {
        return null;
    }

    @Override
    public ArrayList<Movie> getMovies() throws QueryException {
        return null;
    }

    @Override
    public ArrayList<Album> getAlbums() throws QueryException {
        return null;
    }

    @Override
    public ArrayList<Movie> getMoviesByTitle(String title) throws QueryException {
        return null;
    }

    @Override
    public ArrayList<Song> getSongsByTitle(String title) throws QueryException {
        return null;
    }

    @Override
    public ArrayList<Album> getAlbumsByTitle(String title) throws QueryException {
        return null;
    }

    @Override
    public ArrayList<Movie> getMoviesByGenre(String genre) throws QueryException {
        return null;
    }

    @Override
    public ArrayList<Song> getSongsByGenre(String genre) throws QueryException {
        return null;
    }

    @Override
    public ArrayList<Album> getAlbumsByGenre(String genre) throws QueryException {
        return null;
    }

    @Override
    public ArrayList<Movie> getMoviesByDirector(String directorName) throws QueryException {
        return null;
    }

    @Override
    public ArrayList<Song> getSongsByArtist(String artistName) throws QueryException {
        return null;
    }

    @Override
    public ArrayList<Album> getAlbumsByArtist(String artistName) throws QueryException {
        return null;
    }

    @Override
    public ArrayList<Song> getSongsByRating(int rating) throws QueryException {
        return null;
    }

    @Override
    public ArrayList<Movie> getMoviesByRating(int rating) throws QueryException {
        return null;
    }

    @Override
    public User getUserByName(String userName) throws QueryException {
        return null;
    }
}
