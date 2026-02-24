package se.jimmy.iths.todolist.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.jimmy.iths.todolist.model.Studyplan;
import se.jimmy.iths.todolist.service.StudyplanService;

@Controller
@RequestMapping("/studyplan")
public class StudyplanController {
    private final StudyplanService studyplanService;

    public StudyplanController(StudyplanService studyplanService) {
        this.studyplanService = studyplanService;
    }
    //show all
    @GetMapping
    public String getAllStudyplans(Model model) {
        model.addAttribute("studyplans", studyplanService.getAllStudyplans());
        return "studyplan-list";
    }
    //show one
    @GetMapping("/{id}")
    public String getStudyplanById(@PathVariable Long id, Model model) {
        model.addAttribute("studyplan", studyplanService.getStudyplanById(id));
        return "studyplan-detail";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("studyplan", new Studyplan());
        return "studyplan-create";
    }
    @PostMapping
    public String createStudyplan(@ModelAttribute Studyplan studyplan) {
        studyplanService.createStudyplan(studyplan);
        return "redirect:/studyplan";
    }
    @GetMapping("/{id}/edit")
    public String showUpdateStudyplanForm(@PathVariable Long id, Model model) {
        model.addAttribute("studyplan", studyplanService.getStudyplanById(id));
        return "studyplan-update";
    }

    @PutMapping("/{id}")
    public String updateStudyplan(@PathVariable Long id, @ModelAttribute Studyplan studyplan) {
        studyplanService.updateStudyplan(studyplan,id);
        return "redirect:/studyplan";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteStudyplan(@PathVariable Long id) {
        studyplanService.deleteStudyplanById(id);
        return "redirect:/studyplan";
    }


}
