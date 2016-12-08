package objectmodels;

/**
 * Created by timothy on 2016-11-28.
 */
public abstract class Media implements AddedContent {
    private int mediaId;
    private String title;
    private int addedByUserId; // userId of who added this media
    private String genre;
    private User addedByUser;

    /**
     * Represent a media entity of the database
     *
     * @param mediaId       id of the media
     * @param title         title of the media
     * @param addedByUserId user id of who added the media
     * @param genre         genre of the media
     */
    public Media(int mediaId, String title, int addedByUserId, String genre) {
        this.mediaId = mediaId;
        this.title = title;
        this.addedByUserId = addedByUserId;
        this.genre = genre;
    }

    public Media(String title, String genre, User user) {
        this.title = title;
        this.genre = genre;
        this.addedByUser = user;
    }

    /**
     * Returns id of the media
     *
     * @return id of the media
     */
    public int getMediaId() {
        return mediaId;
    }

    /**
     * Returns the title of the media
     *
     * @return title of the media
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns user id of who that added the media
     *
     * @return user id
     */
    @Override
    public int getAddedByUserId() {
        return addedByUserId;
    }

    /**
     * Returns genre of the media
     *
     * @return genre of the media
     */
    public String getGenre() {
        return genre;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Title: %s, Genre: %s, Added By: %d",
                mediaId, getTitle(), getGenre(), getAddedByUserId());
    }

    public User getAddedByUser() {
        return addedByUser;
    }
}
