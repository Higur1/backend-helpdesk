package io.github.higur.helpdesk.resources;

import io.github.higur.helpdesk.domain.dtos.technicianDTO.TechnicianRequestDTO;
import io.github.higur.helpdesk.domain.dtos.technicianDTO.TechnicianResponseDTO;
import io.github.higur.helpdesk.service.TechnicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
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

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TechnicianResponseDTO> save(@Valid @RequestBody TechnicianRequestDTO technicianRequestDTO) {
        TechnicianResponseDTO save = technicianService.save(technicianRequestDTO);
        return ResponseEntity.created(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(save.getId()).toUri()).body(save);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<TechnicianResponseDTO> update(@PathVariable Integer id, @Valid @RequestBody TechnicianRequestDTO technicianRequestDTO) {
        return ResponseEntity.ok().body(technicianService.update(id, technicianRequestDTO));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        technicianService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
