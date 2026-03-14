package ru.alexandernosevich.springhw2.exception;

public class ResourseOrBookNotFoundException extends RuntimeException {
    public ResourseOrBookNotFoundException(String message) {
        super(message);
    }
}
