package objectmodels;

import java.util.ArrayList;

/**
 * Created by timothy on 2016-11-28.
 */
public class User extends Person {
    //user(userId, userName);
    private int userId;
    private ArrayList<Review> reviews;

    public User(int userId, String userName) {
        super(userName);
        this.userId = userId;
        reviews = new ArrayList<>();
    }

    public User(int userId, String userName, ArrayList<Review> reviews) {
        super(userName);
        this.userId = userId;
        this.reviews = reviews;
    }

    public int getUserId() {
        return userId;
    }

    public ArrayList<Review> getReviews() {
        return (ArrayList<Review>) reviews.clone();
    }

    @Override
    public String toString() {
        if (reviews.size() > 0)
            return String.format("ID: %d, Name: %s, Reviews: %s",
                    userId, getName(), reviews.toString());
        else
            return String.format("ID: %d, Name: %s",
                    userId, getName());
    }
}
