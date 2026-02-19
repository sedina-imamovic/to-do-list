package se.jimmy.iths.todolist.model;


import jakarta.persistence.*;

import java.sql.Date;
import java.time.LocalDate;


@Entity
public class Studyplan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String Task;
    private int priority;

    //Category
    private String coursename;

    private LocalDate startdate;
    private LocalDate deadline;

    public Studyplan(){}

    public Studyplan(String Task, int priority, String coursename, LocalDate startdate, LocalDate deadline){
        this.Task = Task;
        this.priority = priority;
        this.coursename = coursename;
        this.startdate = startdate;
        this.deadline = deadline;
    }

    //--- Getters ---

    public Long getId() {

        return id;
    }
    public String getTask(){
        return Task;
    }
    public int getPriority(){
        return priority;
    }
    public String getCoursename(){
        return coursename;
    }
    public LocalDate getStartdate(){
        return startdate;
    }
    public LocalDate getDeadline(){
        return deadline;
    }

    //--- Setters ---
    public void setId(Long id) {
        this.id = id;
    }
    public void setTask(String Task) {
        this.Task = Task;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }
    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }
    public void setStartdate(LocalDate startdate) {
        this.startdate = startdate;
    }
    public void setDeadline(LocalDate deadline) {this.deadline = deadline;}
}
