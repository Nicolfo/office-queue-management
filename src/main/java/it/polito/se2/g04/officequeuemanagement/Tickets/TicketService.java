package it.polito.se2.g04.officequeuemanagement.Tickets;
import it.polito.se2.g04.officequeuemanagement.Services.Service;
public interface TicketService {
    TicketDTO createTicket(Service service);
}
