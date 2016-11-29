package objectmodels;

/**
 * Created by timothy on 2016-11-28.
 */
public class Artist {
    private int artistId;
    private String artistName;

    public Artist(int artistId, String artistName ) {
        this.artistId= artistId;
        this.artistName = artistName;
    }

    public int getArtistId() {
        return artistId;
    }

    public String getArtistName() {
        return artistName;
    }
}
