package objectmodels;

import java.util.ArrayList;

/**
 * Created by timothy on 2016-11-28.
 */
public class Song extends Media {

    // song(songId, mediaId, title,userId)
    private int songId;

    private ArrayList<Artist> artists;

    /**
     * Represent a song entity of the database
     *
     * @param songId  id of the song
     * @param mediaId id of the media that the song belongs to
     * @param title   title of the song
     * @param userId  id of the user who added the song
     * @param genre   genre of the song
     */
    public Song(int songId, int mediaId, String title, int userId, String genre) {
        super(mediaId, title, userId, genre);
        this.songId = songId;
        artists = new ArrayList<>();
    }

    /**
     * Represent a song entity of the database
     *
     * @param songId  id of the song
     * @param mediaId id of the media that the song belongs to
     * @param title   title of the song
     * @param userId  id of the user who added the song
     * @param genre   genre of the song
     * @param artists artists that made the song
     */
    public Song(int songId, int mediaId, String title, int userId, String genre, ArrayList<Artist> artists) {
        super(mediaId, title, userId, genre);
        this.songId = songId;
        this.artists = artists;
    }

    /**
     * Returns a clone of the artists that made the song
     *
     * @return clone of artists
     */
    public ArrayList<Artist> getArtists() {
        return (ArrayList<Artist>) artists.clone();
    }

    /**
     * Returns the id of the song
     *
     * @return id of song
     */
    public int getSongId() {
        return songId;
    }

    @Override
    public String toString() {
        if (artists.size() > 0)
            return String.format("ID: %d, Title: %s, Genre: %s, Added By: %d, Artists: %s",
                    songId, getTitle(), getGenre(), getAddedByUserId(), artists.toString());
        else
            return String.format("ID: %d, Title: %s, Genre: %s, Added By: %d",
                    songId, getTitle(), getGenre(), getAddedByUserId());
    }
}
