package com.webapp.service.serviceimpl;

import com.webapp.service.ManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ManagementServiceImpl implements ManagementService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ManagementServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ResponseEntity<Void> getHealthParams(Object body) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Cache-Control", "no-cache, no-store, must-revalidate");
        httpHeaders.set("Pragma", "no-cache");
        httpHeaders.set("X-Content-Type-Options", "nosniff");

        if(body != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(httpHeaders).build();
        }

        try {
            jdbcTemplate.execute("SELECT 1");
            return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).headers(httpHeaders).build();
        }
    }

    @Override
    public ResponseEntity<Void> invalidMethod() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Cache-Control", "no-cache, no-store, must-revalidate");
        httpHeaders.set("Pragma", "no-cache");
        httpHeaders.set("X-Content-Type-Options", "nosniff");
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).headers(httpHeaders).build();
    }
}