package objectmodels;

import java.util.ArrayList;

/**
 * Created by timothy on 2016-11-28.
 */
public class Album extends Media {
    // Album(mediaId,title,userId);
    private ArrayList<Song> songs;

    public Album(int mediaId, String title, int userId, String genre) {
        super(mediaId, title, userId, genre);
        songs = new ArrayList<>();
    }

    public Album(int mediaId, String title, int userId, String genre, ArrayList<Song> songs) {
        super(mediaId, title, userId, genre);
        this.songs = songs;
    }

    public ArrayList<Song> getSongs() {
        return (ArrayList<Song>) songs.clone();
    }
}
