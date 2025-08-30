package io.github.higur.helpdesk.domain.dtos.technicianDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.higur.helpdesk.domain.Technician;
import io.github.higur.helpdesk.domain.enums.Profile;

import java.time.LocalDate;
import java.util.Set;

public class TechnicianResponseDTO {

    protected Integer id;
    protected String name;
    protected String cpf;
    protected String email;
    protected String password;

    protected Set<Profile> profiles;

    @JsonFormat(pattern = "MM/dd/yyyy")
    protected LocalDate createdAt;

    public TechnicianResponseDTO() {
        super();
    }

    public TechnicianResponseDTO(Technician obj) {
        this.id = obj.getId();
        this.name = obj.getName();
        this.cpf = obj.getCpf();
        this.email = obj.getEmail();
        this.profiles = obj.getProfiles();
        this.createdAt = obj.getCreatedAt();
    }

    public TechnicianResponseDTO(Integer id, String name, String cpf, String email, Set<Profile> profiles, LocalDate createdAt) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.profiles = profiles;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Profile> getProfiles() {
        return profiles;
    }

    public void addProfile(Profile profile) {
        this.profiles.add(profile);
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}
