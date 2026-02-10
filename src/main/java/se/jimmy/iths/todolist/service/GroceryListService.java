package se.jimmy.iths.todolist.service;

import org.springframework.stereotype.Service;

@Service
public class GroceryListService {

    private final GroceryListRepository groceryListRepository;

    public GroceryListService(GroceryListRepository groceryListRepository) {
        this.groceryListRepository = groceryListRepository;
    }

    public GroceryList save(GroceryList groceryList) {
        return groceryListRepository.save(groceryList);
    }

    public List<GroceryList> findAll() {
        return groceryListRepository.findAll();
    }

    public GroceryList findById(Long id) {
        return groceryListRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Grocery list not found"));
    }

    public void delete(Long id) {
        groceryListRepository.delete(id);
    }

}
