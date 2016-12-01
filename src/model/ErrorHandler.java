package model;

/**
 * Created by timothy on 2016-12-01.
 */
public class ErrorHandler {
    private String errorMessage;

    public ErrorHandler() {
        errorMessage = null;
    }

    public void setErrorMessage(String message) {
        errorMessage = message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void resetErrorMessage() {
        errorMessage = null;
    }
}
