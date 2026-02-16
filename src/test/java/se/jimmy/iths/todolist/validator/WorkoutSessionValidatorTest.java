package se.jimmy.iths.todolist.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.jimmy.iths.todolist.exceptions.WorkoutSessionValidationException;
import se.jimmy.iths.todolist.model.WorkoutSession;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class WorkoutSessionValidatorTest {
    private WorkoutSessionValidator validator;
    private WorkoutSession validSession;

    @BeforeEach
    void setUp() {
        validator = new WorkoutSessionValidator();
        validSession = new WorkoutSession("Running", 30, 5, LocalDate.now());
    }

    @Test
    void validate_ValidSession_ShouldNotThrow() {
        assertDoesNotThrow(() -> validator.validate(validSession));
    }

    @Test
    void validate_InvalidSession_ShouldThrowException() {
        validSession.setDurationInMinutes(0);
        assertThrows(WorkoutSessionValidationException.class, () -> validator.validate(validSession));
    }

    @Test
    void validateExerciseType_ValidType_ShouldNotThrow() {
        assertDoesNotThrow(() -> validator.validateExerciseType("Running"));
    }

    @Test
    void validateExerciseType_InvalidInput_ShouldThrowException() {
        assertThrows(WorkoutSessionValidationException.class, () -> validator.validateExerciseType(null));
        assertThrows(WorkoutSessionValidationException.class, () -> validator.validateExerciseType(""));
        assertThrows(WorkoutSessionValidationException.class, () -> validator.validateExerciseType("   "));
    }

    @Test
    void validateDuration_ValidDuration_ShouldNotThrow() {
        assertDoesNotThrow(() -> validator.validateDuration(1));
    }

    @Test
    void validateDuration_InvalidInput_ShouldThrowException() {
        assertThrows(WorkoutSessionValidationException.class, () -> validator.validateDuration(0));
        assertThrows(WorkoutSessionValidationException.class, () -> validator.validateDuration(-10));
    }

    @Test
    void validateIntensity_ValidIntensity_ShouldNotThrow() {
        assertDoesNotThrow(() -> validator.validateIntensity(1));
        assertDoesNotThrow(() -> validator.validateIntensity(10));
    }

    @Test
    void validateIntensity_InvalidIntensity_ShouldThrowException() {
        assertThrows(WorkoutSessionValidationException.class, () -> validator.validateIntensity(0));
        assertThrows(WorkoutSessionValidationException.class, () -> validator.validateIntensity(11));
    }

    @Test
    void validateDate_ValidDate_ShouldNotThrow() {
        assertDoesNotThrow(() -> validator.validateDate(LocalDate.now()));
        assertDoesNotThrow(() -> validator.validateDate(LocalDate.now().minusDays(1)));
    }

    @Test
    void validateDate_InvalidInput_ShouldThrowException() {
        assertThrows(WorkoutSessionValidationException.class, () -> validator.validateDate(null));
        assertThrows(WorkoutSessionValidationException.class, () -> validator.validateDate(LocalDate.now().plusDays(1)));
    }
}
