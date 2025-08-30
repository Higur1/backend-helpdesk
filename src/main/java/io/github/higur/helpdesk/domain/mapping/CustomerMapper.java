package io.github.higur.helpdesk.domain.mapping;

import io.github.higur.helpdesk.domain.Customer;
import io.github.higur.helpdesk.domain.dtos.customerDTO.CustomerRequestDTO;
import io.github.higur.helpdesk.domain.dtos.customerDTO.CustomerResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public CustomerResponseDTO toDTO(Customer customer) {
        return new CustomerResponseDTO(
                customer.getId(),
                customer.getName(),
                customer.getCpf(),
                customer.getEmail(),
                customer.getProfiles(),
                customer.getCreatedAt()
        );
    }

    public Customer toEntity(CustomerRequestDTO customerRequestDTO) {
        return new Customer(
                customerRequestDTO.getName(),
                customerRequestDTO.getCpf(),
                customerRequestDTO.getEmail(),
                customerRequestDTO.getPassword(),
                customerRequestDTO.getProfiles()
        );
    }
}
