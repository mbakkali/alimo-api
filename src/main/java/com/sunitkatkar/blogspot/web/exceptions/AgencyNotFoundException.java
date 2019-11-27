package com.sunitkatkar.blogspot.web.exceptions;

public class AgencyNotFoundException extends RuntimeException {
    public AgencyNotFoundException(String message) {
        super(message);
    }
}
