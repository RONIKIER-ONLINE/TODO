package online.ronikier.todo.infrastructure.repository;

import online.ronikier.todo.domain.Person;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person, Long> {
    Optional<Person> findByUsername(String username);
    Optional<Person> findById(Long id);
}
