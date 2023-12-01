package it.polito.se2.g04.officequeuemanagement.Tickets;
import it.polito.se2.g04.officequeuemanagement.Counters.Counter;
import it.polito.se2.g04.officequeuemanagement.Services.Service;

import java.util.List;

public interface TicketService {
    TicketDTO createTicket(Service service);
    TicketDTO3 callNextCustomer(Counter counter);
    List<TicketDTO2> ticketsServing();
}
