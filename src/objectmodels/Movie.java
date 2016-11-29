package objectmodels;

/**
 * Created by timothy on 2016-11-28.
 */
public class Movie extends Media {
    // Movie(mediaId,, title, userId,directorId);
    private int directorId;

    /**
     * Using media as a superclass to get access to mediaId and title
     * @param mediaId defines the movie
     * @param title title of the movie
     * @param directorId director of the movie
     */
    public Movie(int mediaId, String title, int directorId, int userId) {
        super(mediaId, title, userId);
        this.directorId = directorId;
    }

    /**
     * @return the director of the movie's id
     */
    public int getDirectorId() {
        return directorId;
    }
}
