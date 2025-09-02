package io.github.higur.helpdesk.service;

import io.github.higur.helpdesk.domain.Person;
import io.github.higur.helpdesk.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Person findByEmail(String email) {
        return personRepository.findByEmail(email).get();
    }
}
