package com.qiraht.ppob_sims_spring.controller;

import com.qiraht.ppob_sims_spring.dto.ApiResponse;
import com.qiraht.ppob_sims_spring.dto.response._ServiceResponse;
import com.qiraht.ppob_sims_spring.service._ServiceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/service")
@Tag(name = "2. Module Information")
public class _ServiceController {
    private final _ServiceService _serviceService;

    public _ServiceController(_ServiceService _serviceService) {
        this._serviceService = _serviceService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<List<_ServiceResponse>>> getServiceController() {
        List<_ServiceResponse> data = _serviceService.getAllService().stream()
                .map(s -> new _ServiceResponse(
                        s.getCode(),
                        s.getName(),
                        s.getIcon(),
                        s.getTariff()
                )).toList();

        return ResponseEntity.ok(ApiResponse.success("success", data));
    }
}
