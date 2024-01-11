package co.cstsd.todomanagement.repository;

import co.cstsd.todomanagement.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
