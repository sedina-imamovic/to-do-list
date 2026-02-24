package se.jimmy.iths.todolist.validator;

import org.springframework.stereotype.Component;
import se.jimmy.iths.todolist.exceptions.HomeChoreValidationException;
import se.jimmy.iths.todolist.model.HomeChore;

@Component
public class HomeChoreValidator {

    public void validateChore(String chore){
        if(chore == null || chore.isBlank()){
            throw new HomeChoreValidationException("Chore cannot be empty");
        }
    }

    public void validateRoom(String room){
        if(room == null || room.isBlank()){
            throw new HomeChoreValidationException("Room cannot be empty");
        }
    }

    public void validateEstimatedTime(int estimatedTime){
        if(estimatedTime < 0 || estimatedTime > 60){
            throw new HomeChoreValidationException("Estimated time cannot be negative or more than 60 minutes");
        }
    }

    public void validate(HomeChore homeChore){
        validateChore(homeChore.getChore());
        validateRoom(homeChore.getRoom());
        validateEstimatedTime(homeChore.getEstimatedTime());
    }
}
