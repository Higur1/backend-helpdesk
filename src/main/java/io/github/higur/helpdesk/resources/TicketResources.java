package io.github.higur.helpdesk.resources;

import io.github.higur.helpdesk.domain.dtos.ticketDTO.TicketRequestDTO;
import io.github.higur.helpdesk.domain.dtos.ticketDTO.TicketResponseDTO;
import io.github.higur.helpdesk.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketResources {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> find(@PathVariable Integer id) {
        return ResponseEntity.ok().body(ticketService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<TicketResponseDTO>> findAll() {
        return ResponseEntity.ok().body(ticketService.findAll());
    }

    @PostMapping
    public ResponseEntity<TicketResponseDTO> save(@Valid @RequestBody TicketRequestDTO ticketRequestDTO) {
        TicketResponseDTO save = ticketService.save(ticketRequestDTO);
        return ResponseEntity.created(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(save.getId()).toUri()).body(save);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> update(@PathVariable Integer id, @Valid @RequestBody TicketRequestDTO ticketRequestDTO) {
        return ResponseEntity.ok().body(ticketService.update(id, ticketRequestDTO));
    }
}
