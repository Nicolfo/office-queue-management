package it.polito.se2.g04.officequeuemanagement.Tickets;

import it.polito.se2.g04.officequeuemanagement.Services.Service;
import it.polito.se2.g04.officequeuemanagement.Services.ServiceService;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin
public class TicketController {

    private final TicketService ticketService;
    private final ServiceService serviceService;


    public TicketController(TicketService ticketService, ServiceService serviceService) {
        this.ticketService = ticketService;
        this.serviceService = serviceService;
    }

    @PostMapping("/API/tickets/createTicket/{serviceID}")
    @ResponseStatus(HttpStatus.CREATED)
    public TicketDTO getTicket(@PathVariable UUID serviceID){
        Service service = serviceService.getServiceById(serviceID);
        return ticketService.createTicket(service);
    }
}
