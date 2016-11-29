package objectmodels;

/**
 * Created by timothy on 2016-11-28.
 */
public class Artist extends Person {
    private int artistId;

    public Artist(int artistId, String artistName) {
        super(artistName);
        this.artistId = artistId;
    }

    public int getArtistId() {
        return artistId;
    }
}
