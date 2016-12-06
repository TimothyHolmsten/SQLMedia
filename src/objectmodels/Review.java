package objectmodels;

/**
 * Created by timothy on 2016-11-28.
 */
public class Review {
    //review( reviewId, reviewText,rating,mediaId, userId);
    private int reviewId;
    private String reviewText;
    private int rating;
    private int mediaId;
    private int userId;

    /**
     * Represents a review entity of the database
     *
     * @param reviewId   id of the review
     * @param reviewText the reviews comment/text
     * @param rating     rating of the review
     * @param mediaId    id of the media that was reviewed
     * @param userId     id of the user who made the review
     */
    public Review(int reviewId, String reviewText, int rating, int mediaId, int userId) {
        this.reviewId = reviewId;
        this.reviewText = reviewText;
        this.rating = rating;
        this.mediaId = mediaId;
        this.userId = userId;
    }

    /**
     * Returns id of the user who made the review
     *
     * @return user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Returns the id of the media that was reviewed
     *
     * @return id of the media
     */
    public int getMediaId() {
        return mediaId;
    }

    /**
     * Returns the rating that was given in the review
     *
     * @return review's rating
     */
    public int getRating() {
        return rating;
    }

    /**
     * Returns the comment/text of the review
     *
     * @return comment of the review
     */
    public String getReviewText() {
        return reviewText;
    }

    /**
     * Returns the id of the review
     *
     * @return id of review
     */
    public int getReviewId() {
        return reviewId;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Rating: %d, Comment: %s",
                reviewId, rating, reviewText);
    }
}
