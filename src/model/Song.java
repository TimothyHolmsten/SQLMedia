package model;

import java.util.ArrayList;

/**
 * Created by timothy on 2016-11-28.
 */
public class Song extends Media {

    // song(songId, mediaId, title,userId)
    private int songId;

    private ArrayList<Artist> artists;

    public Song(int songId, int mediaId, String title, int userId) {
        super(mediaId, title, userId);
        this.songId = songId;
        artists = new ArrayList<>();
    }

    public Song(int songId, int mediaId, String title, int userId,ArrayList<Artist> artists) {
        super(mediaId, title, userId);
        this.artists = artists;
    }

    public ArrayList<Artist> getArtists() {
        return (ArrayList<Artist>) artists.clone();
    }

    public int getSongId() {
        return songId;
    }
}
