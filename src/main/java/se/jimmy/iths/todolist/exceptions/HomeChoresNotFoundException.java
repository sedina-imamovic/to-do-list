package se.jimmy.iths.todolist.exceptions;

public class HomeChoresNotFoundException extends RuntimeException {
    public HomeChoresNotFoundException(String message) {
        super(message);
    }
}
