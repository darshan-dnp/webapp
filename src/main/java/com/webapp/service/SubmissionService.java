package com.webapp.service;

import com.webapp.entity.Assignment;
import com.webapp.entity.Submission;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface SubmissionService {
    ResponseEntity<Object> submitAssignment(Submission submission);
}
