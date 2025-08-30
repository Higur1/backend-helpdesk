package io.github.higur.helpdesk.domain.mapping;

import io.github.higur.helpdesk.domain.Customer;
import io.github.higur.helpdesk.domain.Technician;
import io.github.higur.helpdesk.domain.Ticket;
import io.github.higur.helpdesk.domain.dtos.ticketDTO.TicketRequestDTO;
import io.github.higur.helpdesk.domain.dtos.ticketDTO.TicketResponseDTO;
import io.github.higur.helpdesk.domain.enums.Priority;
import io.github.higur.helpdesk.domain.enums.Status;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TicketMapper {

    public TicketResponseDTO toDto(Ticket ticket) {
        return new TicketResponseDTO(
                ticket.getId(),
                ticket.getCreatedAt(),
                ticket.getClosedAt(),
                Status.toEnum(ticket.getStatus()),
                ticket.getTitle(),
                ticket.getObservation(),
                ticket.getTechnician().getId(),
                ticket.getTechnician().getName(),
                ticket.getCustomer().getId(),
                ticket.getCustomer().getName(),
                Priority.toEnum(ticket.getPriority())
        );
    }

    public Ticket toEntity(TicketRequestDTO ticketRequestDTO, Customer customer, Technician technician) {
        return new Ticket(
                LocalDate.now(),
                null,
                ticketRequestDTO.getStatus().getCode(),
                ticketRequestDTO.getTitle(),
                ticketRequestDTO.getObservation(),
                technician,
                customer,
                ticketRequestDTO.getPriority().getCode()
        );
    }
}

