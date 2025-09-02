package io.github.higur.helpdesk.service;

import io.github.higur.helpdesk.domain.Customer;
import io.github.higur.helpdesk.domain.dtos.customerDTO.CustomerRequestDTO;
import io.github.higur.helpdesk.domain.dtos.customerDTO.CustomerResponseDTO;
import io.github.higur.helpdesk.domain.mapping.CustomerMapper;
import io.github.higur.helpdesk.domain.validator.CustomerValidation;
import io.github.higur.helpdesk.repository.CustomerRepository;
import io.github.higur.helpdesk.service.exceptions.DataIntegrityViolationException;
import io.github.higur.helpdesk.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper mapper;

    @Autowired
    private CustomerValidation validator;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public CustomerResponseDTO find(Integer id) {
        return new CustomerResponseDTO(
                customerRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Object Not Found!"))
        );
    }

    public List<CustomerResponseDTO> findAll() {
        return customerRepository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    public CustomerResponseDTO save(CustomerRequestDTO customerRequestDTO) {
        customerRequestDTO.setPassword(passwordEncoder.encode(customerRequestDTO.getPassword()));
        Customer customer = mapper.toEntity(customerRequestDTO);
        List<String> conflicts = collectConflicts(customer);
        if (!conflicts.isEmpty()) {
            throw new DataIntegrityViolationException("Already exists: " + String.join(" and ", conflicts));
        }
        return mapper.toDTO(customerRepository.save(customer));
    }

    public CustomerResponseDTO update(Integer id, CustomerRequestDTO customerRequestDTO) {
        customerRequestDTO.setPassword(passwordEncoder.encode(customerRequestDTO.getPassword()));
        Customer customer = mapper.toEntity(customerRequestDTO);
        customer.setId(id);

        List<String> conflicts = collectConflicts(customer);
        if (!conflicts.isEmpty()) {
            throw new DataIntegrityViolationException("Already exists: " + String.join(" and ", conflicts));
        }
        return mapper.toDTO(customerRepository.save(customer));
    }

    public void delete(Integer id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Object Not Found!"));
        if (validator.haveATicketOpenOrProceeding(customer)){
            throw new DataIntegrityViolationException("Is not possible delete customer with tickets OPEN or PROCEEDING");
        }
    }

    private List<String> collectConflicts(Customer customer) {
        return Stream.of(
                validator.cpfExists(customer) ? "CPF" : null,
                validator.emailExists(customer) ? "EMAIL" : null
        ).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
