package io.github.higur.helpdesk.service;

import io.github.higur.helpdesk.domain.Technician;
import io.github.higur.helpdesk.domain.dtos.TechnicianRequestDTO;
import io.github.higur.helpdesk.domain.dtos.TechnicianResponseDTO;
import io.github.higur.helpdesk.domain.mapping.TechnicianMapper;
import io.github.higur.helpdesk.domain.validator.TechnicianValidator;
import io.github.higur.helpdesk.repository.TechnicianRepository;
import io.github.higur.helpdesk.service.exceptions.DataIntegrityViolationException;
import io.github.higur.helpdesk.service.exceptions.ObjectNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TechnicianService {
    @Autowired
    private TechnicianRepository technicianRepository;

    @Autowired
    private TechnicianMapper mapper;

    @Autowired
    private TechnicianValidator validator;

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
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public TechnicianResponseDTO save(TechnicianRequestDTO technicianRequestDTO) {
        Technician technician = mapper.toEntity(technicianRequestDTO);
        List<String> conflicts =  collectConflicts(technician);
        if (!conflicts.isEmpty()) {
            throw new DataIntegrityViolationException("Already exists: " + String.join(" and ", conflicts));
        }
        return mapper.toDTO(technicianRepository.save(technician));
    }

    public TechnicianResponseDTO update(Integer id, TechnicianRequestDTO technicianRequestDTO){
        Technician technician = mapper.toEntity(technicianRequestDTO);
        technician.setId(id);

        List<String> conflicts =  collectConflicts(technician);
        if (!conflicts.isEmpty()) {
            throw new DataIntegrityViolationException("Already exists: " + String.join(" and ", conflicts));
        }

        return mapper.toDTO(technicianRepository.save(technician));
    }

    private List<String> collectConflicts(Technician technician) {
        return Stream.of(
                validator.cpfExists(technician) ? "CPF" : null,
                validator.emailExists(technician) ? "EMAIL" : null
        ).filter(Objects::nonNull).toList();
    };
}

