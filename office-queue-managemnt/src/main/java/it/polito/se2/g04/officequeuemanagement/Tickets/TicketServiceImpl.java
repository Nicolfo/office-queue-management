package it.polito.se2.g04.officequeuemanagement.Tickets;

import it.polito.se2.g04.officequeuemanagement.Counters.Counter;
import it.polito.se2.g04.officequeuemanagement.Counters.CounterDTO;
import it.polito.se2.g04.officequeuemanagement.Counters.CounterService;
import it.polito.se2.g04.officequeuemanagement.Services.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.Collection;

@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private CounterService counterService;

    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public TicketDTO createTicket(it.polito.se2.g04.officequeuemanagement.Services.Service service) {
        Ticket toAdd = new Ticket(service, null, null);
        Duration estimatedTime = getEstimatedTime(service);
        ticketRepository.save(toAdd);
        return new TicketDTO(toAdd.getId(), estimatedTime);
    }

    // Returns the currently estimated time for a specified service, in seconds
    private Duration getEstimatedTime(it.polito.se2.g04.officequeuemanagement.Services.Service service) {
        // Find all tickets not served yet for the service
        Collection<Ticket> tickets = ticketRepository.findAll();
        List<Ticket> waitingTickets = tickets.stream()
                .filter(t -> t.getService().equals(service))
                .filter(t -> t.getServed_timestamp() == null)
                .toList();
        Double nR = (double) waitingTickets.size();
        // Get the denominator
        Double sR = 0.0;
        // For each counter, check if it offers this service
        // If yes, add 1/#services to sR
        Collection<CounterDTO> counters = counterService.getAvailableCounters();
        for (CounterDTO counter : counters) {
            if (counter.getAssociatedServices().contains(service)) {
                sR += 1.0 / ((double) counter.getAssociatedServices().size());
            }
        }
        // If there are no counters that offer that service, throw an exception
        if (sR == 0.0) {
            throw new NoCounterForServiceException("No counter is available for the requested service.");
        }
        double time = (double) service.getServiceTime().getSeconds() * (0.5 + nR / sR);
        int seconds = (int) Math.round(time);
        return Duration.ofSeconds(seconds);
    }

    @Override
    public TicketDTO3 callNextCustomer(Counter counter) {
        //prendere il ticket della coda più lunga
        List<Object[]> list=ticketRepository.getQueues();
        for(Object[] elem : list){
            List<it.polito.se2.g04.officequeuemanagement.Services.Service> list1=counter.getAssociated_services();
            UUID uuid;
            try{
                uuid = (UUID) elem[0];
            }catch (Exception e){
                ByteBuffer buffer = ByteBuffer.wrap((byte[]) elem[0]);

                // Create a UUID from the ByteBuffer
                long mostSignificantBits = buffer.getLong();
                long leastSignificantBits = buffer.getLong();
                uuid = new UUID(mostSignificantBits, leastSignificantBits);
            }



            for (it.polito.se2.g04.officequeuemanagement.Services.Service service : list1) {
                if (service.getId().compareTo(uuid)==0) {
                    Ticket toManage=ticketRepository.getReferenceById((Long) elem[2]);
                    toManage.setCounter(counter);
                    toManage.setServed_timestamp(new Timestamp(System.currentTimeMillis()));
                    ticketRepository.save(toManage);
                    return new TicketDTO3(toManage.getId(), toManage.getService().getName());
                }
            }
        }
        throw new EmptyQueueException("The queue is empty");
    }

    @Override
    public List<TicketDTO2> ticketsServing() {
        //return a list of TicketDTO2, containing for each counter the last ticket served
        return ticketRepository.getServingTickets().stream().map(it->new TicketDTO2(it.getId(),it.getCounter().getId())).toList();
    }

}
