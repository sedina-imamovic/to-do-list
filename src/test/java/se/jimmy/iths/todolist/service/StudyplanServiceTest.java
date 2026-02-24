package se.jimmy.iths.todolist.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.jimmy.iths.todolist.exceptions.StudyplanNotFoundException;
import se.jimmy.iths.todolist.exceptions.StudyplanValidationException;
import se.jimmy.iths.todolist.model.Studyplan;
import se.jimmy.iths.todolist.repository.StudyplanRepository;
import se.jimmy.iths.todolist.validator.StudyplanValidator;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
class StudyplanServiceTest {

    @Mock
    private StudyplanRepository studyplanRepository;

    @Mock
    private StudyplanValidator studyplanValidator;

    @InjectMocks
    private StudyplanService service;

    private Studyplan studyplan1;
    private Studyplan studyplan2;
    private List<Studyplan> studyplanList;

    @BeforeEach
    void setUp() {
        studyplan1 = new Studyplan("Task 1", 50, "Math", LocalDate.now(), LocalDate.now().plusDays(2));
        studyplan1.setId(1L);
        studyplan2 = new Studyplan("Task 2", 20, "English", LocalDate.now(), LocalDate.now().plusDays(4));
        studyplan2.setId(2L);

        studyplanList = List.of(studyplan1, studyplan2);
    }

    @Test
    void getAllStudyplans_ShouldReturnList() {
        when(studyplanRepository.findAll()).thenReturn(studyplanList);

        List<Studyplan> result = service.getAllStudyplans();

        assertEquals(2, result.size());
        assertEquals("English", result.get(1).getCoursename());
        verify(studyplanRepository).findAll();
    }

    @Test
    void getStudyplanById_ShouldReturnStudyplan() {
        when(studyplanRepository.findById(1L)).thenReturn(Optional.of(studyplan1));

        Studyplan result = service.getStudyplanById(1L);

        assertNotNull(result);
        assertEquals(studyplan1.getTask(), result.getTask());
    }

    @Test
    void getStudyplanById_NotFound_ShouldThrowException() {
        when(studyplanRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(StudyplanNotFoundException.class, () -> service.getStudyplanById(99L));
    }

    @Test
    void createStudyplan_ShouldSaveAndReturn() {
        Studyplan newPlan = new Studyplan("New Task", 20, "History", null, LocalDate.now().plusDays(3));

        when(studyplanRepository.save(any(Studyplan.class))).thenAnswer(i -> i.getArguments()[0]);

        Studyplan result = service.createStudyplan(newPlan);
        verify(studyplanValidator).validate(newPlan);
        verify(studyplanRepository).save(newPlan);
        assertNotNull(result.getStartdate()); //default if null
    }

    @Test
    void createStudyplan_ValidationFails_ShouldThrow() {
        Studyplan invalidPlan = new Studyplan("", 200, "", null, LocalDate.now());

        doThrow(new StudyplanValidationException("Invalid")).when(studyplanValidator).validate(invalidPlan);

        assertThrows(StudyplanValidationException.class, () -> service.createStudyplan(invalidPlan));
        verify(studyplanRepository, never()).save(any());
    }

    @Test
    void updateStudyplan_ShouldUpdateAndSave() {
        Studyplan updated = new Studyplan("Updated Task", 40, "English", LocalDate.now(), LocalDate.now().plusDays(1));
        when(studyplanRepository.findById(1L)).thenReturn(Optional.of(studyplan1));
        when(studyplanRepository.save(any(Studyplan.class))).thenAnswer(i -> i.getArguments()[0]);

        Studyplan result = service.updateStudyplan(updated, 1L);

        assertEquals("Updated Task", result.getTask());
        verify(studyplanValidator).validate(studyplan1);
        verify(studyplanRepository).save(studyplan1);
    }

    @Test
    void updateStudyplan_NotFound_ShouldThrow() {
        when(studyplanRepository.findById(99L)).thenReturn(Optional.empty());
        Studyplan updated = new Studyplan("Updated", 40, "English", LocalDate.now(), LocalDate.now().plusDays(1));

        assertThrows(StudyplanNotFoundException.class, () -> service.updateStudyplan(updated, 99L));
    }

    @Test
    void deleteStudyplanById_ShouldDelete() {
        when(studyplanRepository.findById(1L)).thenReturn(Optional.of(studyplan1));

        service.deleteStudyplanById(1L);

        verify(studyplanRepository).deleteById(1L);
    }

    @Test
    void deleteStudyplanById_NotFound_ShouldThrow() {
        when(studyplanRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(StudyplanNotFoundException.class, () -> service.deleteStudyplanById(99L));
        verify(studyplanRepository, never()).deleteById(any());
    }
}
