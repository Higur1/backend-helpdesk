package io.github.higur.helpdesk.service;

import io.github.higur.helpdesk.domain.dtos.TechnicianRequestDTO;
import io.github.higur.helpdesk.domain.dtos.TechnicianResponseDTO;
import io.github.higur.helpdesk.domain.mapping.TechnicianMapper;
import io.github.higur.helpdesk.domain.validator.TechnicianValidator;
import io.github.higur.helpdesk.repository.TechnicianRepository;
import io.github.higur.helpdesk.service.exceptions.DataIntegrityViolationException;
import io.github.higur.helpdesk.service.exceptions.ObjectNotFoundException;
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
        List<String> conflicts =  collectConflicts(technicianRequestDTO);
        if (!conflicts.isEmpty()) {
            throw new DataIntegrityViolationException("Already exists: " + String.join(" and ", conflicts));
        }
        return mapper.toDTO(technicianRepository.save(mapper.toEntity(technicianRequestDTO)));
    }

    private List<String> collectConflicts(TechnicianRequestDTO technicianRequestDTO) {
        return Stream.of(
                validator.cpfExists(technicianRequestDTO) ? "CPF" : null,
                validator.emailExists(technicianRequestDTO) ? "EMAIL" : null
        ).filter(Objects::nonNull).toList();

    };
}

