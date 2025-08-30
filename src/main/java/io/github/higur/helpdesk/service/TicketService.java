package io.github.higur.helpdesk.service;

import io.github.higur.helpdesk.domain.Ticket;
import io.github.higur.helpdesk.domain.dtos.ticketDTO.TicketRequestDTO;
import io.github.higur.helpdesk.domain.dtos.ticketDTO.TicketResponseDTO;
import io.github.higur.helpdesk.domain.mapping.TicketMapper;
import io.github.higur.helpdesk.repository.TicketRepository;
import io.github.higur.helpdesk.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketMapper mapper;

    public TicketResponseDTO findById(Integer id){
        return mapper.toDto(ticketRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Ticket Not Found!")));
    }
    public List<TicketResponseDTO> findAll(){
        return ticketRepository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }
}
