package com.qiraht.ppob_sims_spring.service;

import com.qiraht.ppob_sims_spring.entity._Service;
import com.qiraht.ppob_sims_spring.enums._ServiceStatus;
import com.qiraht.ppob_sims_spring.exception.custom.ValidationException;
import com.qiraht.ppob_sims_spring.repository._ServiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class _ServiceService {
    private final _ServiceRepository _serviceRepository;

    public _ServiceService(_ServiceRepository _serviceRepository) {
        this._serviceRepository = _serviceRepository;
    }

    public List<_Service> getAllService() {
        return _serviceRepository.findAll();
    }

    public _Service getServiceByCode(String code) {
        _Service service = _serviceRepository.findByCode(code).orElseThrow(
                () -> new ValidationException("Service tidak ditemukan")
        );

        if (!service.getStatus().equals(_ServiceStatus.ACTIVE)) {
            throw new ValidationException("Service tidak aktif");
        }

        return service;
    }
}
