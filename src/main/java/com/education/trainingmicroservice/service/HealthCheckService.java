package com.education.trainingmicroservice.service;

import com.education.trainingmicroservice.dto.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HealthCheckService {
    private static final Status STATUS_OK = new Status("OK");

    public Status getStatusOk() {
        return STATUS_OK;
    }
}
