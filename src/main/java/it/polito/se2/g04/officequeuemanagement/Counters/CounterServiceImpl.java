package it.polito.se2.g04.officequeuemanagement.Counters;

@Service
public class CounterServiceImpl implements CounterService {
    private final CounterRepository counterRepository;

    public CounterServiceImpl(CounterRepository counterRepository) {
        this.counterRepository = counterRepository;
    }


    @Override
    public ArrayList<String> getAvailableCounters() {
        //prendere il ticket della coda più lunga
        ArrayList<String> list=counterRepository.findAll().getName();
        return list;
    }