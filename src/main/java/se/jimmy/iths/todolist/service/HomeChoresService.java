package se.jimmy.iths.todolist.service;

import org.springframework.stereotype.Service;
import se.jimmy.iths.todolist.exceptions.HomeChoresNotFoundException;
import se.jimmy.iths.todolist.model.HomeChores;
import se.jimmy.iths.todolist.repository.HomeChoresRepository;
import se.jimmy.iths.todolist.validator.HomeChoresValidator;

import java.util.List;

@Service
public class HomeChoresService {
    private final HomeChoresRepository choresRepository;
    private final HomeChoresValidator choresValidator;

    public HomeChoresService(HomeChoresRepository choresRepository,
                             HomeChoresValidator choresValidator) {
        this.choresRepository = choresRepository;
        this.choresValidator = choresValidator;
    }

    //GET ALL
    public List<HomeChores> getAllChores() {
        return choresRepository.findAll();
    }

    //GET ONE
    public HomeChores getChore(Long id){
        return choresRepository.findById(id)
                .orElseThrow(() -> new HomeChoresNotFoundException("Not chore with id: " + id + " found."));
    }

    //POST
    public HomeChores createChore(HomeChores chore){
        choresValidator.validate(chore);
        return choresRepository.save(chore);
    }

    //PUT
    public HomeChores updateChore(Long id, HomeChores choreUpdate){
        HomeChores existing = choresRepository.findById(id)
                .orElseThrow(() -> new HomeChoresNotFoundException("Not chore with id: " + id + " found."));

        choresValidator.validate(choreUpdate);

        existing.setChore(choreUpdate.getChore());
        existing.setRoom(choreUpdate.getRoom());
        existing.setImportant(choreUpdate.isImportant());
        existing.setEstimatedTime(choreUpdate.getEstimatedTime());
        return choresRepository.save(existing);
    }

    //DELETE
    public void deleteChore(Long id){
        HomeChores exsiting = choresRepository.findById(id)
                .orElseThrow(() -> new HomeChoresNotFoundException("Not chore with id: " + id + " found."));

        choresRepository.delete(exsiting);
    }
}
