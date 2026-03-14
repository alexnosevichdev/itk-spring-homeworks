package ru.alexandernosevich.springhw2.exception;

import java.time.LocalDateTime;

public class ErrorResponse {
    private int status;
    private String message;
    private LocalDateTime timelog;

    public ErrorResponse(int status,  String message, LocalDateTime timelog) {
        this.status = status;
        this.message = message;
        this.timelog = timelog;
    }

    public int getStatus() {
        return status;
    }

    public LocalDateTime getTimelog() {
        return timelog;
    }

    public String getMessage() {
        return message;
    }
}
