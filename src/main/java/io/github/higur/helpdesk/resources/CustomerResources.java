package io.github.higur.helpdesk.resources;

import io.github.higur.helpdesk.domain.dtos.customerDTO.CustomerRequestDTO;
import io.github.higur.helpdesk.domain.dtos.customerDTO.CustomerResponseDTO;
import io.github.higur.helpdesk.service.CustomerService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(value = "/customers")
public class CustomerResources {

    @Autowired
    private CustomerService customerService;

    @PreAuthorize("hasAnyRole('ADMIN', 'TECHNICIAN')")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(customerService.find(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'TECHNICIAN')")
    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> findAll() {
        return ResponseEntity.ok().body(customerService.findAll());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'TECHNICIAN')")
    @PostMapping
    public ResponseEntity<CustomerResponseDTO> save(@Valid @RequestBody CustomerRequestDTO customerRequestDTO) {
        CustomerResponseDTO save = customerService.save(customerRequestDTO);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(save.getId()).toUri()).body(save);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'TECHNICIAN')")
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> update(@PathVariable Integer id, @Valid @RequestBody CustomerRequestDTO customerRequestDTO) {
        return ResponseEntity.ok().body(customerService.update(id, customerRequestDTO));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'TECHNICIAN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
