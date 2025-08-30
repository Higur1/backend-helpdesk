package io.github.higur.helpdesk.domain.validator;

import io.github.higur.helpdesk.domain.Technician;
import io.github.higur.helpdesk.domain.dtos.TechnicianRequestDTO;
import io.github.higur.helpdesk.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TechnicianValidator {

    @Autowired
    private PersonRepository personRepository;

    public boolean cpfExists(Technician technician) {
        return personRepository
                .findByCpf(technician.getCpf())
                .filter(found -> technician.getId() == null || !technician.getId().equals(found.getId()))
                .isPresent();
    }
    public boolean emailExists(Technician technician) {
        return personRepository
                .findByEmail(technician.getEmail())
                .filter(found -> technician.getId() == null || !technician.getId().equals(found.getId()))
                .isPresent();
    }
}
