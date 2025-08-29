package io.github.higur.helpdesk.resources;

import io.github.higur.helpdesk.domain.dtos.TechnicianResponseDTO;
import io.github.higur.helpdesk.service.TechnicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
