package edu.miu.carfleet.domain;

/**
 * @author bazz
 * Apr 22 2023
 * 16:12
 */
public class CarError {
    public String message;

    public CarError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
