package se.jimmy.iths.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.jimmy.iths.todolist.model.HomeChores;

@Repository
public interface HomeChoresRepository extends JpaRepository<HomeChores, Long> {
}
