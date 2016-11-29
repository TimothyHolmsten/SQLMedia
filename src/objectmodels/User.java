package objectmodels;

import java.util.ArrayList;

/**
 * Created by timothy on 2016-11-28.
 */
public class User {
    //user(userId, userName);
    private int userId;
    private String userName;
    private ArrayList<Review> reviews;

    public User(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
        reviews = new ArrayList<>();
    }

    public User(int userId, String userName, ArrayList<Review> reviews) {
        this.userId = userId;
        this.userName = userName;
        this.reviews = reviews;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public ArrayList<Review> getReviews() {
        return (ArrayList<Review>) reviews.clone();
    }
}
