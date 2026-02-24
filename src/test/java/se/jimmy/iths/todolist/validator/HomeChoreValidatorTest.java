package se.jimmy.iths.todolist.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.jimmy.iths.todolist.exceptions.HomeChoreValidationException;
import se.jimmy.iths.todolist.model.HomeChore;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HomeChoreValidatorTest {

    private HomeChoreValidator validator;

    @BeforeEach
    public void setUp() {
        validator = new HomeChoreValidator();
    }

    @Test
    @DisplayName("Validate chore with no exception")
    public void validateChore() {
        assertDoesNotThrow(() -> validator.validateChore("Vacuum"));
    }
    @Test
    @DisplayName("Validate chore with exception")
    public void validateChoreBlank() {
        assertThrows(HomeChoreValidationException.class, () -> validator.validateChore(" "));
        assertThrows(HomeChoreValidationException.class, () -> validator.validateChore(""));
        assertThrows(HomeChoreValidationException.class, () -> validator.validateChore(null));
    }

    @Test
    @DisplayName("Validate Room with no exception")
    public void validateRoom() {
        assertDoesNotThrow(() -> validator.validateRoom("Living room"));
    }

    @Test
    @DisplayName("Validate Room with exception")
    public void validateRoomBlank() {
        assertThrows(HomeChoreValidationException.class, () -> validator.validateRoom(" "));
        assertThrows(HomeChoreValidationException.class, () -> validator.validateRoom(""));
        assertThrows(HomeChoreValidationException.class, () -> validator.validateRoom(null));
    }

    @Test
    @DisplayName("Validate Estimated time with no exception")
    public void validateEstimatedTime() {
        assertDoesNotThrow(() -> validator.validateEstimatedTime(40));
    }

    @Test
    @DisplayName("Validate Estimated time with exception")
    public void validateEstimatedTimeUnderAndOver() {
        assertThrows(HomeChoreValidationException.class, () -> validator.validateEstimatedTime(61));
        assertThrows(HomeChoreValidationException.class, () -> validator.validateEstimatedTime(-1));
    }

    @Test
    @DisplayName("Validate with correct input")
    public void validateChoreWithCorrectInput() {
        HomeChore homeChore = new HomeChore("Vacuum","Kitchen",true,20);
        assertDoesNotThrow(() -> validator.validate(homeChore));
    }

    @Test
    @DisplayName("Validate with incorrect input")
    public void validateChoreWithIncorrectInput() {
        HomeChore homeChore = new HomeChore("",null,false,-1);
        assertThrows(HomeChoreValidationException.class, () -> validator.validate(homeChore));
    }
}