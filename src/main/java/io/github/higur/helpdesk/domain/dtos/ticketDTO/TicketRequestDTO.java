package io.github.higur.helpdesk.domain.dtos.ticketDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.higur.helpdesk.domain.enums.Priority;
import io.github.higur.helpdesk.domain.enums.Status;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.time.LocalDate;

public class TicketRequestDTO {
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate createdAt = LocalDate.now();
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate closedAt;
    @NotNull(message = "Status is a required field")
    private Status status;
    @NotBlank(message = "Title is a required field")
    private String title;
    private String observation;
    @NotNull(message = "Technician id is a required field")
    private Integer technicianId;
    @NotNull(message = "Customer id is a required field")
    private Integer customerId;
    @NotNull(message = "Priority is a required field")
    private Priority priority;

    public TicketRequestDTO() {
        super();
    }

    public TicketRequestDTO(LocalDate createdAt, LocalDate closedAt, Status status, String title, String observation, Integer technicianId, Integer customerId, Priority priority) {
        this.createdAt = createdAt;
        this.closedAt = closedAt;
        this.status = status;
        this.title = title;
        this.observation = observation;
        this.technicianId = technicianId;
        this.customerId = customerId;
        this.priority = priority;
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

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}
