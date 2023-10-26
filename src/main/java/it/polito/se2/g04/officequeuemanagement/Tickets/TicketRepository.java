package it.polito.se2.g04.officequeuemanagement.Tickets;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
    @Query(value = "SELECT s.id, COUNT(*) AS number_of_ticket, MIN(t.id) AS ticket_id, s.service_time " +
            "FROM ticket t " +
            "JOIN service s ON t.service_id = s.id " +
            "WHERE t.counter_id IS NULL " +
            "GROUP BY s.id, s.service_time " +
            "ORDER BY number_of_ticket DESC ,service_time ASC ", nativeQuery = true)
    public List<Object[]> getQueues();



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
