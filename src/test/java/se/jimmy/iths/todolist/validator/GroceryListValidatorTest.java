package se.jimmy.iths.todolist.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.jimmy.iths.todolist.exceptions.GroceryListValidationException;
import se.jimmy.iths.todolist.model.GroceryList;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GroceryListValidatorTest {

    private GroceryListValidator validator;

    @BeforeEach
    public void setup() {
        validator = new GroceryListValidator();
    }

    @Test
    public void validateNameDoesNotThrowException() {
        assertDoesNotThrow(() -> validator.validateName("Apple"));
    }

    @Test
    public void validateNameThrowsExceptionWhenBlank() {
        assertThrows(GroceryListValidationException.class, () -> validator.validateName(" "));
    }

    @Test
    void validateQuantityDoesNotThrowException() {
        assertDoesNotThrow(() -> validator.validateQuantity(10));
    }

    @Test
    public void validateQuantityThrowsExceptionWhenMaxQuantityIsOneHundred() {
        assertThrows(GroceryListValidationException.class, () -> validator.validateQuantity(150));
    }

    @Test
    void validateCategoryDoesNotThrowException() {
        assertDoesNotThrow(() -> validator.validateCategory("Fruit"));
    }

    @Test
    public void validateCategoryThrowExceptionWhenBlank() {
        assertThrows(GroceryListValidationException.class, () -> validator.validateCategory(" "));
    }

    @Test
    public void validateCategoryThrowExceptionWhenNull() {
        assertThrows(GroceryListValidationException.class, () -> validator.validateCategory(null));
    }

    @Test
    public void validateWhenValidInput() {
        GroceryList grocery = new GroceryList("Apple", 3, "Fruit", false);
        assertDoesNotThrow(() -> validator.validate(grocery));
    }

    @Test
    public void validateThrowsExceptionWhenInvalid() {
        GroceryList grocery = new GroceryList(" ", 0, " ", false);
        assertThrows(GroceryListValidationException.class, () -> validator.validate(grocery));
    }


}
