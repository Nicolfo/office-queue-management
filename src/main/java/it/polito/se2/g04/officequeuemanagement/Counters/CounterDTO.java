package it.polito.se2.g04.officequeuemanagement.Counters;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;
@AllArgsConstructor
@Getter
public class CounterDTO {
    private UUID id;
    private String name;
}
