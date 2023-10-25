package it.polito.se2.g04.officequeuemanagement.Tickets;

import it.polito.se2.g04.officequeuemanagement.Counters.CounterService;
import it.polito.se2.g04.officequeuemanagement.Services.Service;
import it.polito.se2.g04.officequeuemanagement.Services.ServiceService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin
public class TicketController {

    private final TicketService ticketService;
    private final ServiceService serviceService;
    private final CounterService counterService;


    public TicketController(TicketService ticketService, ServiceService serviceService, CounterService counterService) {
        this.ticketService = ticketService;
        this.serviceService = serviceService;
        this.counterService = counterService;
    }

    @PostMapping("/API/tickets/createTicket/{serviceID}")
    @ResponseStatus(HttpStatus.CREATED)
    public TicketDTO getTicket(@PathVariable UUID serviceID){
        Service service = serviceService.getServiceById(serviceID);
        return ticketService.createTicket(service);
    }

    @PostMapping("/API/tickets/createTicket/")
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public TicketDTO getTicketWithNoPathVariable(){
        throw new CreateTicketWithNoPathVariable("Cant create a ticket without a correct path variable (it must be a UUID)");
    };
    @GetMapping("/API/tickets/serveNextTicket/{counterID}")
    public Long serveNextTicket(@PathVariable UUID counterID){
        //return 1l;
        return ticketService.callNextCustomer(counterService.getCounterById(counterID));
    }


}
