package com.qiraht.ppob_sims_spring.service;

import com.qiraht.ppob_sims_spring.entity.Banner;
import com.qiraht.ppob_sims_spring.repository.BannerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerService {
    private final BannerRepository bannerRepository;

    public BannerService(BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }

    public List<Banner> getAllBanners() {
        return bannerRepository.findAll();
    }
}
