package it.polito.se2.g04.officequeuemanagement.Tickets;

import it.polito.se2.g04.officequeuemanagement.Counters.Counter;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;

    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public TicketDTO createTicket(it.polito.se2.g04.officequeuemanagement.Services.Service service) {
        Ticket toAdd = new Ticket(service, null, null);
        ticketRepository.save(toAdd);
        return new TicketDTO(toAdd.getId(), Duration.ofSeconds(10));  //add formula for estimated time
    }

    @Override
    public Long callNextCustomer(Counter counter) {
        //prendere il ticket della coda più lunga
        List<Ticket> list=ticketRepository.findAll();
        return list.get(0).getId();
    }

    @Override
    public Long ticketsServing() {
        //prendere il ticket della coda più lunga
        List<Long> list=ticketRepository.getServingTickets();
        return list;
    }

}
