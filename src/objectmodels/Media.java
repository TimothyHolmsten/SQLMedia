package objectmodels;

/**
 * Created by timothy on 2016-11-28.
 */
public abstract class Media {
    private int mediaId;
    private String title;
    private int addedByUserId; // userId of who added this media

    public Media(int mediaId, String title, int addedBy) {
        this.mediaId = mediaId;
        this.title = title;
        this.addedByUserId = addedBy;
    }

    public int getMediaId() {
        return mediaId;
    }

    public String getTitle() {
        return title;
    }

    public int getAddedByUserId() {
        return addedByUserId;
    }
}
