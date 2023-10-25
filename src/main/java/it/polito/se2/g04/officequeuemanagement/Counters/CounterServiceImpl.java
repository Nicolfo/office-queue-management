package it.polito.se2.g04.officequeuemanagement.Counters;

import it.polito.se2.g04.officequeuemanagement.Services.ServiceRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.List;

@Service
public class CounterServiceImpl implements CounterService{

    private final CounterRepository counterRepository;

    public CounterServiceImpl( CounterRepository counterRepository) {
        this.counterRepository = counterRepository;
    }

    public Counter getCounterById(UUID id){
        if(counterRepository.existsById(id))
            return counterRepository.getReferenceById(id);
        else
            throw new CounterNotFoundException("Cannot find a counter with this id");
    }

    @Override
    public List<CounterDTO> getAvailableCounters() {
        return counterRepository.findAll().stream().map(it->new CounterDTO(it.getId(),it.getName())).toList();
    }
}
