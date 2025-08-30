package io.github.higur.helpdesk.domain.dtos.customerDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.higur.helpdesk.domain.enums.Profile;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomerRequestDTO {
    @NotBlank(message = "name is required")
    protected String name;
    @NotBlank(message = "cpf is required")
    @CPF(message = "Invalid CPF")
    protected String cpf;
    @NotBlank(message = "email is required")
    @Email(message = "Invalid Email")
    protected String email;
    @NotBlank(message = "password is required")
    protected String password;

    protected Set<Profile> profiles = new HashSet<>();

    @JsonFormat(pattern = "MM/dd/yyyy")
    protected LocalDate createdAt;

    public CustomerRequestDTO() {
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
