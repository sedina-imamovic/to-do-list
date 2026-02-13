package se.jimmy.iths.todolist.exceptions;

public class GroceryListNotFoundException extends RuntimeException {
    public GroceryListNotFoundException(String message) {
        super(message);
    }
}
