package se.jimmy.iths.todolist.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class HomeChores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String chore;
    private String room;
    private boolean isImportant;
    private int estimatedTime;

    public HomeChores() {}

    public HomeChores(String chore, String room, boolean isImportant, int estimatedTime) {
        this.chore = chore;
        this.room = room;
        this.isImportant = isImportant;
        this.estimatedTime = estimatedTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChore() {
        return chore;
    }

    public void setChore(String chore) {
        this.chore = chore;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public boolean isIsImportant() {
        return isImportant;
    }

    public void setIsImportant(boolean important) {
        this.isImportant = important;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
    }
}
