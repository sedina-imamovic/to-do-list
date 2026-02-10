package se.jimmy.iths.todolist.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroceryListService {

    private final GroceryListRepository groceryListRepository;
    //private final GroceryListValidator groceryListValidator;

    public GroceryListService(GroceryListRepository groceryListRepository) {
        this.groceryListRepository = groceryListRepository;

    }

    public List<GroceryList> getAllGroceries() {
        return groceryListRepository.findAll();
    }

    public GroceryList createGrocery(GroceryList groceryList) {
        return groceryListRepository.save(groceryList);
    }

    public GroceryList getGrocery(Long id) {
        return groceryListRepository.findById(id)
                .orElseThrow(() -> new GroceryListNotFoundException("Grocery list not found with id: " + id));
    }


    public GroceryList updateGrocery(Long id, GroceryList groceryList) {
        groceryList.setId(id);
        return groceryListRepository.save(groceryList);
    }

    public void deleteGrocery(Long id) {
        groceryListRepository.deleteById(id);

    }

}
