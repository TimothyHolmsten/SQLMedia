package objectmodels;

/**
 * Created by timothy on 2016-11-28.
 */
public class Director extends Person {
    // Director(directorId,directorName);
    private int directorId;

    /**
     * Represents a directory entity of the database
     *
     * @param directorId   id of the director
     * @param directorName name of the director
     */
    public Director(int directorId, String directorName) {
        super(directorName);
        this.directorId = directorId;
    }

    public Director(String directorName) {
        super(directorName);
    }

    /**
     * Returns the director's id
     *
     * @return director's id
     */
    public int getDirectorId() {
        return directorId;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Name: %s",
                directorId, getName());
    }

}
