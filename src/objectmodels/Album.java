package objectmodels;

import java.util.ArrayList;

/**
 * Created by timothy on 2016-11-28.
 */
public class Album extends Media {
    // Album(mediaId,title,userId);
    private ArrayList<Song> songs;
    private int albumId;

    public Album(int albumId, int mediaId, String title, int userId, String genre) {
        super(mediaId, title, userId, genre);
        this.albumId = albumId;
        songs = new ArrayList<>();
    }

    public Album(int mediaId, String title, int userId, String genre, ArrayList<Song> songs) {
        super(mediaId, title, userId, genre);
        this.songs = songs;
    }

    public ArrayList<Song> getSongs() {
        return (ArrayList<Song>) songs.clone();
    }

    public int getAlbumId() {
        return albumId;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Title: %s, Genre: %s, Added By: %d, Songs: %s /n",
                albumId, getTitle(), getGenre(), getAddedByUserId(), songs.toString());
    }
}
