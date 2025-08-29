package io.github.higur.helpdesk.resources;

import io.github.higur.helpdesk.domain.dtos.TechnicianRequestDTO;
import io.github.higur.helpdesk.domain.dtos.TechnicianResponseDTO;
import io.github.higur.helpdesk.service.TechnicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(value = "/technicians")
public class TechnicianResources {

    @Autowired
    private TechnicianService technicianService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<TechnicianResponseDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok().body((technicianService.find(id)));
    }

    @GetMapping
    public ResponseEntity<List<TechnicianResponseDTO>> findAll() {
        return ResponseEntity.ok().body(technicianService.findAll());
    }

    @PostMapping
    public ResponseEntity<TechnicianResponseDTO> save(@RequestBody TechnicianRequestDTO technicianRequestDTO) {
        return ResponseEntity.created(
                        ServletUriComponentsBuilder
                                .fromCurrentRequest()
                                .path("/{id}")
                                .buildAndExpand(technicianService.save(technicianRequestDTO).getId()).toUri()).build();
    }
}
