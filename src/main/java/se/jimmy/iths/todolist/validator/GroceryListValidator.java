package se.jimmy.iths.todolist.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import se.jimmy.iths.todolist.exceptions.GroceryListValidationException;
import se.jimmy.iths.todolist.model.GroceryList;

@Component
public class GroceryListValidator {

    private static final Logger logger = LoggerFactory.getLogger(GroceryListValidator.class);

    public void validateName(String name) {
        if (name == null || name.isBlank()) {
            logger.error("Validation error: Name is null or blank");
            throw new GroceryListValidationException("Grocery list name cannot be empty");
        }
    }

    public void validateQuantity(int quantity) {
        if (quantity < 1 || quantity > 100) {
            logger.error("Validation error: Quantity is out of range");
            throw new GroceryListValidationException("Quantity must be between 1 and 100");
        }
    }

    public void validateCategory(String category) {
        if (category == null || category.isBlank()) {
            logger.error("Validation error: Category is null or blank");
            throw new GroceryListValidationException("Category cannot be empty");
        }
    }

    public void validate(GroceryList groceryList) {
        validateName(groceryList.getName());
        validateQuantity(groceryList.getQuantity());
        validateCategory(groceryList.getCategory());

    }
}
