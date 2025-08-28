package io.github.higur.helpdesk.repository;

import io.github.higur.helpdesk.domain.Technician;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechnicianRepository extends JpaRepository<Technician, Integer> {
}
