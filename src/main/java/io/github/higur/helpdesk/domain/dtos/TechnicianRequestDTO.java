package io.github.higur.helpdesk.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.higur.helpdesk.domain.Technician;
import io.github.higur.helpdesk.domain.enums.Profile;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public class TechnicianRequestDTO {
    protected String name;
    protected String cpf;
    protected String email;
    protected String password;

    protected Set<Profile> profiles;

    @JsonFormat(pattern = "MM/dd/yyyy")
    protected LocalDate createdAt;

    public TechnicianRequestDTO() {
        super();
    }

    public Technician TechnicianRequestDTO(TechnicianRequestDTO technicianRequestDTO) {
        return new Technician(
                null,
                technicianRequestDTO.name,
                technicianRequestDTO.cpf,
                technicianRequestDTO.email,
                technicianRequestDTO.password
        );
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Integer> getProfiles() {
        return profiles.stream().map(x -> x.getCode()).collect(Collectors.toSet());
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
