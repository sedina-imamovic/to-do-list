package se.jimmy.iths.todolist.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.jimmy.iths.todolist.exceptions.HomeChoreNotFoundException;
import se.jimmy.iths.todolist.exceptions.HomeChoreValidationException;
import se.jimmy.iths.todolist.model.HomeChore;
import se.jimmy.iths.todolist.repository.HomeChoreRepository;
import se.jimmy.iths.todolist.validator.HomeChoreValidator;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HomeChoreServiceTest {
    @Mock
    private HomeChoreRepository repository;

    @Mock
    private HomeChoreValidator validator;

    @InjectMocks
    private HomeChoreService service;

    private HomeChore chore;

    @BeforeEach
    public void setUp() {
        chore = new HomeChore("Vacuum","Living room",false,10);
        chore.setId(1L);
    }

    @Test
    @DisplayName("Chore found and returns correctly")
    void getChoreCorrect() {
        when(repository.findById(1L)).thenReturn(Optional.of(chore));

        HomeChore result = service.getChore(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    @DisplayName("Chore not found and throws exception")
    void getChoreIncorrect() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(HomeChoreNotFoundException.class, () -> service.getChore(1L));
    }

    @Test
    @DisplayName("getAllChores returns correctly")
    void getAllChoresCorrect() {
        when(repository.findAll()).thenReturn(List.of(chore));

        List<HomeChore> result = service.getAllChores();

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("createChore valid input saves and returns chore correctly")
    void createChoreCorrect() {
        when(repository.save(chore)).thenReturn(chore);

        HomeChore result = service.createChore(chore);
        assertEquals("Vacuum", result.getChore());

        verify(validator).validate(chore);
        verify(repository).save(chore);
    }

    @Test
    @DisplayName("createChore invalid input doesnt save and throws exception")
    void createChoreIncorrect() {
        doThrow(new HomeChoreValidationException("Could not validate")).when(validator).validate(chore);

        assertThrows(HomeChoreValidationException.class, () -> service.createChore(chore));

        verify(repository, never()).save(chore);
    }

    @Test
    @DisplayName("updateChore valid input and returns chore correctly")
    void updateChoreCorrect() {
        when(repository.findById(1L)).thenReturn(Optional.of(chore));
        HomeChore update = new HomeChore("Dust","Kitchen",true,30);

        assertDoesNotThrow(() -> service.updateChore(1L, update));

        verify(validator).validate(update);
        verify(repository).save(chore);
    }

    @Test
    @DisplayName("updateChore throws exception")
    void updateChoreIncorrect() {
        when(repository.findById(5L)).thenReturn(Optional.empty());
        HomeChore update = new HomeChore("Dust","Kitchen",true,30);

        assertThrows(HomeChoreNotFoundException.class, () -> service.updateChore(5L, update));
    }

    @Test
    @DisplayName("Delete by id correctly")
    void deleteByIdCorrectly() {
        when(repository.findById(1L)).thenReturn(Optional.of(chore));

        assertDoesNotThrow(() -> service.deleteChore(1L));

        verify(repository).delete(chore);
    }

    @Test
    @DisplayName("Delete throws exception")
    void deleteByIdIncorrect() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(HomeChoreNotFoundException.class, () -> service.deleteChore(1L));
    }
}
