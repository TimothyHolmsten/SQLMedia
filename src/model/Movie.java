package model;

/**
 * Created by timothy on 2016-11-28.
 */
public class Movie extends Media{
    // Movie(mediaId,, title, userId,directorId);
    private int directorId;

    public Movie(int mediaId, String title, int directorId) {
        super(mediaId, title);
        this.directorId = directorId;
    }

    public int getDirectorId() {
        return directorId;
    }
}
