package it.polito.se2.g04.officequeuemanagement.Tickets;

import it.polito.se2.g04.officequeuemanagement.Counters.Counter;
import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSInput;

import java.sql.Timestamp;
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
    public Ticket getNextTicketfromQueue(Counter counter){
        List<it.polito.se2.g04.officequeuemanagement.Services.Service> services=counter.getAssociated_services();
        int lenght=0;
        Ticket t=null;
        for(it.polito.se2.g04.officequeuemanagement.Services.Service s: services){
            List<Ticket> queue= ticketRepository.findTicketByServiceAndCounterEmptyOrderById(s);
            if(queue.size()>lenght){
                lenght=queue.size();
                t=queue.get(0);
            }
        }
        t.setCounter(counter);
        t.setServed_timestamp(new Timestamp(System.currentTimeMillis()));
        return t;
    }

}
