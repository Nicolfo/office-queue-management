package it.polito.se2.g04.officequeuemanagement.Tickets;
import it.polito.se2.g04.officequeuemanagement.Tickets.Ticket;
import it.polito.se2.g04.officequeuemanagement.Services.Service;
public interface CounterService {
    ArrayList<String> getAvailableCounters();
}