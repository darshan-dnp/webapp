package com.webapp.service.serviceimpl;

import com.timgroup.statsd.StatsDClient;
import com.webapp.service.MetricsService;
import org.springframework.stereotype.Service;

@Service
public class MetricsServiceImpl implements MetricsService {
    private final StatsDClient statsDClient;

    public MetricsServiceImpl(StatsDClient statsDClient) {
        this.statsDClient = statsDClient;
    }

    public void incrementApiCallCounter(String apiName) {
        statsDClient.increment(apiName);
    }
}
