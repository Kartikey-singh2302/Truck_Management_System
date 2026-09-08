package com.Kartikey_Singh.TMS.exception;

public class LoadAlreadyBookedException extends RuntimeException {
    public LoadAlreadyBookedException() {
        super("Load with ID " + " is already booked.");
    }
}
