package io.github.higur.helpdesk.repository;

import io.github.higur.helpdesk.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
