package com.webapp.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ManagementService {
    public ResponseEntity<Void> getHealthParams(Object body);
    public ResponseEntity<Void> invalidMethod();
}
