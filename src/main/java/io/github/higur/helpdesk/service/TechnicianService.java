package io.github.higur.helpdesk.service;

import io.github.higur.helpdesk.domain.dtos.TechnicianResponseDTO;
import io.github.higur.helpdesk.repository.TechnicianRepository;
import io.github.higur.helpdesk.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TechnicianService {
    @Autowired
    private TechnicianRepository technicianRepository;

    public TechnicianResponseDTO find(Integer id) {
        return new TechnicianResponseDTO(
                technicianRepository.findById(id)
                        .orElseThrow(() -> new ObjectNotFoundException("Object Not Found!"))
        );
    }

    public List<TechnicianResponseDTO> findAll() {
        return technicianRepository
                .findAll()
                .stream()
                .map(TechnicianResponseDTO::new)
                .collect(Collectors.toList());
    }
}
