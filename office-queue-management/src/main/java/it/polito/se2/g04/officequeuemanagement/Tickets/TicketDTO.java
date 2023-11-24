package it.polito.se2.g04.officequeuemanagement.Tickets;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Duration;
@Getter
@AllArgsConstructor
public class TicketDTO {
    private Long id;
    private Duration estimated_time;

}
