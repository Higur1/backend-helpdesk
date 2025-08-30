package io.github.higur.helpdesk.domain.mapping;

import io.github.higur.helpdesk.domain.Technician;
import io.github.higur.helpdesk.domain.dtos.technicianDTO.TechnicianRequestDTO;
import io.github.higur.helpdesk.domain.dtos.technicianDTO.TechnicianResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class TechnicianMapper {
    public TechnicianResponseDTO toDTO(Technician technician) {
        return new TechnicianResponseDTO(
                technician.getId(),
                technician.getName(),
                technician.getCpf(),
                technician.getEmail(),
                technician.getProfiles(),
                technician.getCreatedAt()
        );
    }

    public Technician toEntity(TechnicianRequestDTO technicianRequestDTO) {
        return new Technician(
                technicianRequestDTO.getName(),
                technicianRequestDTO.getCpf(),
                technicianRequestDTO.getEmail(),
                technicianRequestDTO.getPassword(),
                technicianRequestDTO.getProfiles()
        );
    }
}
