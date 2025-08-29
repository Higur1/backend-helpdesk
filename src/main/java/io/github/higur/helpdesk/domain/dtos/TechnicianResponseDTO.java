package io.github.higur.helpdesk.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.higur.helpdesk.domain.Technician;
import io.github.higur.helpdesk.domain.enums.Profile;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public class TechnicianResponseDTO {

    protected Integer id;
    protected String name;
    protected String cpf;
    protected String email;
    protected String password;

    protected Set<Integer> profiles;

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
        this.password = obj.getPassword();
        this.profiles = obj.getProfiles().stream().map(x -> x.getCode()).collect(Collectors.toSet());
        this.createdAt = obj.getCreatedAt();
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Profile> getProfiles() {
        return profiles.stream().map(x -> Profile.toEnum(x)).collect(Collectors.toSet());
    }

    public void addProfile(Profile profile) {
        this.profiles.add(profile.getCode());
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}
