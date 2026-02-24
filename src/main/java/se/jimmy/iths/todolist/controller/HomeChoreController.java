package se.jimmy.iths.todolist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.jimmy.iths.todolist.model.HomeChore;
import se.jimmy.iths.todolist.service.HomeChoreService;

@Controller
@RequestMapping("/homeChore")
public class HomeChoreController {

    private final HomeChoreService homeChoreService;

    public HomeChoreController(HomeChoreService homeChoreService) {
        this.homeChoreService = homeChoreService;
    }

    @GetMapping
    public String getAllHomeChores(Model model) {
        model.addAttribute("homeChores", homeChoreService.getAllChores());
        return "homeChore-list";
    }

    @GetMapping("/{id}")
    public String getHomeChore(@PathVariable Long id, Model model) {
        model.addAttribute("homeChore", homeChoreService.getChore(id));
        return "homeChore-detail";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("homeChore", new HomeChore());
        return "homeChore-create";
    }

    @PostMapping
    public String createHomeChore(@ModelAttribute HomeChore homeChore){
        homeChoreService.createChore(homeChore);
        return  "redirect:/homeChore";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model){
        model.addAttribute("homeChore", homeChoreService.getChore(id));
        return "homeChore-edit";
    }

    @PutMapping("/{id}")
    public String editHomeChore(@PathVariable Long id, @ModelAttribute HomeChore homeChore){
        homeChoreService.updateChore(id, homeChore);
        return "redirect:/homeChore";
    }

    @DeleteMapping("/{id}")
    public String deleteHomeChore(@PathVariable Long id){
        homeChoreService.deleteChore(id);
        return "redirect:/homeChore";
    }
}
