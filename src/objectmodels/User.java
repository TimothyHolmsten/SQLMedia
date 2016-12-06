package objectmodels;

import java.util.ArrayList;

/**
 * Created by timothy on 2016-11-28.
 * This class represents a user , is extended from person class.
 * Every user has an userId and list of all reviews
 */
public class User extends Person {

    //user(userId, userName);
    private int userId;
    private ArrayList<Review> reviews;

    /**
     *  this constructor use a String and a integers ,
     *  @param  userId is the first parameter to construtor
     *  @param userName  is the second parameter to construtor
     *
     *
     */
    public User(int userId, String userName) {
        super(userName);
        this.userId = userId;
        reviews = new ArrayList<>();
    }

    /**
     * this a second construtor with three parameter , an arraylist of review and a interge
     * and a string
     * @param reviews added all reviews that person has done
     * @param userName added username
     * @param userId added userid to user class
     */
    public User(int userId, String userName, ArrayList<Review> reviews) {
        super(userName);
        this.userId = userId;
        this.reviews = reviews;
    }

    /**
     *
     * @returnthis method returns the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * this
     * @return array list , this returns a list of all reviews user has done
     */
    public ArrayList<Review> getReviews() {
        return (ArrayList<Review>) reviews.clone();
    }

    @Override
    /**
     * this metod is exetends from toString
     * @return alla Users data in a String
     */
    public String toString() {
        if (reviews.size() > 0)
            return String.format("ID: %d, Name: %s, Reviews: %s",
                    userId, getName(), reviews.toString());
        else
            return String.format("ID: %d, Name: %s",
                    userId, getName());
    }
}
