package se.jimmy.iths.todolist.validator;


import org.springframework.stereotype.Component;
import se.jimmy.iths.todolist.exceptions.StudyplanValidationException;
import se.jimmy.iths.todolist.model.Studyplan;


import java.time.LocalDate;

@Component
public class StudyplanValidator {

    public void validate(Studyplan studyplan) {
        validateTaskType(studyplan.getTask());
        validateCourseName(studyplan.getCoursename());
        validatePriority(studyplan.getPriority());
        validateStartdate(studyplan.getStartdate());
        validateDeadline(studyplan.getDeadline());
        validateDateOrder(studyplan.getStartdate(), studyplan.getDeadline());

    }
    public void validateTaskType(String taskType) {
        if (taskType == null || taskType.isBlank()) {
            throw new  StudyplanValidationException("Task type is empty");
        }
    }
    public void validateCourseName(String courseName) {
        if (courseName == null || courseName.isBlank()) {
            throw new  StudyplanValidationException("Course name is empty");
        }
    }
    public void validatePriority(int priority) {
        if (priority < 0 || priority > 100) {
            throw new  StudyplanValidationException("Priority must be between 0 and 100");
        }
    }
    public void validateStartdate(LocalDate startdate) {
        if (startdate == null) {
            throw new  StudyplanValidationException("Start date should not be empty");
        }
    }
    public void validateDeadline(LocalDate deadline) {
        if (deadline == null) {
            throw new  StudyplanValidationException("Deadline should not be empty");
        }
    }

    public void validateDateOrder(LocalDate startdate, LocalDate deadline) {
        if (startdate != null && deadline != null && deadline.isBefore(startdate)) {
            throw new  StudyplanValidationException("Deadline should be before start date");
        }
    }

}


