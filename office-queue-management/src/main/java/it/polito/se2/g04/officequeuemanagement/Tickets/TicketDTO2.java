package it.polito.se2.g04.officequeuemanagement.Tickets;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Duration;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class TicketDTO2{
    private Long ticket_id;
    private UUID counter_id;


}
