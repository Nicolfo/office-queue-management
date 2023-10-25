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
}
