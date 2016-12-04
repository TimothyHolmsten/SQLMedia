package objectmodels;

/**
 * Created by timothy on 2016-11-28.
 */
public class Director extends Person{
    // Director(directorId,directorName);
    private int directorId;

    public Director(int directorId, String directorName) {
        super(directorName);
        this.directorId = directorId;
    }

    public int getDirectorId() {
        return directorId;
    }
    @Override
    public String toString() {
        return String.format("ID: %d, Name: %s",
                directorId, getName());
    }
}
