package it.polito.se2.g04.officequeuemanagement.Counters;

import java.util.UUID;
import java.util.List;


public interface CounterService {
    public Counter getCounterById(UUID id);
   public List<CounterDTO> getAvailableCounters();
}
