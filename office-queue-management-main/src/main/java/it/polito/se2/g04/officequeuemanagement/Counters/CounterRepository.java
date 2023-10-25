package it.polito.se2.g04.officequeuemanagement.Counters;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CounterRepository extends JpaRepository<Counter, UUID> {
}
