package it.polito.se2.g04.officequeuemanagement.Services;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
public class Service {
    public Service( String name, Duration serviceTime) {
        this.name = name;
        this.serviceTime = serviceTime;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private Duration serviceTime;
}
