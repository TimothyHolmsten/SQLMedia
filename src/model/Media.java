package model;

/**
 * Created by timothy on 2016-11-28.
 */
public abstract class Media {
    private int mediaId;
    private String title;

    public Media(int mediaId, String title) {
        this.mediaId = mediaId;
        this.title = title;
    }

    public int getMediaId() {
        return mediaId;
    }

    public String getTitle() {
        return title;
    }
}
