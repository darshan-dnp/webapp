package com.webapp.controller;

import com.webapp.service.ManagementService;
import com.webapp.service.MetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/healthz")
@Cacheable(value = "managementCache", condition = "false")
public class ManagementController {

    ManagementService managementService;
    private MetricsService metricsService;

    @GetMapping
    public ResponseEntity<Void> getHealth(@RequestBody(required = false) Object body){
        metricsService.incrementApiCallCounter("get./healthz");
        return managementService.getHealthParams(body);
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS})
    public ResponseEntity<Void> invalidMethodHandler(){
        metricsService.incrementApiCallCounter("InvalidRequest");
        return managementService.invalidMethod();
    }
}
