package objectmodels;

/**
 * Created by timothy on 2016-11-28.
 */
public class Movie extends Media {
    // Movie(mediaId,, title, userId,directorId);
    private int directorId;
    private int movieId;
    private Director director;
    /**
     * Represent a movie entity of the database
     *
     * @param mediaId    defines the movie
     * @param title      title of the movie
     * @param directorId director of the movie
     */
    public Movie(int movieId, int mediaId, String title, int directorId, int userId, String genre) {
        super(mediaId, title, userId, genre);
        this.movieId = movieId;
        this.directorId = directorId;
    }

    public Movie(String title, String genre, Director director, User user) {
        super(title, genre, user);
        this.director=director;

    }

    /**
     * @return the director of the movie's id
     */
    public int getDirectorId() {
        return directorId;
    }

    /**
     * Returns the id of the movie
     *
     * @return id of the movie
     */
    public int getMovieId() {
        return movieId;
    }

    public Director getDirector() {
        return director;
    }


}
