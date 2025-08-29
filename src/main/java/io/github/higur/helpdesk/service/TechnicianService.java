package io.github.higur.helpdesk.service;

import io.github.higur.helpdesk.domain.Technician;
import io.github.higur.helpdesk.repository.TechnicianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TechnicianService {
    @Autowired
    private TechnicianRepository technicianRepository;

    public Technician find(Integer id){
        Optional<Technician> obj = technicianRepository.findById(id);
        return obj.orElse(null);
    }
}
