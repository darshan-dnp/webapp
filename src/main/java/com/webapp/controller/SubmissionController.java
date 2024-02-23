package com.webapp.controller;

import com.webapp.entity.Assignment;
import com.webapp.entity.Submission;
import com.webapp.service.MetricsService;
import com.webapp.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class SubmissionController {

    @Autowired
    private MetricsService metricsService;

    @Autowired
    private SubmissionService submissionService;

    @PostMapping("/v1/Assignments/{assignmentID}/submission")
    public ResponseEntity<Object> submitAssignment(@RequestBody Submission submission, @PathVariable UUID assignmentID, Principal principal){
        metricsService.incrementApiCallCounter("post./v1/Assignments/{assignmentID}/submission");
        submission.setAssignmentID(assignmentID);
        submission.setUserEmail(principal.getName());

        return submissionService.submitAssignment(submission);
    }
}
