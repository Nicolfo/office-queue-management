package it.polito.se2.g04.officequeuemanagement.Counters;

import it.polito.se2.g04.officequeuemanagement.Services.ServiceRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CounterServiceImpl implements CounterService{

    private final CounterRepository counterRepository;

    public CounterServiceImpl( CounterRepository counterRepository) {
        this.counterRepository = counterRepository;
    }

    public Counter getCounterById(UUID id){
        return counterRepository.getReferenceById(id);
    }
}
