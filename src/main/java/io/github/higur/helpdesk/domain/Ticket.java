package io.github.higur.helpdesk.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.higur.helpdesk.domain.enums.Priority;
import io.github.higur.helpdesk.domain.enums.Status;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate createdAt;

    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate closedAt;

    private Integer priorityId;
    private Integer statusId;
    private String title;
    private String observation;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "technician_id")
    private Technician technician;

    public Ticket() {
    }

    public Ticket(Integer id, LocalDate createdAt, LocalDate closedAt, Status status, String title, String observation, Technician technician, Customer customer, Priority priority) {
        this.id = id;
        this.createdAt = createdAt;
        this.closedAt = closedAt;
        this.statusId = status.getCode();
        this.title = title;
        this.observation = observation;
        this.technician = technician;
        this.customer = customer;
        this.priorityId = priority.getCode();
    }

    public Ticket(LocalDate createdAt, LocalDate closedAt, Integer statusId, String title, String observation, Technician technician, Customer customer, Integer priorityId) {
        this.createdAt = createdAt;
        this.closedAt = closedAt;
        this.statusId = statusId;
        this.title = title;
        this.observation = observation;
        this.technician = technician;
        this.customer = customer;
        this.priorityId = priorityId;
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

    public Integer getPriority() {
        return priorityId;
    }

    public void setPriority(Integer priority) {
        this.priorityId = priority;
    }

    public Integer getStatus() {
        return statusId;
    }

    public void setStatus(Integer statusId) {
        this.statusId = statusId;
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
