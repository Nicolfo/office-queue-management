package it.polito.se2.g04.officequeuemanagement.Services;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ServiceRepository extends JpaRepository<Service, UUID> {
}
