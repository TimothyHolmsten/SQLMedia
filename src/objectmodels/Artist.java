package objectmodels;

/**
 * Created by timothy on 2016-11-28.
 */
public class Artist extends Person implements AddedContent {
    private int artistId;
    private int addedByUserId;

    /**
     * Represents an artist entity of the database
     *
     * @param artistId      id of the artist
     * @param artistName    name of the artist
     * @param addedByUserId id of the user that added the artist to the database
     */
    public Artist(int artistId, String artistName, int addedByUserId) {
        super(artistName);
        this.artistId = artistId;
        this.addedByUserId = addedByUserId;
    }

    /**
     * Returns the artist's id
     *
     * @return artists's id
     */
    public int getArtistId() {
        return artistId;
    }

    /**
     * Returns the id of the user who added the artist
     *
     * @return id of the user who added the artist
     */
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
