package com.webapp.service.serviceimpl;

import com.webapp.entity.Assignment;
import com.webapp.repository.AssignmentRepository;
import com.webapp.service.AssignmentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Override
    public ResponseEntity<Assignment> createAssignment(Assignment assignment) {
        Assignment savedAssignment = assignmentRepository.save(assignment);
        return new ResponseEntity<Assignment>(savedAssignment, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Object> getAllAssignments(String email) {
        List<Assignment> allAssignments = assignmentRepository.findAllByUserEmail(email);
        if(allAssignments == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Object>(allAssignments, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getAssignment(UUID assignmentID, String email) {
        Assignment assignment = assignmentRepository.findByAssignmentID(assignmentID);
        if(assignment == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        if(!assignment.getUserEmail().equals(email)){
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<Object>(assignment, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<Void> deleteAssignment(UUID assignmentID, String email) {
        Assignment assignment = assignmentRepository.findByAssignmentID(assignmentID);

        if(assignment == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        if(!assignment.getUserEmail().equals(email)){
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
        assignmentRepository.deleteByAssignmentID(assignmentID);

        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> updateAssignment(UUID assignmentID, Assignment assignment, String email) {
        Assignment savedAssignment = assignmentRepository.findByAssignmentID(assignmentID);
        if(savedAssignment == null){
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        if(!savedAssignment.getUserEmail().equals(email)){
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
        assignment.setAssignmentID(savedAssignment.getAssignmentID());
        assignment.setAssignmentUpdated(Timestamp.valueOf(LocalDateTime.now()));
        assignment.setUserEmail(savedAssignment.getUserEmail());
        assignmentRepository.save(assignment);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
