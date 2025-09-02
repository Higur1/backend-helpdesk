package io.github.higur.helpdesk.service;

import io.github.higur.helpdesk.domain.Technician;
import io.github.higur.helpdesk.domain.dtos.technicianDTO.TechnicianRequestDTO;
import io.github.higur.helpdesk.domain.dtos.technicianDTO.TechnicianResponseDTO;
import io.github.higur.helpdesk.domain.mapping.TechnicianMapper;
import io.github.higur.helpdesk.domain.validator.TechnicianValidator;
import io.github.higur.helpdesk.repository.TechnicianRepository;
import io.github.higur.helpdesk.service.exceptions.DataIntegrityViolationException;
import io.github.higur.helpdesk.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

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
        technicianRequestDTO.setPassword(passwordEncoder.encode(technicianRequestDTO.getPassword()));
        Technician technician = mapper.toEntity(technicianRequestDTO);
        List<String> conflicts = collectConflicts(technician);
        if (!conflicts.isEmpty()) {
            throw new DataIntegrityViolationException("Already exists: " + String.join(" and ", conflicts));
        }
        return mapper.toDTO(technicianRepository.save(technician));
    }

    public TechnicianResponseDTO update(Integer id, TechnicianRequestDTO technicianRequestDTO) {
        technicianRequestDTO.setPassword(passwordEncoder.encode(technicianRequestDTO.getPassword()));
        Technician technician = mapper.toEntity(technicianRequestDTO);
        technician.setId(id);

        List<String> conflicts = collectConflicts(technician);
        if (!conflicts.isEmpty()) {
            throw new DataIntegrityViolationException("Already exists: " + String.join(" and ", conflicts));
        }

        return mapper.toDTO(technicianRepository.save(technician));
    }

    public void delete(Integer id) {
        Technician technician = technicianRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Object Not Found"));
        if (validator.haveATicketOpenOrProceeding(technician)){
            throw new DataIntegrityViolationException("Is not possible delete technician with tickets OPEN or PROCEEDING");
        }
        technicianRepository.delete(technician);
    }

    private List<String> collectConflicts(Technician technician) {
        return Stream.of(
                validator.cpfExists(technician) ? "CPF" : null,
                validator.emailExists(technician) ? "EMAIL" : null
        ).filter(Objects::nonNull).collect(Collectors.toList());
    }

    ;
}

