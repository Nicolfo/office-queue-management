package it.polito.se2.g04.officequeuemanagement.Tickets;

import lombok.Getter;

import java.time.Duration;
@Getter
public class TicketDTO {
    private Long id;
    private Duration estimated_time;

    public TicketDTO(Long id, Duration estimatedTime) {
        this.id = id;
        estimated_time = estimatedTime;
    }
}
