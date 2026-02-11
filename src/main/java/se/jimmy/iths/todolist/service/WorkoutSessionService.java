package se.jimmy.iths.todolist.service;

import org.springframework.stereotype.Service;
import se.jimmy.iths.todolist.exceptions.WorkoutSessionNotFoundException;
import se.jimmy.iths.todolist.model.WorkoutSession;
import se.jimmy.iths.todolist.repository.WorkoutSessionRepository;
import se.jimmy.iths.todolist.validator.WorkoutSessionValidator;

import java.util.List;

@Service
public class WorkoutSessionService {

    private final WorkoutSessionRepository workoutSessionRepository;
    private final WorkoutSessionValidator validator;

    public WorkoutSessionService(WorkoutSessionRepository workoutSessionRepository, WorkoutSessionValidator validator) {
        this.workoutSessionRepository = workoutSessionRepository;
        this.validator = validator;
    }

    public List<WorkoutSession> getAllWorkoutSessions() {
        return workoutSessionRepository.findAll();
    }

    public WorkoutSession getById(Long id) {
        return workoutSessionRepository.findById(id).orElseThrow(() -> new WorkoutSessionNotFoundException("Session not found"));
    }

    public WorkoutSession create(WorkoutSession workoutSession) {
        validator.validate(workoutSession);
        return workoutSessionRepository.save(workoutSession);
    }

    public WorkoutSession update(WorkoutSession workoutSession, Long id) {
        WorkoutSession sessionToUpdate = getById(id);
        sessionToUpdate.setExerciseType(workoutSession.getExerciseType());
        sessionToUpdate.setDurationInMinutes(workoutSession.getDurationInMinutes());
        sessionToUpdate.setIntensity(workoutSession.getIntensity());
        sessionToUpdate.setDate(workoutSession.getDate());
        validator.validate(sessionToUpdate);
        return workoutSessionRepository.save(sessionToUpdate);
    }

    public void delete(Long id) {
        getById(id);
        workoutSessionRepository.deleteById(id);
    }
}
