package se.jimmy.iths.todolist.service;


import org.springframework.stereotype.Service;
import se.jimmy.iths.todolist.exceptions.StudyplanNotFoundException;
import se.jimmy.iths.todolist.model.Studyplan;
import se.jimmy.iths.todolist.repository.StudyplanRepository;
import se.jimmy.iths.todolist.validator.StudyplanValidator;

import java.time.LocalDate;
import java.util.List;

@Service
public class StudyplanService {

    private final StudyplanRepository studyplanrepository;
    private final StudyplanValidator validator;

    public StudyplanService(StudyplanRepository studyrepo, StudyplanValidator validator) {
        this.studyplanrepository = studyrepo;
        this.validator = validator;
    }

    public List<Studyplan> getAllStudyplans() {
        return studyplanrepository.findAll();
    }

    public Studyplan getStudyplanById(Long id) {
        return studyplanrepository.findById(id).orElseThrow(() -> new StudyplanNotFoundException("Plan not found"));
    }

    public Studyplan createStudyplan(Studyplan studyplan) {
        if (studyplan.getStartdate() == null){
            studyplan.setStartdate(LocalDate.now()); //default date if nothing entered
        }
        validator.validate(studyplan);
        return studyplanrepository.save(studyplan);
    }

    public Studyplan updateStudyplan(Studyplan studyplan, Long id) {
        Studyplan studyplanupdate = getStudyplanById(id);

        studyplanupdate.setTask(studyplan.getTask());
        studyplanupdate.setPriority(studyplan.getPriority());
        studyplanupdate.setCoursename(studyplan.getCoursename());
        studyplanupdate.setStartdate(studyplan.getStartdate());
        studyplanupdate.setDeadline(studyplan.getDeadline());

        validator.validate(studyplanupdate);
        return studyplanrepository.save(studyplanupdate);
    }

    public void deleteStudyplanById(Long id) {
        getStudyplanById(id);
        studyplanrepository.deleteById(id);
    }

}
