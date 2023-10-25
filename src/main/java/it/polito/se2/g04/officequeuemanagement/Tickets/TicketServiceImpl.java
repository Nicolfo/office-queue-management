package it.polito.se2.g04.officequeuemanagement.Tickets;

import org.springframework.stereotype.Service;

import java.time.Duration;

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

<<<<<<< Updated upstream
=======
    @Override
    public TicketDTO3 callNextCustomer(Counter counter) {
        //prendere il ticket della coda più lunga
        List<Object[]> list=ticketRepository.getQueues();
        for(Object[] elem : list){
            List<it.polito.se2.g04.officequeuemanagement.Services.Service> list1=counter.getAssociated_services();
            ByteBuffer buffer = ByteBuffer.wrap((byte[]) elem[0]);

            // Create a UUID from the ByteBuffer
            long mostSignificantBits = buffer.getLong();
            long leastSignificantBits = buffer.getLong();
            UUID uuid = new UUID(mostSignificantBits, leastSignificantBits);


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

>>>>>>> Stashed changes
}
