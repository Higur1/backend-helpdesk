package io.github.higur.helpdesk.domain;

import io.github.higur.helpdesk.domain.enums.Priority;
import io.github.higur.helpdesk.domain.enums.Status;

import java.time.LocalDate;
import java.util.Objects;

public class Ticket {
    private Integer id;
    private LocalDate createdAt = LocalDate.now();
    private LocalDate closedAt;
    private Priority priority;
    private Status status;
    private String title;
    private String observation;

    private Technician technician;
    private Customer customer;

    public Ticket() {
    }

    public Ticket(Integer id, Status status, String title, String observation, Technician technician, Customer customer, Priority priority) {
        this.id = id;
        this.status = status;
        this.title = title;
        this.observation = observation;
        this.technician = technician;
        this.customer = customer;
        this.priority = priority;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(LocalDate closedAt) {
        this.closedAt = closedAt;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Technician getTechnician() {
        return technician;
    }

    public void setTechnician(Technician technician) {
        this.technician = technician;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
