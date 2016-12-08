package objectmodels;

import java.util.ArrayList;

/**
 * Created by timothy on 2016-11-28.
 */
public class Album extends Media {
    // Album(mediaId,title,userId);
    private ArrayList<Song> songs;
    private int albumId;

    /**
     * Represents an album entity of the database
     *
     * @param albumId id of the album
     * @param mediaId id of the media it belongs to
     * @param title   title of the album
     * @param userId  user's id of who added the album
     * @param genre   genre of the album
     */
    public Album(int albumId, int mediaId, String title, int userId, String genre) {
        super(mediaId, title, userId, genre);
        this.albumId = albumId;
        songs = new ArrayList<>();
    }

    /**
     * Represents an album entity of the database
     *
     * @param albumId id of the album
     * @param mediaId id of the media it belongs to
     * @param title   title of the album
     * @param userId  user's id of who added the album
     * @param genre   genre of the album
     * @param songs   the songs that are in the album
     */
    public Album(int albumId, int mediaId, String title, int userId, String genre, ArrayList<Song> songs) {
        super(mediaId, title, userId, genre);
        this.songs = songs;
        this.albumId = albumId;
    }

    public Album(String title, String genre, User user) {
        super(title, genre, user);
    }

    /**
     * Returns the songs of the album
     *
     * @return song of the album
     */
    public ArrayList<Song> getSongs() {
        return (ArrayList<Song>) songs.clone();
    }

    /**
     * Returns the album's id
     *
     * @return albums's id
     */
    public int getAlbumId() {
        return albumId;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Title: %s, Genre: %s, Added By: %d, Songs: %s",
                albumId, getTitle(), getGenre(), getAddedByUserId(), songs.toString());
    }


}
