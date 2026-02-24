package se.jimmy.iths.todolist.validator;

import org.junit.jupiter.api.*;
import se.jimmy.iths.todolist.exceptions.StudyplanValidationException;
import se.jimmy.iths.todolist.model.Studyplan;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StudyplanValidatorTest {

    private StudyplanValidator studyplanValidator;
    private Studyplan validStudyplan;

    @BeforeEach
    void setUp() {
        studyplanValidator = new StudyplanValidator();
        validStudyplan = new Studyplan("Task 1", 50, "Math", LocalDate.now(), LocalDate.now().plusDays(2));
    }
    @Test
    void validate_ValidStudyplan_ShouldNotThrowException() {
        assertDoesNotThrow(() -> studyplanValidator.validate(validStudyplan));
    }

    @Test
    void validate_NullTask_ShouldThrowException() {
        validStudyplan.setTask(null);

        assertThrows(StudyplanValidationException.class,
                () -> studyplanValidator.validate(validStudyplan));
    }

    @Test
    void validate_EmptyCourseName_ShouldThrowException() {
        validStudyplan.setCoursename("");

        assertThrows(StudyplanValidationException.class,
                () -> studyplanValidator.validate(validStudyplan));
    }

    @Test
    void validate_InvalidPriority_ShouldThrowException() {
        validStudyplan.setPriority(200);

        assertThrows(StudyplanValidationException.class,
                () -> studyplanValidator.validate(validStudyplan));
    }

    @Test
    void validate_NullStartDate_ShouldThrowException() {
        validStudyplan.setStartdate(null);

        assertThrows(StudyplanValidationException.class,
                () -> studyplanValidator.validate(validStudyplan));
    }

    @Test
    void validate_DeadlineBeforeStart_ShouldThrowException() {
        validStudyplan.setStartdate(LocalDate.now());
        validStudyplan.setDeadline(LocalDate.now().minusDays(1));

        assertThrows(StudyplanValidationException.class,
                () -> studyplanValidator.validate(validStudyplan));
    }
}

