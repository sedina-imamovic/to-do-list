package se.jimmy.iths.todolist.exceptions;

public class HomeChoreNotFoundException extends RuntimeException {
    public HomeChoreNotFoundException(String message) {
        super(message);
    }
}
