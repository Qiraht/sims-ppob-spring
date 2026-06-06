package com.qiraht.ppob_sims_spring.controller;

import com.qiraht.ppob_sims_spring.dto.ApiResponse;
import com.qiraht.ppob_sims_spring.dto.response.BannerResponse;
import com.qiraht.ppob_sims_spring.entity.Banner;
import com.qiraht.ppob_sims_spring.service.BannerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/banner")
@Tag(name = "2. Module Information")
public class BannerController {
    private final BannerService bannerService;

    public BannerController(BannerService bannerService) {
        this.bannerService = bannerService;
    };

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<List<BannerResponse>>> getBannerController() {
      List<BannerResponse> banners = bannerService.getAllBanners().stream()
              .map(b -> new BannerResponse(
              b.getName(),
              b.getImage(),
              b.getDescription()
      )).toList();

      return ResponseEntity.ok(ApiResponse.success( "success", banners));
    };
}
