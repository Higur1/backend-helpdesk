package io.github.higur.helpdesk;

import io.github.higur.helpdesk.domain.Customer;
import io.github.higur.helpdesk.domain.Technician;
import io.github.higur.helpdesk.domain.Ticket;
import io.github.higur.helpdesk.domain.enums.Priority;
import io.github.higur.helpdesk.domain.enums.Profile;
import io.github.higur.helpdesk.domain.enums.Status;
import io.github.higur.helpdesk.repository.CustomerRepository;
import io.github.higur.helpdesk.repository.TechnicianRepository;
import io.github.higur.helpdesk.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class HelpdeskApplication implements CommandLineRunner {

    @Autowired
    private TechnicianRepository technicianRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TicketRepository ticketRepository;

    public static void main(String[] args) {
        SpringApplication.run(HelpdeskApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Technician tech1 = new Technician(null, "Higor", "606.884.650-48", "higor@mail.com", "123");
        tech1.addProfile(Profile.ADMIN);
        Customer cust1 = new Customer(null, "Linus Torvalds", "928.742.460-85", "torvalds@mail.com", "123");
        Ticket tick1 = new Ticket(null, Status.OPEN, "ticket 01", "first ticket",  tech1, cust1, Priority.MEDIUM);

        technicianRepository.saveAll(List.of(tech1));
        customerRepository.saveAll(List.of(cust1));
        ticketRepository.saveAll(List.of(tick1));
    }
}
