package io.github.higur.helpdesk.domain.dtos.ticketDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.higur.helpdesk.domain.enums.Priority;
import io.github.higur.helpdesk.domain.enums.Status;

import java.time.LocalDate;

public class TicketResponseDTO {
    private Integer id;
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate createdAt;
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate closedAt;
    private Priority priority;
    private Status status;
    private String title;
    private String observation;
    private Integer technicianId;
    private String technicianName;
    private Integer customerId;
    private String customerName;

    public TicketResponseDTO() {
    }

    public TicketResponseDTO(Integer id, LocalDate createdAt, LocalDate closedAt, Status status, String title, String observation, Integer technicianId, String technicianName, Integer customerId, String customerName, Priority priority) {
        this.id = id;
        this.createdAt = createdAt;
        this.closedAt = closedAt;
        this.status = status;
        this.title = title;
        this.observation = observation;
        this.technicianId = technicianId;
        this.technicianName = technicianName;
        this.customerId = customerId;
        this.customerName = customerName;
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

    public Integer getTechnicianId() {
        return technicianId;
    }

    public void setTechnicianId(Integer technicianId) {
        this.technicianId = technicianId;
    }

    public String getTechnicianName() {
        return technicianName;
    }

    public void setTechnicianName(String technicianName) {
        this.technicianName = technicianName;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
