package it.polito.se2.g04.officequeuemanagement.Counters;

import it.polito.se2.g04.officequeuemanagement.Services.Service;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
public class Counter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    @ManyToMany
    private List<Service> associated_services;
}
