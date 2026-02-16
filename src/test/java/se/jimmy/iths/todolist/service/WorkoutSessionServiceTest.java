package se.jimmy.iths.todolist.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.jimmy.iths.todolist.exceptions.WorkoutSessionNotFoundException;
import se.jimmy.iths.todolist.exceptions.WorkoutSessionValidationException;
import se.jimmy.iths.todolist.model.WorkoutSession;
import se.jimmy.iths.todolist.repository.WorkoutSessionRepository;
import se.jimmy.iths.todolist.validator.WorkoutSessionValidator;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkoutSessionServiceTest {

    @Mock
    private WorkoutSessionRepository repository;

    @Mock
    private WorkoutSessionValidator validator;

    @InjectMocks
    private WorkoutSessionService service;

    private List<WorkoutSession> workoutSessions;

    private WorkoutSession newSession;

    @BeforeEach
    void setUp() {

        newSession = new WorkoutSession("Cycling", 50, 8, LocalDate.now());

        WorkoutSession session1 = new WorkoutSession("Gym", 30, 3, LocalDate.now());
        session1.setId(1L);

        WorkoutSession session2 = new WorkoutSession("Running", 60, 6, LocalDate.now().minusDays(1L));
        session2.setId(2L);

        WorkoutSession session3 = new WorkoutSession("Dance", 45, 5, LocalDate.now().minusDays(2L));
        session3.setId(3L);

        workoutSessions = List.of(session1, session2, session3);
    }

    @Test
    void getAllWorkoutSessions_ShouldReturnListOfSessions() {
        when(repository.findAll()).thenReturn(workoutSessions);

        List<WorkoutSession> result = service.getAllWorkoutSessions();

        assertEquals(3, result.size());
        assertEquals(2L, result.get(1).getId());
        assertEquals("Dance", result.getLast().getExerciseType());
    }

    @Test
    void getById_ShouldReturnWorkoutSession() {
        Long id = 4L;
        newSession.setId(id);
        when(repository.findById(id)).thenReturn(Optional.ofNullable(newSession));

        WorkoutSession result = service.getById(id);

        assertNotNull(result);
        assertEquals("Cycling", result.getExerciseType());
        assertEquals(id, result.getId());
    }

    @Test
    void getById_IdNotFound_ShouldReturnWorkoutSessionNotFoundException() {
        Long id = 99L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(WorkoutSessionNotFoundException.class, () -> service.getById(id));
    }

    @Test
    void create_ShouldReturnWorkoutSession() {
        when(repository.save(any(WorkoutSession.class))).thenReturn(newSession);

        WorkoutSession result = service.create(newSession);

        verify(validator).validate(newSession);
        verify(repository).save(newSession);
        assertNotNull(result);
        assertEquals("Cycling", result.getExerciseType());
    }

    @Test
    void create_ShouldThrowException_WhenValidationFails() {
        doThrow(new WorkoutSessionValidationException("Validation failed")).when(validator).validate(newSession);

        assertThrows(WorkoutSessionValidationException.class, () -> service.create(newSession));

        verify(repository, never()).save(any());
    }

    @Test
    void update_ShouldUpdateAndSaveSession() {
        Long id = 1L;
        WorkoutSession existingSession = workoutSessions.getFirst();

        when(repository.findById(id)).thenReturn(Optional.of(existingSession));
        when(repository.save(any(WorkoutSession.class))).thenAnswer(i -> i.getArguments()[0]);

        WorkoutSession result = service.update(newSession, id);

        assertEquals("Cycling", result.getExerciseType());
        assertEquals(id, result.getId());

        verify(validator).validate(existingSession);
        verify(repository).save(existingSession);
    }

    @Test
    void update_ShouldThrowException_WhenValidationFails() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.of(workoutSessions.getFirst()));
        doThrow(new WorkoutSessionValidationException("Validation failed")).when(validator).validate(any());

        assertThrows(WorkoutSessionValidationException.class, () -> service.update(newSession, id));

        verify(repository, never()).save(any());
    }

    @Test
    void update_IdNotFound_ShouldReturnWorkoutSessionNotFoundException() {
        Long id = 99L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(WorkoutSessionNotFoundException.class, () -> service.update(newSession, id));
    }

    @Test
    void delete_ShouldDelete_WhenIdExists() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.of(workoutSessions.getFirst()));

        service.delete(id);

        verify(repository).deleteById(id);
    }

    @Test
    void delete_IdNotFound_ShouldThrowException() {
        Long id = 99L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(WorkoutSessionNotFoundException.class, () -> service.delete(id));
        verify(repository, never()).deleteById(any());
    }
}