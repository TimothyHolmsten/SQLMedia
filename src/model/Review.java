package model;

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
}
