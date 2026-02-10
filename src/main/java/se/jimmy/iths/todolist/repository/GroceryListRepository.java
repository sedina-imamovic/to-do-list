package se.jimmy.iths.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.jimmy.iths.todolist.model.GroceryList;

public interface GroceryListRepository extends JpaRepository<GroceryList, Long> {
}
