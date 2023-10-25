package it.polito.se2.g04.officequeuemanagement.Tickets;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket,Long> {



    @Query(value = "SELECT t1.id, t1.counter_id, t1.served_timestamp, t1.service_id " +
            "FROM Ticket t1 " +
            "WHERE t1.counter_id IS NOT NULL " +
            "AND t1.served_timestamp = (" +
            "    SELECT MAX(t2.served_timestamp) " +
            "    FROM Ticket t2 " +
            "    WHERE t2.counter_id = t1.counter_id " +
            ")",nativeQuery = true)
    public List<Ticket> getServingTickets();


}
