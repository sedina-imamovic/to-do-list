package se.jimmy.iths.todolist.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/groceries")
public class GroceryListController {

    private final GroceryListService groceryListService;

    public GroceryListController(GroceryListService groceryListService) {
        this.groceryListService = groceryListService;
    }

    @GetMapping
    public String getAllGroceries(Model model) {
        model.addAttribute("groceries", groceryListService.getAllGroceries());
        return "groceries";
    }

    @GetMapping("/new")
    public String showCreateForm() {
        return "create-grocery";
    }

    @PostMapping
    public String createGrocery(@ModelAttribute GroceryList groceryList) {
        groceryListService.createGrocery(groceryList);
        return "redirect:/groceries";
    }

    @GetMapping("/{id}")
    public String getGrocery(@PathVariable Long id, Model model) {
        model.addAttribute("grocery", groceryListService.getGrocery(id));
        return "grocery";
    }

    @PutMapping("/{id}")
    public String updateGrocery(@PathVariable Long id, @ModelAttribute GroceryList groceryList) {
        groceryListService.updateGrocery(id, groceryList);
        return "redirect:/groceries";
    }

    @GetMapping("/{id}/edit")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        model.addAttribute("grocery", groceryListService.getGrocery(id));
        return "edit-grocery";
    }

    @DeleteMapping("/{id}")
    public String deleteGrocery(@PathVariable Long id) {
        groceryListService.deleteGrocery(id);
        return "redirect:/groceries";
    }
}
