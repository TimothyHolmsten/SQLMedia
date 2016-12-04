package objectmodels;

/**
 * Created by timothy on 2016-11-28.
 */
public class Artist extends Person implements AddedContent {
    private int artistId;
    private int addedByUserId;

    public Artist(int artistId, String artistName, int addedByUserId) {
        super(artistName);
        this.artistId = artistId;
        this.addedByUserId = addedByUserId;
    }

    public int getArtistId() {
        return artistId;
    }

    @Override
    public int getAddedByUserId() {
        return addedByUserId;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Name: %s, Added By: %d",
                artistId, getName(), addedByUserId);
    }
}
