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
    public List<String> getAvailableCounters() {
        List<String> list = counterRepository.findAll().stream().map(it->it.getName()).toList();
        return list;
    }
}