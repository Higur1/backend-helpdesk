package io.github.higur.helpdesk.service;

import io.github.higur.helpdesk.domain.Customer;
import io.github.higur.helpdesk.domain.Technician;
import io.github.higur.helpdesk.domain.dtos.customerDTO.CustomerRequestDTO;
import io.github.higur.helpdesk.domain.dtos.customerDTO.CustomerResponseDTO;
import io.github.higur.helpdesk.domain.enums.Profile;
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
        Customer oldCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Object Not Found!"));

        oldCustomer.setPassword(encodePasswordIfChanged(oldCustomer, customerRequestDTO));
        validateConflictsIfChanged(oldCustomer, customerRequestDTO);

        oldCustomer.getProfiles().clear();
        oldCustomer.getProfiles().addAll(customerRequestDTO.getProfiles().stream().map(Profile::toEnum).collect(Collectors.toSet()));

        mapper.updateFromDTO(oldCustomer, customerRequestDTO);

        return mapper.toDTO(customerRepository.save(oldCustomer));
    }

    private void validateConflictsIfChanged(Customer oldCustomer, CustomerRequestDTO customerRequestDTO) {
        boolean emailChanged = !oldCustomer.getEmail().equals(customerRequestDTO.getEmail());
        boolean cpfChanged = !oldCustomer.getCpf().equals(customerRequestDTO.getCpf());

        if(emailChanged || cpfChanged){
            List<String> conflicts = collectConflicts(mapper.toEntity(customerRequestDTO));
            if(!conflicts.isEmpty()){
                throw new DataIntegrityViolationException(
                        "Already exists: "+ String.join(" and ", conflicts)
                );
            }
        }
    }

    private String encodePasswordIfChanged(Customer oldCustomer, CustomerRequestDTO customerRequestDTO) {
        return oldCustomer.getPassword().equals(customerRequestDTO.getPassword())
                ? oldCustomer.getPassword()
                : passwordEncoder.encode(customerRequestDTO.getPassword());
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
