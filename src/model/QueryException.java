package model;

/**
 * Created by timothy on 2016-12-02, model.
 */
public class QueryException extends Exception {
    QueryException(String msg) {
        super(msg);
    }

    QueryException() {
        super();
    }
}
