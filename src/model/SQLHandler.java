package model;

import objectmodels.*;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by timothy on 2016-11-28.
 */
public class SQLHandler implements Query {
    private Connection connection;
    private ErrorHandler errorHandler;

    public SQLHandler() throws SQLException, ClassNotFoundException {
        errorHandler = new ErrorHandler();
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(SERVER, "root", "1234");
    }


    @Override
    public int addDirector(String directorName) throws QueryException {
        String insertDirector = "INSERT INTO Director(directorName) VALUES(?);";
        int directorId;
        try {
            PreparedStatement addDirector = connection.prepareStatement(insertDirector);
            addDirector.setString(1, directorName);
            if (addDirector.executeUpdate() < 1)
                throw new QueryException("Could not add director");
            directorId = lastInsertId("directorId");
        } catch (SQLException e) {
            throw new QueryException(e.getMessage());
        }
        return directorId;
    }

    @Override
    public void addMovie(String title, String genre, Director director, User user) throws QueryException {
        String insertMovie = "INSERT INTO Movie(mediaId, directorId) VALUES(?, ?);";
        try {
            connection.setAutoCommit(false);
            int mediaId = addMedia(title, genre, user);
            if (mediaId > 0) {
                PreparedStatement addMovie = connection.prepareStatement(insertMovie);
                addMovie.setInt(1, mediaId);
                addMovie.setInt(2, director.getDirectorId());
                if (addMovie.executeUpdate() < 1) {
                    connection.rollback();
                    throw new QueryException("Could not add movie");
                }
                connection.commit();
            } else {
                connection.rollback();
                throw new QueryException("Could not add media");
            }

        } catch (SQLException e) {
            throw new QueryException(e.getMessage());
        } finally {
            turnAutoCommitOn();
        }

    }


    @Override
    public void addAlbum(String title, ArrayList<Song> songs, User user) throws QueryException {
        String insertAlbum = "INSERT INTO Album(mediaId) VALUES(?);";
        String insertAlbumSong = "INSERT INTO AlbumSong(albumId, songId) VALUES(?, ?);";
        if (songs.size() < 1)
            throw new QueryException("An album must have songs");

        try {
            connection.setAutoCommit(false);
            // Fix better genre
            int mediaId = addMedia(title, songs.get(0).getGenre(), user);
            int updatedAlbum;
            int albumId;
            if (mediaId > 0) {
                PreparedStatement addAlbum = connection.prepareStatement(insertAlbum);
                addAlbum.setInt(1, mediaId);
                updatedAlbum = addAlbum.executeUpdate();
                albumId = lastInsertId("albumId");
            } else {
                connection.rollback();
                throw new QueryException("Could not add media");
            }
            if (updatedAlbum > 0) {
                PreparedStatement addAlbumSong = connection.prepareStatement(insertAlbumSong);

                for (Song song : songs) {
                    addAlbumSong.setInt(1, albumId);
                    addAlbumSong.setInt(2, song.getSongId());
                    if (addAlbumSong.executeUpdate() < 1) {
                        connection.rollback();
                        throw new QueryException("Could not add song to album");
                    }
                }
                connection.commit();
            } else {
                connection.rollback();
                throw new QueryException("Could not add album");
            }

        } catch (SQLException e) {
            throw new QueryException(e.getMessage());
        } finally {
            turnAutoCommitOn();
        }
    }

    @Override
    public void addArtistToSong(Artist artist, Song song, User user) throws QueryException {
        String insertArtist = "INSERT INTO Artist(artistName) VALUES(?);";
        String insertSongArtist = "INSERT INTO SongArtist(songId, artistId, userId) VALUES(?, ?, ?);";
        try {
            connection.setAutoCommit(false);
            PreparedStatement addArtist = connection.prepareStatement(insertArtist);
            if (getArtistById(artist.getArtistId()) == null) {
                addArtist.setString(1, artist.getName());
                addArtist.executeUpdate();
            }
            PreparedStatement addArtistToSong = connection.prepareStatement(insertSongArtist);
            addArtistToSong.setInt(1, song.getSongId());
            addArtistToSong.setInt(2, artist.getArtistId());
            addArtistToSong.setInt(3, user.getUserId());
            addArtistToSong.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            throw new QueryException(e.getMessage());
        }
    }

    private void turnAutoCommitOn() {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                connection.close();
            } catch (SQLException e1) {
                System.exit(e1.getErrorCode());
            }
        }
    }

    @Override
    public void addSong(String title, String genre, User user, Artist artist) throws QueryException {
        String insertSong = "INSERT INTO Song(mediaId) VALUES(?);";
        try {
            connection.setAutoCommit(false);
            int mediaId = addMedia(title, genre, user);
            int songUpdated;
            int songId;

            if (mediaId > 0) {
                PreparedStatement addSong = connection.prepareStatement(insertSong);
                addSong.setInt(1, mediaId);
                songUpdated = addSong.executeUpdate();
            } else {
                connection.rollback();
                throw new QueryException("Could not add media");
            }
            if (songUpdated > 0) {
                songId = lastInsertId("songId");
                addArtistToSong(artist, getSongById(songId), user);
                connection.commit();
            } else {
                connection.rollback();
                throw new QueryException("Could not add song");
            }
        } catch (SQLException e) {
            throw new QueryException(e.getMessage());
        } finally {
            turnAutoCommitOn();
        }
    }

    @Override
    public void addSong(String title, String genre, User user, String artistName) throws QueryException {
        String insertSong = "INSERT INTO Song(mediaId) VALUES(?);";
        String insertArtist = "INSERT INTO Artist(artistName) VALUES(?);";
        try {
            connection.setAutoCommit(false);
            int mediaId = addMedia(title, genre, user);
            int songUpdated;
            int songId;

            if (mediaId > 0) {
                PreparedStatement addSong = connection.prepareStatement(insertSong);
                addSong.setInt(1, mediaId);
                songUpdated = addSong.executeUpdate();
            } else {
                connection.rollback();
                throw new QueryException("Could not add media");
            }
            if (songUpdated > 0) {
                songId = lastInsertId("songId");
                PreparedStatement addArtist = connection.prepareStatement(insertArtist);
                addArtist.setString(1, artistName);
                int artistUpdated = addArtist.executeUpdate();
                if (artistUpdated > 0) {
                    addArtistToSong(getArtistById(lastInsertId("artistId")), getSongById(songId), user);
                    connection.commit();
                } else {
                    connection.rollback();
                    throw new QueryException("Could not add artist");
                }
            } else {
                connection.rollback();
                throw new QueryException("Could not add song");
            }
        } catch (SQLException e) {
            throw new QueryException(e.getMessage());
        } finally {
            turnAutoCommitOn();
        }
    }

    @Override
    public int addMedia(String title, String genre, User user) throws QueryException {
        String insertMedia = "INSERT INTO Media(title, genre) VALUES(?, ?);";
        String insertAddedContent = "INSERT INTO AddedContent(mediaId, userId) VALUES(?, ?);";
        int mediaId;
        ResultSet rs = null;
        try {
            PreparedStatement addMedia = connection.prepareStatement(insertMedia);
            addMedia.setString(1, title);
            addMedia.setString(2, genre);
            addMedia.executeUpdate();
            mediaId = lastInsertId("mediaId");
            if (mediaId > 0) {
                PreparedStatement addAddedContent = connection.prepareStatement(insertAddedContent);
                addAddedContent.setInt(1, mediaId);
                addAddedContent.setInt(2, user.getUserId());
                addAddedContent.executeUpdate();
            }
        } catch (SQLException e) {
            throw new QueryException(e.getMessage());
        }
        return mediaId;
    }

    @Override
    public void addUser(String userName) throws QueryException {
        String insertUser = "INSERT INTO User(userName) VALUES(?);";
        try {
            PreparedStatement addUser = connection.prepareStatement(insertUser);
            addUser.setString(1, userName);
            if (addUser.executeUpdate() < 1) {
                throw new QueryException("Could add user");
            }
        } catch (SQLException e) {
            throw new QueryException(e.getMessage());
        }
    }

    @Override
    public void addReview(String reviewText, int rating, User user, int mediaId) throws QueryException {
        String insertReview = "INSERT INTO Review(reviewText,rating,mediaId,userId) VALUES(?,?,?,?)";
        try {
            connection.setAutoCommit(false);
            PreparedStatement addReview = connection.prepareStatement(insertReview);
            addReview.setString(1, reviewText);
            addReview.setInt(2, rating);
            addReview.setInt(4, user.getUserId());
            addReview.setInt(3, mediaId);
            if (addReview.executeUpdate() < 1) {
                connection.rollback();

                throw new QueryException("could add review");
            }
            connection.commit();

        } catch (SQLException e) {

            throw new QueryException(e.getMessage());
        } finally {
            turnAutoCommitOn();
        }
    }

    @Override
    public Movie getMovieById(int id) throws QueryException {
        Movie movie = null;
        ResultSet rs = null;

        return movie;
    }

    @Override
    public Director getDirectorById(int id) throws QueryException {
        Director director = null;
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM Director WHERE Director.directorId= " + id;
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                director = new Director(rs.getInt("directorId"), rs.getString("directorName"));
            }
        } catch (SQLException e) {
            throw new QueryException(e.getMessage());
        } finally {
            if (rs != null) {
                closeResultSet(rs);
            }
        }
        return director;
    }

    @Override
    public ArrayList<Director> getDirectors() throws QueryException {
        ArrayList<Director> directors = new ArrayList<>();
        ResultSet rs = null;
        String query = "SELECT * FROM Director";
        try {
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next())
                directors.add(new Director(rs.getInt("directorId"), rs.getString("directorName")));
        } catch (SQLException e) {
            throw new QueryException(e.getMessage());
        } finally {
            if (rs != null)
                closeResultSet(rs);
        }
        return directors;
    }

    @Override
    public Artist getArtistById(int id) throws QueryException {
        Artist artist = null;
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM view_GetArtistUserId WHERE artistId = " + id;
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next())
                artist = new Artist(rs.getInt("artistId"), rs.getString("artistName"), rs.getInt("userId"));
        } catch (SQLException e) {
            throw new QueryException(e.getMessage());
        } finally {
            if (rs != null)
                closeResultSet(rs);
        }
        return artist;
    }

    @Override
    public ArrayList<Artist> getSongArtists(int songId) throws QueryException {
        ResultSet rs = null;
        ArrayList<Artist> artistsList = new ArrayList<>();

        try {
            String artistQuery = "SELECT * FROM view_GetSongArtistUserId WHERE songId =" + songId;
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(artistQuery);
            while (rs.next())
                artistsList.add(new Artist(rs.getInt("artistId"), rs.getString("artistName"), rs.getInt("userId")));
        } catch (SQLException e) {
            throw new QueryException(e.getMessage());
        } finally {
            if (rs != null)
                closeResultSet(rs);
        }
        return artistsList;
    }

    @Override
    public Song getSongById(int id) throws QueryException {
        Song song = null;
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM view_GetSongMediaUserId WHERE songId =" + id;
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(query);

            while (rs.next())
                song = new Song(
                        rs.getInt("songId"),
                        rs.getInt("mediaId"),
                        rs.getString("title"),
                        rs.getInt("userId"),
                        rs.getString("genre"),
                        getSongArtists(id));
        } catch (SQLException e) {
            throw new QueryException(e.getMessage());
        } finally {
            if (rs != null)
                closeResultSet(rs);
        }
        return song;
    }

    @Override
    public User getUserById(int id) throws QueryException {
        User user = null;
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM User WHERE userId = " + id;
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next())
                user = new User(rs.getInt("userId"), rs.getString("userName"));
        } catch (SQLException e) {
            throw new QueryException(e.getMessage());
        } finally {
            if (rs != null)
                closeResultSet(rs);
        }
        return user;
    }

    @Override
    public Album getAlbumById(int id) throws QueryException {

        Album album = null;
        ResultSet rs = null;
        ArrayList<Song> songs = new ArrayList<>();

        try {

            String querySongs = "SELECT * FROM AlbumSong WHERE albumId=" + id;
            Statement StatementSongs = connection.createStatement();
            StatementSongs.execute(querySongs);
            rs = StatementSongs.executeQuery(querySongs);
            while (rs.next()) {
                songs.add(getSongById(rs.getInt("songId")));
            }
            rs = null;
            String queryAlbum = "SELECT * FROM view_GetAlbumMediaUser where albumId=" + id;
            Statement StatementAlbum = connection.createStatement();
            StatementAlbum.execute(queryAlbum);
            rs = StatementAlbum.executeQuery(queryAlbum);
            while (rs.next())
                album = new Album(
                        rs.getInt("albumId"),
                        rs.getInt("mediaId"),
                        rs.getString("title"),
                        rs.getInt("userId"),
                        rs.getString("genre"),
                        songs
                );
        } catch (SQLException e) {
            throw new QueryException(e.getMessage());
        } finally {
            closeResultSet(rs);
        }
        return album;
    }

    @Override
    public User getUserByName(String userName) throws QueryException {
        User user = null;
        ResultSet rs = null;

        try {
            String query = String.format("SELECT * FROM User WHERE userName = '%s'", userName);
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next())
                user = new User(rs.getInt("userId"), rs.getString("userName"));
        } catch (SQLException e) {
            throw new QueryException(e.getMessage());
        } finally {
            if (rs != null)
                closeResultSet(rs);
        }
        return user;
    }

    @Override
    public ArrayList<Artist> getArtists() throws QueryException {
        ArrayList<Artist> artists = new ArrayList<>();
        ResultSet rs = null;

        String query = "SELECT Artist.*, SongArtist.userId FROM Artist, SongArtist WHERE Artist.artistId = SongArtist.artistId";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next())
                artists.add(new Artist(rs.getInt("artistId"),
                        rs.getString("artistName"),
                        rs.getInt("userId")));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null)
                closeResultSet(rs);
        }
        return artists;
    }

    @Override
    public ArrayList<Media> getAllMedia() throws QueryException {
        ArrayList<Media> media = new ArrayList<>();
        media.addAll(getSongs());
        media.addAll(getMovies());
        media.addAll(getAlbums());

        return media;
    }

    @Override
    public ArrayList<Movie> getMoviesByTitle(String title) throws QueryException {
        ArrayList<Movie> movies = new ArrayList<>();
        ResultSet rs = null;
        String query = String.format("SELECT * FROM view_GetMovieDirectorMediaUserId WHERE title = '%s'", title);

        try {
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                movies.add(new Movie(
                        rs.getInt("movieId"),
                        rs.getInt("mediaId"),
                        rs.getString("title"),
                        rs.getInt("directorId"),
                        rs.getInt("userId"),
                        rs.getString("genre")

                ));
            }
        } catch (SQLException e) {
            throw new QueryException(e.getMessage());
        } finally {
            if (rs != null)
                closeResultSet(rs);
        }
        return movies;
    }

    @Override
    public ArrayList<Song> getSongsByTitle(String title) throws QueryException {
        ArrayList<Song> songs = new ArrayList<>();
        ResultSet rs = null;
        String query = String.format("SELECT * FROM view_GetSongMediaUserIdArtist WHERE title ='%s'", title);
        try {
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                songs.add(new Song(rs.getInt("songId"),
                        rs.getInt("mediaId"),
                        rs.getString("title"),
                        rs.getInt("userId"),
                        rs.getString("genre"),
                        getSongArtists(rs.getInt("songId"))));
            }
        } catch (SQLException e) {
            throw new QueryException(e.getMessage());
        } finally {
            {
                if (rs != null) {
                    closeResultSet(rs);
                }
            }
        }
        return songs;
    }

    @Override
    public ArrayList<Album> getAlbumsByTitle(String title) throws QueryException {
        ArrayList<Album> albums = new ArrayList<>();
        ResultSet rs = null;
        String query = String.format("SELECT * FROM view_GetAlbumMediaUser WHERE title= '%s'", title);
        try {
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next())
                albums.add(new Album(
                        rs.getInt("albumId"),
                        rs.getInt("mediaId"),
                        rs.getString("title"),
                        rs.getInt("userId"),
                        rs.getString("genre")
                ));
        } catch (SQLException e) {
            throw new QueryException(e.getMessage());
        } finally {
            if (rs != null) {
                closeResultSet(rs);
            }
        }
        return albums;
    }

    @Override
    public ArrayList<Movie> getMoviesByGenre(String genre) throws QueryException {
        ArrayList<Movie> movies = new ArrayList<>();
        ResultSet rs = null;
        String query = String.format("SELECT * FROM view_GetMovieDirectorMediaUserId WHERE genre = '%s'", genre);
        try {
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next())
                movies.add(new Movie(
                        rs.getInt("movieId"),
                        rs.getInt("mediaId"),
                        rs.getString("title"),
                        rs.getInt("directorId"),
                        rs.getInt("userId"),
                        rs.getString("genre")
                ));
        } catch (SQLException e) {
            throw new QueryException(e.getMessage());
        } finally {
            if (rs != null) {
                closeResultSet(rs);
            }
        }
        return movies;
    }

    @Override
    public ArrayList<Song> getSongsByGenre(String genre) throws QueryException {
        ArrayList<Song> songs = new ArrayList<>();
        ResultSet rs = null;
        String query = String.format("SELECT * FROM view_GetSongMediaUserIdArtist WHERE '%s'", genre);
        try {
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next())
                songs.add(new Song(
                        rs.getInt("songId"),
                        rs.getInt("mediaId"),
                        rs.getString("title"),
                        rs.getInt("userId"),
                        rs.getString("genre")
                ));
        } catch (SQLException e) {
            throw new QueryException(e.getMessage());
        } finally {
            if (rs != null)
                closeResultSet(rs);
        }
        return songs;
    }

    @Override
    public ArrayList<Album> getAlbumsByGenre(String genre) throws QueryException {
        ArrayList<Album> albums = new ArrayList<>();
        ResultSet rs = null;
        String query = String.format("SELECT * FROM view_GetAlbumMediaUser WHERE genre = '%s'", genre);
        try {
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next())
                albums.add(new Album(
                        rs.getInt("albumId"),
                        rs.getInt("mediaId"),
                        rs.getString("title"),
                        rs.getInt("userId"),
                        rs.getString("genre")
                ));
        } catch (SQLException e) {
            throw new QueryException(e.getMessage());
        } finally {
            if (rs != null)
                closeResultSet(rs);
        }
        return albums;
    }

    @Override
    public ArrayList<Movie> getMoviesByDirector(String directorName) throws QueryException {
        ArrayList<Movie> movies = new ArrayList<>();
        ResultSet rs = null;
        String query = String.format("SELECT * FROM view_GetMovieDirectorMediaUserId WHERE directorName = '%s'", directorName);
        try {
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next())
                movies.add(new Movie(
                        rs.getInt("movieId"),
                        rs.getInt("mediaId"),
                        rs.getString("title"),
                        rs.getInt("directorId"),
                        rs.getInt("userId"),
                        rs.getString("genre")
                ));
        } catch (SQLException e) {
            throw new QueryException(e.getMessage());
        } finally {
            if (rs != null)
                closeResultSet(rs);
        }
        return movies;
    }

    @Override
    public ArrayList<Song> getSongsByArtist(String artistName) throws QueryException {
        ArrayList<Song> songs = new ArrayList<>();
        ResultSet rs = null;
        String query = String.format("SELECT * FROM view_GetSongMediaUserIdArtist WHERE artistName = '%s'");
        try {
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next())
                songs.add(new Song(
                        rs.getInt("songId"),
                        rs.getInt("mediaId"),
                        rs.getString("title"),
                        rs.getInt("userId"),
                        rs.getString("genre")
                ));
        } catch (SQLException e) {
            throw new QueryException(e.getMessage());
        } finally {
            if (rs != null)
                closeResultSet(rs);
        }
        return songs;
    }

    @Override
    public ArrayList<Album> getAlbumsByArtist(String artistName) {
        ArrayList<Album> songs = new ArrayList<>();
        ResultSet rs = null;
        try{
            String query = String.format("SELECT * FROM view_GetSongMediaUserIdArtist WHERE artistName='%s'",artistName);
            Statement statement = connection.createStatement();
            rs= statement.executeQuery(query);
            while (rs.next()) {

                songs.add(new Song(rs.getInt("songId"),
                        rs.getInt("mediaId"),
                        rs.getString("title"),
                        rs.getInt("userId"),
                        rs.getString("genre")));


            }

        }catch (SQLException e){
            throw  new QueryException(e.getMessage();
        }finally {
            if(rs!= null){
                closeResultSet(rs);
            }
        }

        return songs;
    }

    @Override
    public ArrayList<Album> getAlbums() throws QueryException {
        ArrayList<Album> albums = new ArrayList<>();
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM view_GetAlbumMediaUser";
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                albums.add(new Album(rs.getInt("albumId"),
                        rs.getInt("mediaId"),
                        rs.getString("title"),
                        rs.getInt("userId"),
                        rs.getString("genre")));
            }
        } catch (SQLException e) {
            throw new QueryException(e.getMessage());

        } finally {
            if (rs != null) {
                closeResultSet(rs);
            }
        }
        return albums;
    }

    @Override
    public ArrayList<Movie> getMovies() throws QueryException {
        ArrayList<Movie> movies = new ArrayList<>();
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM view_GetMovieDirectorMediaUserId";
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(query);

            while (rs.next()) {


                movies.add(new Movie(rs.getInt("movieId"),
                        rs.getInt("mediaId"),
                        rs.getString("title"),
                        rs.getInt("directorId"),
                        rs.getInt("userId"),
                        rs.getString("genre"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null)
                closeResultSet(rs);

        }

        return movies;
    }

    @Override
    public ArrayList<Song> getSongs() throws QueryException {
        ArrayList<Song> songs = new ArrayList<>();
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM view_GetSongMediaUserId";
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next())
                songs.add(new Song(
                        rs.getInt("songId"),
                        rs.getInt("mediaId"),
                        rs.getString("title"),
                        rs.getInt("userId"),
                        rs.getString("genre"),
                        getSongArtists(rs.getInt("songId"))));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null)
                closeResultSet(rs);
        }
        return songs;
    }

    private void closeResultSet(ResultSet rs) {
        try {
            rs.close();
        } catch (SQLException e) {
            System.exit(e.getErrorCode());
        }
    }

    private int lastInsertId(String id) {
        Statement statement = null;
        ResultSet rs = null;
        int lastInsert = 0;
        try {
            statement = connection.createStatement();
            String query = "SELECT LAST_INSERT_ID() AS " + id;
            rs = statement.executeQuery(query);
            while (rs.next())
                lastInsert = rs.getInt(id);
            closeResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastInsert;
    }

}
