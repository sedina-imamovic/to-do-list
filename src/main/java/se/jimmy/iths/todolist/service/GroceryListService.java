package se.jimmy.iths.todolist.service;

import org.springframework.stereotype.Service;
import se.jimmy.iths.todolist.exceptions.GroceryListNotFoundException;
import se.jimmy.iths.todolist.model.GroceryList;
import se.jimmy.iths.todolist.repository.GroceryListRepository;
import se.jimmy.iths.todolist.validator.GroceryListValidator;

import java.util.List;

@Service
public class GroceryListService {

    private final GroceryListRepository groceryListRepository;
    private final GroceryListValidator groceryListValidator;

    public GroceryListService(GroceryListRepository groceryListRepository, GroceryListValidator groceryListValidator) {
        this.groceryListRepository = groceryListRepository;
        this.groceryListValidator = groceryListValidator;

    }


    public List<GroceryList> getAllGroceries() {
        return groceryListRepository.findAll();
    }

    public GroceryList createGrocery(GroceryList groceryList) {
        groceryListValidator.validate(groceryList);
        return groceryListRepository.save(groceryList);
    }

    public GroceryList getGrocery(Long id) {
        return groceryListRepository.findById(id)
                .orElseThrow(() -> new GroceryListNotFoundException("Grocery list not found with id: " + id));
    }


    public GroceryList updateGrocery(Long id, GroceryList groceryListupdate) {
        GroceryList existing = getGrocery(id);


        existing.setName(groceryListupdate.getName());
        existing.setQuantity(groceryListupdate.getQuantity());
        existing.setCategory(groceryListupdate.getCategory());
        existing.setPurchased(groceryListupdate.isPurchased());

        groceryListValidator.validate(groceryListupdate);

        return groceryListRepository.save(existing);
    }

    public void deleteGrocery(Long id) {
        GroceryList existing = getGrocery(id);
        groceryListRepository.delete(existing);

    }

}
