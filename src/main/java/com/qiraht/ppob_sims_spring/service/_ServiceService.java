package com.qiraht.ppob_sims_spring.service;

import com.qiraht.ppob_sims_spring.entity._Service;
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


}
