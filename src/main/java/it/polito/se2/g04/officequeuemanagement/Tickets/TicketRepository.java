package it.polito.se2.g04.officequeuemanagement.Tickets;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
    @Query("SELECT t FROM Ticket t")
    public List<Ticket> getServingTicket();

    
    @Query("SELECT DISTINCT ON (name) id 
    FROM ticket 
    WHERE counter IS NOT NULL
    GROUP BY counter, id, timestamp
    ORDER BY served_timestamp DESC"
    )

    public List<Long> getServingTickets();


}
