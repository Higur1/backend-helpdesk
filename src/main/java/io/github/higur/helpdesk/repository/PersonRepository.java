package io.github.higur.helpdesk.repository;

import io.github.higur.helpdesk.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> {
}
