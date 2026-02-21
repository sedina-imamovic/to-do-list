package se.jimmy.iths.todolist.service;

import org.springframework.stereotype.Service;
import se.jimmy.iths.todolist.exceptions.HomeChoreNotFoundException;
import se.jimmy.iths.todolist.model.HomeChore;
import se.jimmy.iths.todolist.repository.HomeChoreRepository;
import se.jimmy.iths.todolist.validator.HomeChoreValidator;

import java.util.List;

@Service
public class HomeChoreService {
    private final HomeChoreRepository choresRepository;
    private final HomeChoreValidator choresValidator;

    public HomeChoreService(HomeChoreRepository choresRepository,
                            HomeChoreValidator choresValidator) {
        this.choresRepository = choresRepository;
        this.choresValidator = choresValidator;
    }

    //GET ALL
    public List<HomeChore> getAllChores() {
        return choresRepository.findAll();
    }

    //GET ONE
    public HomeChore getChore(Long id){
        return choresRepository.findById(id)
                .orElseThrow(() -> new HomeChoreNotFoundException("Not chore with id: " + id + " found."));
    }

    //POST
    public HomeChore createChore(HomeChore chore){
        choresValidator.validate(chore);
        return choresRepository.save(chore);
    }

    //PUT
    public HomeChore updateChore(Long id, HomeChore choreUpdate){
        HomeChore existing = choresRepository.findById(id)
                .orElseThrow(() -> new HomeChoreNotFoundException("Not chore with id: " + id + " found."));

        choresValidator.validate(choreUpdate);

        existing.setChore(choreUpdate.getChore());
        existing.setRoom(choreUpdate.getRoom());
        existing.setImportant(choreUpdate.isImportant());
        existing.setEstimatedTime(choreUpdate.getEstimatedTime());
        return choresRepository.save(existing);
    }

    //DELETE
    public void deleteChore(Long id){
        HomeChore exsiting = choresRepository.findById(id)
                .orElseThrow(() -> new HomeChoreNotFoundException("Not chore with id: " + id + " found."));

        choresRepository.delete(exsiting);
    }
}
