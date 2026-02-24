package se.jimmy.iths.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.jimmy.iths.todolist.model.HomeChore;

@Repository
public interface HomeChoreRepository extends JpaRepository<HomeChore, Long> {
}
