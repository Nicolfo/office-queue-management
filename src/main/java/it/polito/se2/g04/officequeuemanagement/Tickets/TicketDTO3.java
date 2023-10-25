package it.polito.se2.g04.officequeuemanagement.Tickets;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Duration;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class TicketDTO3{
    private Long ticket_id;
    private String service_name;
}
