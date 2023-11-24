package it.polito.se2.g04.officequeuemanagement.Counters;

import it.polito.se2.g04.officequeuemanagement.Services.Service;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;
@AllArgsConstructor
@Getter
public class CounterDTO {
    private UUID id;
    private String name;
    private List<Service> associatedServices;
}
