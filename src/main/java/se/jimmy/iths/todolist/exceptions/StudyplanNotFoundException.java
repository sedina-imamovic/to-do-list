package se.jimmy.iths.todolist.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StudyplanNotFoundException extends RuntimeException {
    public StudyplanNotFoundException(String message) {
        super(message);
    }
}
