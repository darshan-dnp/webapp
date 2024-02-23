package com.webapp.service;

import com.webapp.entity.Assignment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface AssignmentService {
    ResponseEntity<Assignment> createAssignment(Assignment assignment);

    ResponseEntity<Object> getAllAssignments(String email);

    ResponseEntity<Object> getAssignment(UUID assignmentID, String name);

    ResponseEntity<Void> deleteAssignment(UUID assignmentID, String name);

    ResponseEntity<Void> updateAssignment(UUID assignmentID, Assignment assignment, String name);
}
