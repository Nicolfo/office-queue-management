package it.polito.se2.g04.officequeuemanagement.Counters;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class CounterController {

    private final CounterService counterService;

    public CounterController( CounterService counterService) {
        this.counterService =  counterService;
    }


    @GetMapping("/API/counters/countersAvailable")
    public List<CounterDTO> getAvailableCounters(){
        return counterService.getAvailableCounters();
    }

}