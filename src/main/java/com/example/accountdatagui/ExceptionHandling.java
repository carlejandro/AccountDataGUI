package com.example.accountdatagui;

import java.io.FileNotFoundException;

public class ExceptionHandling {

    public static class CustomFileNotFoundException extends Exception {
        public CustomFileNotFoundException(String message) {
            super(message);
        }
    }

    public static class EmptyAcctIdException extends Exception {
        public EmptyAcctIdException(String message) {super(message);}
    }

    public static class NegativeAcctIDException extends Exception {
        public NegativeAcctIDException(String message) {super(message);}
    }

    public static class InvalidAccountException extends Exception {
        public InvalidAccountException(String message) {super(message);}
    }

    public static class EmptyPasswordException extends Exception {
        public EmptyPasswordException(String message) {super(message);}
    }

    public static class InvalidPasswordException extends Exception {
        public InvalidPasswordException(String message) {super(message);}
    }

    public static class InvalidSQAnswerException extends Exception {
        public InvalidSQAnswerException(String message) {super(message);}
    }

    public static class EmptySQAnswerException extends Exception {
        public EmptySQAnswerException(String message) {super(message);}
    }

    public static class InvalidStatusException extends Exception {
        public InvalidStatusException(String message) {super(message);}
    }
}
