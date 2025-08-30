package io.github.higur.helpdesk.resources;

import io.github.higur.helpdesk.domain.dtos.ticketDTO.TicketResponseDTO;
import io.github.higur.helpdesk.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TickerResources {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> find(@PathVariable Integer id) {
        return ResponseEntity.ok().body(ticketService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<TicketResponseDTO>> findAll(){
        return ResponseEntity.ok().body(ticketService.findAll());
    }
}
