package se.jimmy.iths.todolist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.jimmy.iths.todolist.model.WorkoutSession;
import se.jimmy.iths.todolist.service.WorkoutSessionService;

@Controller
@RequestMapping("/workout")
public class WorkoutSessionController {

    private final WorkoutSessionService workoutSessionService;

    public WorkoutSessionController(WorkoutSessionService workoutSessionService) {
        this.workoutSessionService = workoutSessionService;
    }

    @GetMapping
    public String getAllWorkouts(Model model) {
        model.addAttribute("workoutSessions", workoutSessionService.getAllWorkoutSessions());
        return "workout-list";
    }

    @GetMapping("/{id}")
    public String showWorkoutDetail(@PathVariable Long id, Model model) {
        model.addAttribute("workoutSession", workoutSessionService.getById(id));
        return "workout-detail";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("workoutSession", new WorkoutSession());
        return "workout-create";
    }

    @PostMapping
    public String createWorkout(@ModelAttribute WorkoutSession workoutSession) {
        workoutSessionService.create(workoutSession);
        return "redirect:/workout";
    }

    @GetMapping("/{id}/edit")
    public String showUpdateForm(Model model, @PathVariable Long id) {
        model.addAttribute("workoutSession", workoutSessionService.getById(id));
        return "workout-update";
    }

    @PutMapping("/{id}")
    public String updateWorkoutById(@PathVariable Long id, @ModelAttribute WorkoutSession workoutSession) {
        workoutSessionService.update(workoutSession, id);
        return "redirect:/workout";
    }

    @DeleteMapping("/{id}")
    public String deleteWorkout(@PathVariable Long id) {
        workoutSessionService.delete(id);
        return "redirect:/workout";
    }
}
