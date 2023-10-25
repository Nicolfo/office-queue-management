package it.polito.se2.g04.officequeuemanagement.Counters;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CounterServiceImpl implements CounterService {
    private final CounterRepository counterRepository;

    public CounterServiceImpl(CounterRepository counterRepository) {
        this.counterRepository = counterRepository;
    }


    @Override
    public List<CounterDTO> getAvailableCounters() {
        return counterRepository.findAll().stream().map(it->new CounterDTO(it.getId(),it.getName())).toList();
    }
}