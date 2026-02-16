package se.jimmy.iths.todolist.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.jimmy.iths.todolist.exceptions.GroceryListNotFoundException;
import se.jimmy.iths.todolist.exceptions.GroceryListValidationException;
import se.jimmy.iths.todolist.model.GroceryList;
import se.jimmy.iths.todolist.repository.GroceryListRepository;
import se.jimmy.iths.todolist.validator.GroceryListValidator;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GroceryListServiceTest {

    @Mock
    private GroceryListRepository repository;

    @Mock
    private GroceryListValidator validator;

    @InjectMocks
    private GroceryListService service;

    private GroceryList grocery;

    @BeforeEach
    public void setUp() {
        grocery = new GroceryList("Apple", 2, "Fruit", false);
        grocery.setId(1L);
    }

    @Test
    public void getAllGroceryLists_WhenGroceriesExists_ReturnsGroceryList() {
        when(repository.findAll()).thenReturn(List.of(grocery));
        List<GroceryList> result = service.getAllGroceries();

        assertEquals(1, result.size());
        verify(repository).findAll();

    }

    @Test
    public void createGrocery_WhenValid_SavesAndReturnsGrocery() {
        when(repository.save(grocery)).thenReturn(grocery);

        GroceryList result = service.createGrocery(grocery);
        assertEquals("Apple", result.getName());
        verify(validator).validate(grocery);
        verify(repository).save(grocery);
    }

    @Test
    public void createGrocery_ThrowsException_WhenValidationFails() {
        doThrow(new GroceryListValidationException("Invalid grocery"))
                .when(validator).validate(grocery);
        assertThrows(GroceryListValidationException.class, () -> service.createGrocery(grocery));

        verify(repository, never()).save(grocery);

    }

    @Test
    public void getGrocery_WhenFound_ReturnsGrocery() {
        when(repository.findById(1L)).thenReturn(Optional.of(grocery));

        GroceryList result = service.getGrocery(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    public void getGrocery_WhenNotFound_ThrowsGroceryListNotFoundException() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(GroceryListNotFoundException.class, () -> service.getGrocery(1L));

    }

    @Test
    public void updateGrocery_ShouldUpdateAndSave_WhenGroceryExists() {
        GroceryList update = new GroceryList("Milk", 3, "Dairy", true);
        when(repository.findById(1L)).thenReturn(Optional.of(grocery));
        when(repository.save(any())).thenReturn(grocery);

        GroceryList result = service.updateGrocery(1L, update);

        assertEquals("Milk", result.getName());

        verify(validator).validate(update);
        verify(repository).save(grocery);
    }

    @Test
    public void updateGrocery_WhenNotFound_ThrowsException() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        GroceryList update = new GroceryList("Milk", 3, "Dairy", true);

        assertThrows(GroceryListNotFoundException.class, () -> {
            service.updateGrocery(1L, update);
        });
    }

    @Test
    public void deleteGrocery_ShouldDeleteGrocery_WhenGroceryExists() {
        when(repository.findById(1L)).thenReturn(Optional.of(grocery));
        service.deleteGrocery(1L);
        verify(repository).delete(grocery);
    }

    @Test
    public void deleteGrocery_WhenNotFound_ThrowsGroceryListNotFoundException() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(GroceryListNotFoundException.class, () -> service.deleteGrocery(1L));

        verify(repository).findById(1L);
        verify(repository, never()).delete(any());
    }

}
