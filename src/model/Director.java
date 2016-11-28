package model;

/**
 * Created by timothy on 2016-11-28.
 */
public class Director {
    // Director(directorId,directorName);
    private int directorId;
    private int directorName;

    public Director(int directorId, int directorName) {
        this.directorId = directorId;
        this.directorName = directorName;
    }

    public int getDirectorId() {
        return directorId;
    }

    public int getDirectorName() {
        return directorName;
    }
}
