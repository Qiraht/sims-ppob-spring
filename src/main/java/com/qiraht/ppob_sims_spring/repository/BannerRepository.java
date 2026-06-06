package com.qiraht.ppob_sims_spring.repository;

import com.qiraht.ppob_sims_spring.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BannerRepository extends JpaRepository<Banner, UUID> {
}
