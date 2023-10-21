package it.polito.se2.g04.officequeuemanagement.Tickets;

import it.polito.se2.g04.officequeuemanagement.Counters.Counter;
import it.polito.se2.g04.officequeuemanagement.Services.Service;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;
    @OneToOne
    private Service service;
    @OneToOne
    @Nullable
    private Counter counter;
    @Nullable
    private Timestamp served_timestamp;
}
