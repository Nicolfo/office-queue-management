package it.polito.se2.g04.officequeuemanagement.Tickets;

import it.polito.se2.g04.officequeuemanagement.Counters.Counter;
import it.polito.se2.g04.officequeuemanagement.Services.Service;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;


@Entity
@Getter
@Setter
public class Ticket {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;

    public Ticket() {

    }

    public Ticket(Service service) {
        this.service = service;
    }
    public Ticket(Service service, Counter counter, Timestamp served_timestamp) {
        this.service = service;
        this.counter = counter;
        this.served_timestamp = served_timestamp;
    }

    @ManyToOne
    private Service service;
    @ManyToOne
    @Nullable
    private Counter counter;
    @Nullable
    private Timestamp served_timestamp;


}
