package io.github.higur.helpdesk.service;

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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DBService {
    @Autowired
    private TechnicianRepository technicianRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TicketRepository ticketRepository;

    public void dbStart() {
        try {
            Technician tech1 = new Technician(null, "Higor", "606.884.650-48", "higor@mail.com", "123");
            Technician tech2 = new Technician(null, "Linus Torvalds", "923.837.480-56", "linus@mail.com", "123");
            Technician tech3 = new Technician(null, "Ada Lovelace", "294.949.250-92", "ada@mail.com", "123");
            Technician tech4 = new Technician(null, "Alan Turing", "041.491.560-77", "turing@mail.com", "123");
            Technician tech5 = new Technician(null, "Grace Hopper", "255.713.290-09", "hopper@mail.com", "123");
            Technician tech6 = new Technician(null, "Donald Knuth", "554.281.420-49", "knuth@mail.com", "123");
            Technician tech7 = new Technician(null, "Barbara Liskov", "745.169.690-17", "liskov@mail.com", "123");
            Technician tech8 = new Technician(null, "Guido van Rossum", "211.253.680-04", "guido@mail.com", "123");
            Technician tech9 = new Technician(null, "Ken Thompson", "477.266.140-99", "ken@mail.com", "123");
            Technician tech10 = new Technician(null, "Dennis Ritchie", "612.787.810-13", "dennis@mail.com", "123");
            Technician tech11 = new Technician(null, "Margaret Hamilton", "636.355.080-71", "hamilton@mail.com", "123");
            Technician tech12 = new Technician(null, "Richard Stallman", "257.190.020-06", "stallman@mail.com", "123");

            tech1.addProfile(Profile.ADMIN);

            Customer cust1 = new Customer(null, "Linus Torvalds", "913.702.850-27", "torvalds@mail.com", "123");
            Customer cust2 = new Customer(null, "Thomas Edison", "809.629.580-26", "edison@mail.com", "123");
            Customer cust3 = new Customer(null, "Charles Babbage", "380.555.980-10", "babbage@mail.com", "123");
            Customer cust4 = new Customer(null, "Katherine Johnson", "838.138.190-86", "johnson@mail.com", "123");
            Customer cust5 = new Customer(null, "Claude Shannon", "546.830.710-37", "shannon@mail.com", "123");
            Customer cust6 = new Customer(null, "John von Neumann", "489.623.430-88", "vonneumann@mail.com", "123");
            Customer cust7 = new Customer(null, "Steve Jobs", "523.524.050-25", "jobs@mail.com", "123");
            Customer cust8 = new Customer(null, "Bill Gates", "732.137.390-81", "gates@mail.com", "123");
            Customer cust9 = new Customer(null, "Elon Musk", "523.170.070-35", "musk@mail.com", "123");
            Customer cust10 = new Customer(null, "Mark Zuckerberg", "942.423.860-60", "zuck@mail.com", "123");
            Customer cust11 = new Customer(null, "Jeff Bezos", "477.415.740-60", "bezos@mail.com", "123");
            Customer cust12 = new Customer(null, "Nikola Tesla", "957.668.650-40", "tesla@mail.com", "123");

            Ticket tick1 = new Ticket(null, Status.OPEN, "ticket 01", "First ticket", tech1, cust1, Priority.MEDIUM);
            Ticket tick2 = new Ticket(null, Status.CLOSED, "ticket 02", "System not booting", tech2, cust2, Priority.HIGH);
            Ticket tick3 = new Ticket(null, Status.PROCEEDING, "ticket 03", "Password reset request", tech3, cust3, Priority.LOW);
            Ticket tick4 = new Ticket(null, Status.OPEN, "ticket 04", "Network connectivity issue", tech4, cust4, Priority.HIGH);
            Ticket tick5 = new Ticket(null, Status.CLOSED, "ticket 05", "Email configuration problem", tech5, cust5, Priority.MEDIUM);
            Ticket tick6 = new Ticket(null, Status.PROCEEDING, "ticket 06", "Printer not responding", tech6, cust6, Priority.LOW);
            Ticket tick7 = new Ticket(null, Status.OPEN, "ticket 07", "Database access denied", tech7, cust7, Priority.HIGH);
            Ticket tick8 = new Ticket(null, Status.CLOSED, "ticket 08", "Antivirus installation", tech8, cust8, Priority.LOW);
            Ticket tick9 = new Ticket(null, Status.PROCEEDING, "ticket 09", "Slow system performance", tech9, cust9, Priority.MEDIUM);
            Ticket tick10 = new Ticket(null, Status.OPEN, "ticket 10", "Software update request", tech10, cust10, Priority.HIGH);

            technicianRepository.saveAll(List.of(tech1, tech2, tech3, tech4, tech5, tech6, tech7, tech8, tech9, tech10, tech11, tech12));
            customerRepository.saveAll(List.of(cust1, cust2, cust3, cust4, cust5, cust6, cust7, cust8, cust9, cust10, cust11, cust12));
            ticketRepository.saveAll(List.of(tick1, tick2, tick3, tick4, tick5, tick6, tick7, tick8, tick9, tick10));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
