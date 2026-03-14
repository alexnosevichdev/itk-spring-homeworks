package ru.alexandernosevich.springhw2.exception;

public class InvalidException extends RuntimeException {
    public InvalidException(String message) {
        super(message);
    }
}
