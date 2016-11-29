package model;

import java.util.ArrayList;

/**
 * Created by timothy on 2016-11-28.
 */
public class Song extends Media {

    private ArrayList<Artist> artists;

    public Song(int mediaId, String title) {
        super(mediaId, title);
        artists = new ArrayList<>();
    }

    public Song(int mediaId, String title, ArrayList<Artist> artists) {
        super(mediaId, title);
        this.artists = artists;
    }

    public ArrayList<Artist> getArtists() {
        return (ArrayList<Artist>) artists.clone();
    }

}
