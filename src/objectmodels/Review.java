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

    public Review(int reviewId, String reviewText, int rating, int mediaId, int userId) {
        this.reviewId = reviewId;
        this.reviewText = reviewText;
        this.rating = rating;
        this.mediaId = mediaId;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public int getMediaId() {
        return mediaId;
    }

    public int getRating() {
        return rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public int getReviewId() {
        return reviewId;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Rating: %d, Comment: %s",
                reviewId, rating, reviewText);
    }
}
