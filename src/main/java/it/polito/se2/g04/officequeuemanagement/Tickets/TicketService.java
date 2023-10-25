package it.polito.se2.g04.officequeuemanagement.Tickets;
import it.polito.se2.g04.officequeuemanagement.Services.Service;
public interface TicketService {
    TicketDTO createTicket(Service service);
<<<<<<< Updated upstream
=======
    TicketDTO3 callNextCustomer(Counter counter);
    List<TicketDTO2> ticketsServing();
>>>>>>> Stashed changes
}
