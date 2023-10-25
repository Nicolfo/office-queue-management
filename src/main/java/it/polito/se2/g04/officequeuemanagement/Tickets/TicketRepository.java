package it.polito.se2.g04.officequeuemanagement.Tickets;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket,Long> {



    @Query(value="SELECT id "+
    "FROM ticket "+
    "WHERE counter_id IS NOT NULL "+
    "GROUP BY counter_id, id, served_timestamp "+
    "ORDER BY served_timestamp DESC",nativeQuery = true)
    public List<Long> getServingTickets();


}
