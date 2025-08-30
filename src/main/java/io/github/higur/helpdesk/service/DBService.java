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

import java.time.LocalDate;
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
            List<Technician> techs = technicianRepository.saveAll(List.of(
                    new Technician(null, "Higor", "606.884.650-48", "higor@mail.com", "123"),
                    new Technician(null, "Linus Torvalds", "923.837.480-56", "linus@mail.com", "123"),
                    new Technician(null, "Ada Lovelace", "294.949.250-92", "ada@mail.com", "123"),
                    new Technician(null, "Alan Turing", "041.491.560-77", "turing@mail.com", "123"),
                    new Technician(null, "Grace Hopper", "255.713.290-09", "hopper@mail.com", "123"),
                    new Technician(null, "Donald Knuth", "554.281.420-49", "knuth@mail.com", "123"),
                    new Technician(null, "Barbara Liskov", "745.169.690-17", "liskov@mail.com", "123"),
                    new Technician(null, "Guido van Rossum", "211.253.680-04", "guido@mail.com", "123"),
                    new Technician(null, "Ken Thompson", "477.266.140-99", "ken@mail.com", "123"),
                    new Technician(null, "Dennis Ritchie", "612.787.810-13", "dennis@mail.com", "123"),
                    new Technician(null, "Margaret Hamilton", "636.355.080-71", "hamilton@mail.com", "123"),
                    new Technician(null, "Richard Stallman", "257.190.020-06", "stallman@mail.com", "123")
            ));

            List<Customer> custs = customerRepository.saveAll(List.of(
                    new Customer(null, "Linus Torvalds", "913.702.850-27", "torvalds@mail.com", "123"),
                    new Customer(null, "Thomas Edison", "809.629.580-26", "edison@mail.com", "123"),
                    new Customer(null, "Charles Babbage", "380.555.980-10", "babbage@mail.com", "123"),
                    new Customer(null, "Katherine Johnson", "838.138.190-86", "johnson@mail.com", "123"),
                    new Customer(null, "Claude Shannon", "546.830.710-37", "shannon@mail.com", "123"),
                    new Customer(null, "John von Neumann", "489.623.430-88", "vonneumann@mail.com", "123"),
                    new Customer(null, "Steve Jobs", "523.524.050-25", "jobs@mail.com", "123"),
                    new Customer(null, "Bill Gates", "732.137.390-81", "gates@mail.com", "123"),
                    new Customer(null, "Elon Musk", "523.170.070-35", "musk@mail.com", "123"),
                    new Customer(null, "Mark Zuckerberg", "942.423.860-60", "zuck@mail.com", "123"),
                    new Customer(null, "Jeff Bezos", "477.415.740-60", "bezos@mail.com", "123"),
                    new Customer(null, "Nikola Tesla", "957.668.650-40", "tesla@mail.com", "123")
            ));

            List<Ticket> tickets = List.of(
                    new Ticket(LocalDate.now().minusDays(1), null, Status.OPEN.getCode(),"ticket 01", "First ticket", techs.get(0), custs.get(0), Priority.MEDIUM.getCode()),
                    new Ticket(LocalDate.now().minusDays(2), LocalDate.now(), Status.CLOSED.getCode(), "ticket 02", "System not booting", techs.get(1), custs.get(1), Priority.HIGH.getCode()),
                    new Ticket(LocalDate.now().minusDays(3), null, Status.PROCEEDING.getCode(), "ticket 03", "Password reset request", techs.get(2), custs.get(2), Priority.LOW.getCode()),
                    new Ticket(LocalDate.now().minusDays(4), null, Status.OPEN.getCode(), "ticket 04", "Network connectivity issue", techs.get(3), custs.get(3), Priority.HIGH.getCode()),
                    new Ticket(LocalDate.now().minusDays(5), LocalDate.now(), Status.CLOSED.getCode(), "ticket 05", "Email configuration problem", techs.get(4), custs.get(4), Priority.MEDIUM.getCode()),
                    new Ticket(LocalDate.now().minusDays(2), null, Status.PROCEEDING.getCode(), "ticket 06", "Printer not responding", techs.get(5), custs.get(5), Priority.LOW.getCode()),
                    new Ticket(LocalDate.now().minusDays(3), null, Status.OPEN.getCode(), "ticket 07", "Database access denied", techs.get(6), custs.get(6), Priority.HIGH.getCode()),
                    new Ticket(LocalDate.now().minusDays(4), LocalDate.now(), Status.CLOSED.getCode(), "ticket 08", "Antivirus installation", techs.get(7), custs.get(7), Priority.LOW.getCode()),
                    new Ticket(LocalDate.now().minusDays(2), null, Status.PROCEEDING.getCode(), "ticket 09", "Slow system performance", techs.get(8), custs.get(8), Priority.MEDIUM.getCode()),
                    new Ticket(LocalDate.now().minusDays(1), null, Status.OPEN.getCode(), "ticket 10", "Software update request", techs.get(9), custs.get(9), Priority.HIGH.getCode())
            );

            ticketRepository.saveAll(tickets);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
