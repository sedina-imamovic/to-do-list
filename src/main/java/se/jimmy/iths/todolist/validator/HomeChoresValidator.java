package se.jimmy.iths.todolist.validator;

import org.springframework.stereotype.Component;
import se.jimmy.iths.todolist.exceptions.HomeChoresValidationException;
import se.jimmy.iths.todolist.model.HomeChores;

@Component
public class HomeChoresValidator {

    public void validateChore(String chore){
        if(chore == null || chore.isBlank()){
            throw new HomeChoresValidationException("Chore cannot be empty");
        }
    }

    public void validateRoom(String room){
        if(room == null || room.isBlank()){
            throw new HomeChoresValidationException("Room cannot be empty");
        }
    }

    public void validateEstimatedTime(int estimatedTime){
        if(estimatedTime < 0 || estimatedTime > 60){
            throw new HomeChoresValidationException("Estimated time cannot be negative or more than 60 minutes");
        }
    }

    public void validate(HomeChores homeChores){
        validateChore(homeChores.getChore());
        validateRoom(homeChores.getRoom());
        validateEstimatedTime(homeChores.getEstimatedTime());
    }
}
