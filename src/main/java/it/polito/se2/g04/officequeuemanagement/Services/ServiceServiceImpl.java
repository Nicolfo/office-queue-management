package it.polito.se2.g04.officequeuemanagement.Services;

import jakarta.persistence.EntityNotFoundException;
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
        if (serviceRepository.existsById(id))
            return serviceRepository.findById(id).orElse(null);
        else
            throw new ServiceNotFoundException("Cannot find service with id "+ id);
    }

    @Override
    public List<it.polito.se2.g04.officequeuemanagement.Services.Service> getAllServices() {
        return serviceRepository.findAll();
    }
}
