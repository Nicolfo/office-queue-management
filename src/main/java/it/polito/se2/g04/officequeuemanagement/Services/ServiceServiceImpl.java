package it.polito.se2.g04.officequeuemanagement.Services;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ServiceServiceImpl implements ServiceService {
    private final ServiceRepository serviceRepository;

    public ServiceServiceImpl(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public it.polito.se2.g04.officequeuemanagement.Services.Service getServiceById(UUID id) {
        return serviceRepository.getReferenceById(id);
    }

    @Override
    public List<it.polito.se2.g04.officequeuemanagement.Services.Service> getAllServices() {
        return serviceRepository.findAll();
    }
}
