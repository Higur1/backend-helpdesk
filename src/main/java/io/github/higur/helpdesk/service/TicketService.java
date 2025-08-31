package io.github.higur.helpdesk.service;

import io.github.higur.helpdesk.domain.Customer;
import io.github.higur.helpdesk.domain.Technician;
import io.github.higur.helpdesk.domain.Ticket;
import io.github.higur.helpdesk.domain.dtos.ticketDTO.TicketRequestDTO;
import io.github.higur.helpdesk.domain.dtos.ticketDTO.TicketResponseDTO;
import io.github.higur.helpdesk.domain.enums.Status;
import io.github.higur.helpdesk.domain.mapping.TicketMapper;
import io.github.higur.helpdesk.repository.CustomerRepository;
import io.github.higur.helpdesk.repository.TechnicianRepository;
import io.github.higur.helpdesk.repository.TicketRepository;
import io.github.higur.helpdesk.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketMapper mapper;

    @Autowired
    private TechnicianRepository technicianRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public TicketResponseDTO findById(Integer id) {
        return mapper.toDto(ticketRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Ticket Not Found!")));
    }

    public List<TicketResponseDTO> findAll() {
        return ticketRepository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public TicketResponseDTO save(TicketRequestDTO ticketRequestDTO) {
        return mapper.toDto(ticketRepository.save((searchCustomerAndTechnician(ticketRequestDTO))));
    }

    public TicketResponseDTO update(Integer id, TicketRequestDTO ticketRequestDTO) {
        Ticket ticketById = ticketRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Ticket Not Found"));
        Ticket ticket = mapper.updateEntity(statusChange(ticketById, ticketRequestDTO), ticketRequestDTO);

        return mapper.toDto(ticketRepository.save(ticket));
    }
    private Ticket statusChange(Ticket ticketById, TicketRequestDTO ticketRequestDTO){
        if(!ticketById.getStatus().equals(ticketRequestDTO.getStatus().getCode())){
            ticketById.setClosedAt(ticketRequestDTO.getStatus() == Status.CLOSED ? LocalDate.now() : null);
        }
        return ticketById;
    }
    private Ticket searchCustomerAndTechnician(TicketRequestDTO ticketRequestDTO) {
        Customer customerById = customerRepository.findById(ticketRequestDTO.getCustomerId()).orElseThrow(() -> new ObjectNotFoundException("Customer Not Found"));
        Technician technicianById = technicianRepository.findById(ticketRequestDTO.getTechnicianId()).orElseThrow(() -> new ObjectNotFoundException("Technician Not Found"));

        return mapper.toEntity(ticketRequestDTO, customerById, technicianById);
    }
}
