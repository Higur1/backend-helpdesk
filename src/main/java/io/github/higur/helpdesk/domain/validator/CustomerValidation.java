package io.github.higur.helpdesk.domain.validator;

import io.github.higur.helpdesk.domain.Customer;
import io.github.higur.helpdesk.domain.enums.Status;
import io.github.higur.helpdesk.repository.CustomerRepository;
import io.github.higur.helpdesk.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerValidation {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public boolean cpfExists(Customer customer) {
        return personRepository
                .findByCpf(customer.getCpf())
                .filter(found -> customer.getId() == null || !customer.getId().equals(found.getId()))
                .isPresent();
    }

    public boolean emailExists(Customer customer) {
        return personRepository
                .findByEmail(customer.getEmail())
                .filter(found -> customer.getId() == null || !customer.getId().equals(found.getId()))
                .isPresent();
    }

    public boolean haveATicketOpenOrProceeding(Customer customer) {
        return customerRepository.findById(customer.getId())
                .map(found -> found.getTickets()
                        .stream()
                        .anyMatch(ticket -> ticket.getStatus().equals(Status.OPEN) || ticket.getStatus().equals(Status.PROCEEDING)))
                .orElse(false);
    }
}
