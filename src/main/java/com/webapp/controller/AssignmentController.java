package com.webapp.controller;

import com.webapp.entity.Assignment;
import com.webapp.service.AssignmentService;
import com.webapp.service.MetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class AssignmentController {

    private AssignmentService assignmentService;
    private MetricsService metricsService;

    @GetMapping("/v1/Assignments")
    public ResponseEntity<Object> getAllAssignment(@RequestBody(required = false) Object body, Principal principal){
        metricsService.incrementApiCallCounter("get./v1/Assignments");
        if(body != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return assignmentService.getAllAssignments(principal.getName());
    }

    @GetMapping("/v1/Assignments/{assignmentID}")
    public ResponseEntity<Object> getAssignment(@RequestBody(required = false) Object body, @PathVariable UUID assignmentID, Principal principal){
        metricsService.incrementApiCallCounter("get./v1/Assignments/{assignmentID}");
        if(body != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return assignmentService.getAssignment(assignmentID, principal.getName());
    }

    @PostMapping("/v1/Assignments")
    public ResponseEntity<Assignment> createAssignment(@RequestBody Assignment assignment, Principal principal){
        metricsService.incrementApiCallCounter("post./v1/Assignments");
        assignment.setUserEmail(principal.getName());
        return assignmentService.createAssignment(assignment);
    }

    @DeleteMapping("/v1/Assignments/{assignmentID}")
    public ResponseEntity<Void> deleteAssignment(@RequestBody(required = false) Object body, @PathVariable UUID assignmentID, Principal principal){
        metricsService.incrementApiCallCounter("delete./v1/Assignments/{assignmentID}");
        if(body != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return assignmentService.deleteAssignment(assignmentID, principal.getName());
    }

    @PutMapping("/v1/Assignments/{assignmentID}")
    public ResponseEntity<Void> updateAssignment(@RequestBody Assignment assignment, @PathVariable UUID assignmentID, Principal principal){
        metricsService.incrementApiCallCounter("put./v1/Assignments/{assignmentID}");
        if(assignment == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return assignmentService.updateAssignment(assignmentID, assignment, principal.getName());
    }

}
