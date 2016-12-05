package objectmodels;

import java.util.ArrayList;

/**
 * Created by timothy on 2016-11-28.
 */
public class Song extends Media {

    // song(songId, mediaId, title,userId)
    private int songId;

    private ArrayList<Artist> artists;

    public Song(int songId, int mediaId, String title, int userId, String genre) {
        super(mediaId, title, userId, genre);
        this.songId = songId;
        artists = new ArrayList<>();
    }

    public Song(int songId, int mediaId, String title, int userId, String genre, ArrayList<Artist> artists) {
        super(mediaId, title, userId, genre);
        this.songId = songId;
        this.artists = artists;
    }

    public ArrayList<Artist> getArtists() {
        return (ArrayList<Artist>) artists.clone();
    }

    public int getSongId() {
        return songId;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Title: %s, Genre: %s, Added By: %d, Artists: %s \n",
                songId, getTitle(), getGenre(), getAddedByUserId(), artists.toString());
    }
}
