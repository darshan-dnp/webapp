package com.webapp.service;

import org.springframework.stereotype.Service;

@Service
public interface MetricsService {
    void incrementApiCallCounter(String apiName);
}
