package edu.miu.carfleet.service;

/**
 * @author bazz
 * Apr 25 2023
 * 16:45
 */
public class NotificationEvent {
    private String message;


    public NotificationEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
