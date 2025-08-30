package io.github.higur.helpdesk.domain.validator;

import io.github.higur.helpdesk.domain.dtos.TechnicianRequestDTO;
import io.github.higur.helpdesk.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TechnicianValidator {

    @Autowired
    private PersonRepository personRepository;


    public boolean cpfExists(TechnicianRequestDTO technicianRequestDTO) {
        return personRepository.findByCpf(technicianRequestDTO.getCpf()).isPresent();
    }

    public boolean emailExists(TechnicianRequestDTO technicianRequestDTO) {
        return personRepository.findByEmail(technicianRequestDTO.getEmail()).isPresent();
    }
}
