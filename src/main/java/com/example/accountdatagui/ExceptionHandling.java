package com.example.accountdatagui;

import java.io.FileNotFoundException;

public class ExceptionHandling {

    public static class CustomFileNotFoundException extends Exception {
        public CustomFileNotFoundException(String message) {
            super(message);
        }
    }
}
