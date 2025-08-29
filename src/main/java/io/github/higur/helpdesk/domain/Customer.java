package io.github.higur.helpdesk.domain;

import io.github.higur.helpdesk.domain.enums.Profile;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer extends Person{
    @Serial
    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "customer")
    private List<Ticket> tickets = new ArrayList<>();

    public Customer() {
        super();
        addProfile(Profile.CUSTOMER);
    }

    public Customer(Integer id, String name, String cpf, String email, String password) {
        super(id, name, cpf, email, password);
        addProfile(Profile.CUSTOMER);
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
