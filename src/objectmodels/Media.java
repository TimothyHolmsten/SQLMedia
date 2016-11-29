package objectmodels;

/**
 * Created by timothy on 2016-11-28.
 */
public abstract class Media implements AddedContent {
    private int mediaId;
    private String title;
    private int addedByUserId; // userId of who added this media

    public Media(int mediaId, String title, int addedByUserId) {
        this.mediaId = mediaId;
        this.title = title;
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
}
