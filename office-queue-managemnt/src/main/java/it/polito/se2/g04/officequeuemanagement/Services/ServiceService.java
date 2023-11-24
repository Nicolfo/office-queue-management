package it.polito.se2.g04.officequeuemanagement.Services;

import java.util.List;
import java.util.UUID;

public interface ServiceService {
    public Service getServiceById(UUID id);
    public List<Service> getAllServices();
}
