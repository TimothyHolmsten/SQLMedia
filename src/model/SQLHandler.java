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

    @Override
    public void addMovie(Movie movie, Director director, User user) {

    }

    @Override
    public void addAlbum(Album album, User user) {

    }

    @Override
    public void addArtistToSong(Artist artist, Song song, User user) throws QueryException {
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

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            throw new QueryException(e.getSQLState());
        } finally {
            turnOnAutoCommit();
        }
    }

    private void turnOnAutoCommit() {
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
    public void addSong(String title, String genre, User user) throws QueryException {
        String insertSong = "INSERT INTO Song(mediaId) VALUES(?);";
        try {
            connection.setAutoCommit(false);
            int mediaId = addMedia(title, genre, user);
            int songUpdated = 0;

            if (mediaId > 0) {
                PreparedStatement addSong = connection.prepareStatement(insertSong);
                addSong.setInt(1, mediaId);
                songUpdated = addSong.executeUpdate();
            } else
                connection.rollback();
            if (songUpdated > 0)
                connection.commit();
            else
                connection.rollback();

        } catch (SQLException e) {
            throw new QueryException(e.getSQLState());
        } finally {
            turnOnAutoCommit();
        }
    }

    @Override
    public int addMedia(String title, String genre, User user) throws QueryException {
        String insertMedia = "INSERT INTO Media(title, genre) VALUES(?, ?);";
        String insertAddedContent = "INSERT INTO AddedContent(mediaId, userId) VALUES(?, ?);";
        int mediaId;
        try {
            PreparedStatement addMedia = connection.prepareStatement(insertMedia);
            addMedia.setString(1, title);
            addMedia.setString(2, genre);
            addMedia.executeUpdate();
            mediaId = addMedia.getResultSet().getInt(1);
            PreparedStatement addAddedContent = connection.prepareStatement(insertAddedContent);
            addAddedContent.setInt(1, mediaId);
            addAddedContent.setInt(2, user.getUserId());
            addAddedContent.executeUpdate();
        } catch (SQLException e) {
            throw new QueryException(e.getSQLState());
        }
        return mediaId;
    }

    @Override
    public Artist getArtistById(int id) throws QueryException {
        Artist artist = null;
        try {
            String query = "SELECT * FROM view_GetArtistUserId WHERE artistId = " + id;
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next())
                artist = new Artist(rs.getInt("artistId"), rs.getString("artistName"), rs.getInt("userId"));
        } catch (SQLException e) {
            throw new QueryException(e.getSQLState());
        }
        return artist;
    }

    @Override
    public Song getSongById(int id) throws QueryException {
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
            throw new QueryException(e.getSQLState());
        }
        return song;
    }

    @Override
    public User getUserById(int id) throws QueryException {
        User user = null;
        try {
            String query = "SELECT * FROM User WHERE userId = " + id;
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next())
                user = new User(rs.getInt("userId"), rs.getString("userName"));
        } catch (SQLException e) {
            throw new QueryException(e.getSQLState());
        }
        return user;
    }
}
