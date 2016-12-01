package model;

import objectmodels.*;

import java.sql.*;

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

    public ResultSet executeQuery(String query) {
        ResultSet rs = null;
        Statement statement = null;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
        } catch (SQLException e) {
            errorHandler.setErrorMessage(e.getSQLState());
        } finally {
            if (rs != null || statement != null)
                try {
                    rs.close();
                    statement.close();
                } catch (SQLException e) {
                    errorHandler.setErrorMessage(e.getSQLState());
                }
        }
        return rs;
    }

    @Override
    public void addMovie(Movie movie, Director director, User user) {

    }

    @Override
    public void addAlbum(Album album, User user) {

    }

    @Override
    public void addSong(Song song, User user) {

    }

    @Override
    public void addArtist(Artist artist) {

    }

    @Override
    public void addArtistToSong(Artist artist, Song song, User user) {
        String insertArtist = "INSERT INTO Artist(artistName) VALUES(?);";
        String insertSongArtist = "INSERT INTO SongArtist VALUES(?, ?, ?);";
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
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            errorHandler.setErrorMessage(e.getSQLState());
        }
    }

    @Override
    public Artist getArtistById(int id) {
        Artist artist = null;
        try {
            String query = "SELECT * FROM view_GetArtistUserId WHERE artistId = " + id;
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next())
                artist = new Artist(rs.getInt("artistId"), rs.getString("artistName"), rs.getInt("userId"));
        } catch (SQLException e) {
            errorHandler.setErrorMessage(e.getSQLState());
        }
        return artist;
    }

    @Override
    public Song getSongById(int id) {
        Song song = null;
        try {
            String query = "SELECT * FROM view_GetSongMediaUserId WHERE songId =" + id;
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next())
                song = new Song(
                        rs.getInt("songId"),
                        rs.getInt("mediaId"),
                        rs.getString("title"),
                        rs.getInt("userId"),
                        rs.getString("genre"));
        } catch (SQLException e) {
            errorHandler.setErrorMessage(e.getSQLState());
        }
        return song;
    }

    @Override
    public User getUserById(int id) {
        User user = null;
        try {
            String query = "SELECT * FROM User WHERE userId = " + id;
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next())
                user = new User(rs.getInt("userId"), rs.getString("userName"));
        } catch (SQLException e) {
            errorHandler.setErrorMessage(e.getSQLState());
        }
        return user;
    }
}
