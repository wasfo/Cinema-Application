package org.com.exceptions;

public class MovieDuplicateException extends RuntimeException{

    public MovieDuplicateException(String message) {
        super(message);
    }
}
