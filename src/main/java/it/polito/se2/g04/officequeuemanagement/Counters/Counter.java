package it.polito.se2.g04.officequeuemanagement.Counters;

import it.polito.se2.g04.officequeuemanagement.Services.Service;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor

public class Counter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;

    @ManyToMany
    private List<Service> associated_services;

    public Counter(String name, List<Service> associated_services) {
        this.name = name;
        this.associated_services = associated_services;
    }

}
