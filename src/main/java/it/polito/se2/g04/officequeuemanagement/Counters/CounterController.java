package it.polito.se2.g04.officequeuemanagement.Counters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class CounterController {

    private final TicketService ticketService;
    private final ServiceService serviceService;
    private final CounterService counterService;

    public TicketController(TicketService ticketService, ServiceService serviceService, CounterService counterService) {
        this.ticketService = ticketService;
        this.serviceService = serviceService;
        this.counterService =  counterService;
    }


    @GetMapping("/API/counters/countersAvailable")
    public ArrayList<String> getAvailableCounters(){
        return CounterService.getAvailableCounters();
    }

}