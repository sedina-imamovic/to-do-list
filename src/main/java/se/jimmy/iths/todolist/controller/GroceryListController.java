package se.jimmy.iths.todolist.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.jimmy.iths.todolist.model.GroceryList;
import se.jimmy.iths.todolist.service.GroceryListService;

@Controller
@RequestMapping("/groceries")
public class GroceryListController {

    private static final Logger logger = LoggerFactory.getLogger(GroceryListController.class);

    private final GroceryListService groceryListService;

    public GroceryListController(GroceryListService groceryListService) {
        this.groceryListService = groceryListService;
    }

    @GetMapping
    public String getAllGroceries(Model model) {
        logger.info("Get all groceries");
        model.addAttribute("groceries", groceryListService.getAllGroceries());
        return "groceries";
    }

    @GetMapping("/new")
    public String showCreateForm() {
        logger.info("Show create grocery form");
        return "create-grocery";
    }

    @PostMapping
    public String createGrocery(@ModelAttribute GroceryList groceryList) {
        logger.info("Create grocery: " + groceryList.getName());
        groceryListService.createGrocery(groceryList);
        return "redirect:/groceries";
    }

    @GetMapping("/{id}")
    public String getGrocery(@PathVariable Long id, Model model) {
        logger.info("Get grocery with id: " + id);
        model.addAttribute("grocery", groceryListService.getGrocery(id));
        return "grocery";
    }

    @PutMapping("/{id}")
    public String updateGrocery(@PathVariable Long id, @ModelAttribute GroceryList groceryList) {
        logger.info("Update grocery with id: " + id);
        groceryListService.updateGrocery(id, groceryList);
        return "redirect:/groceries";
    }

    @GetMapping("/{id}/edit")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        logger.info("Show edit form for grocery with id: " + id);
        model.addAttribute("grocery", groceryListService.getGrocery(id));
        return "edit-grocery";
    }

    @DeleteMapping("/{id}")
    public String deleteGrocery(@PathVariable Long id) {
        logger.info("Delete grocery with id: " + id);
        groceryListService.deleteGrocery(id);
        return "redirect:/groceries";
    }
}
