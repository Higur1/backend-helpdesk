package io.github.higur.helpdesk.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.higur.helpdesk.domain.enums.Profile;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Technician extends Person {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @OneToMany(mappedBy = "technician")
    private List<Ticket> tickets = new ArrayList<>();

    public Technician() {
        super();
        addProfile(Profile.TECHNICIAN);
    }

    public Technician(Integer id, String name, String cpf, String email, String password) {
        super(id, name, cpf, email, password);
        addProfile(Profile.TECHNICIAN);
    }

    public Technician(String name, String cpf, String email, String password) {
        super(name, cpf, email, password);
        addProfile(Profile.TECHNICIAN);
    }

    public Technician(String name, String cpf, String email, String password, Set<Integer> profiles) {
        super(name, cpf, email, password);

        if (!profiles.contains(Profile.TECHNICIAN.getCode())) {
            addProfile(Profile.TECHNICIAN);
        }
        profiles.forEach(this::addProfile);
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
