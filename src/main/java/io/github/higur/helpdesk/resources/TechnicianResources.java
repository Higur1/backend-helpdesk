package io.github.higur.helpdesk.resources;

import io.github.higur.helpdesk.domain.Technician;
import io.github.higur.helpdesk.service.TechnicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/technicians")
public class TechnicianResources {

    @Autowired
    private TechnicianService technicianService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Technician> findById(@PathVariable Integer id){
        return ResponseEntity.ok().body(technicianService.find(id));
    }
}
