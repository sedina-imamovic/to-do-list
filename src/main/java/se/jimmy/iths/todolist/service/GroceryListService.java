package se.jimmy.iths.todolist.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import se.jimmy.iths.todolist.exceptions.GroceryListNotFoundException;
import se.jimmy.iths.todolist.model.GroceryList;
import se.jimmy.iths.todolist.repository.GroceryListRepository;
import se.jimmy.iths.todolist.validator.GroceryListValidator;

import java.util.List;

@Service
public class GroceryListService {

    private static final Logger logger = LoggerFactory.getLogger(GroceryListService.class);
    private final GroceryListRepository groceryListRepository;
    private final GroceryListValidator groceryListValidator;

    public GroceryListService(GroceryListRepository groceryListRepository, GroceryListValidator groceryListValidator) {
        this.groceryListRepository = groceryListRepository;
        this.groceryListValidator = groceryListValidator;

    }

    public List<GroceryList> getAllGroceries() {
        logger.info("Get all groceries");
        return groceryListRepository.findAll();
    }

    public GroceryList createGrocery(GroceryList groceryList) {
        logger.info("Validating and saving grocery: {}", groceryList.getName());
        groceryListValidator.validate(groceryList);
        return groceryListRepository.save(groceryList);
    }

    public GroceryList getGrocery(Long id) {
        //logger.info("Get grocery with id: {}", id);
        return groceryListRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Grocery list with id: {} not found", id);
                    return new GroceryListNotFoundException("Grocery list not found with id: " + id);
                });
    }

    public GroceryList updateGrocery(Long id, GroceryList groceryListupdate) {

        GroceryList existing = getGrocery(id);
        logger.info("Update grocery list with id: {}", id);

        existing.setName(groceryListupdate.getName());
        existing.setQuantity(groceryListupdate.getQuantity());
        existing.setCategory(groceryListupdate.getCategory());
        existing.setPurchased(groceryListupdate.isPurchased());

        groceryListValidator.validate(groceryListupdate);

        return groceryListRepository.save(existing);
    }

    public void deleteGrocery(Long id) {

        GroceryList existing = getGrocery(id);
        logger.info("Delete grocery with id: {}", id);
        groceryListRepository.delete(existing);

    }

}
