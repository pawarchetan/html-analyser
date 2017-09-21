package de.scout24.analyser.exception;

public class InvalidUrlException extends IllegalArgumentException {

    private String message = "";

    public InvalidUrlException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
