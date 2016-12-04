package objectmodels;

/**
 * Created by timothy on 2016-11-28.
 */
public abstract class Media implements AddedContent {
    private int mediaId;
    private String title;
    private int addedByUserId; // userId of who added this media
    private String genre;

    public Media(int mediaId, String title, int addedByUserId, String genre) {
        this.mediaId = mediaId;
        this.title = title;
        this.addedByUserId = addedByUserId;
        this.genre = genre;
    }

    public int getMediaId() {
        return mediaId;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int getAddedByUserId() {
        return addedByUserId;
    }

    public String getGenre() {
        return genre;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Title: %s, Genre: %s, Added By: %d",
                mediaId, getTitle(), getGenre(), getAddedByUserId());
    }
}
