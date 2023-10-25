package it.polito.se2.g04.officequeuemanagement.Tickets;

import it.polito.se2.g04.officequeuemanagement.Services.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
    List<Ticket> findTicketByServiceAndCounterEmptyOrderById(Service s);
}
