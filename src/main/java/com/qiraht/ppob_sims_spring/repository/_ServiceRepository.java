package com.qiraht.ppob_sims_spring.repository;

import com.qiraht.ppob_sims_spring.entity._Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface _ServiceRepository extends JpaRepository<_Service, UUID> {
    Optional<_Service> findByCode(String code);
}
