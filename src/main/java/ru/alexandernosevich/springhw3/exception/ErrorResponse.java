package ru.alexandernosevich.springhw3.exception;

import java.time.LocalDateTime;

public class ErrorResponse {
    private String message;
    private int status;
    private LocalDateTime timelog;

    public ErrorResponse(String message, int status, LocalDateTime timelog) {
        this.message = message;
        this.status = status;
        this.timelog = timelog;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimelog() {
        return timelog;
    }

    public int getStatus() {
        return status;
    }
}
