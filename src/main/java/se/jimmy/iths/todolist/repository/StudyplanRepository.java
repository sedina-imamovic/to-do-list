package se.jimmy.iths.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.jimmy.iths.todolist.model.Studyplan;

public interface StudyplanRepository extends JpaRepository<Studyplan, Long> {
}
