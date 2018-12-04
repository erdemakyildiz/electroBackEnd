package com.electro.exception;

/**
 * Created by Erdem Akyıldız on 4.12.2018.
 */
public class NotAuthorizedEvent extends Exception {

    public NotAuthorizedEvent(String message) {
        super(message);
    }
}
