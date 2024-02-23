package com.webapp.service.serviceimpl;

import com.webapp.entity.Assignment;
import com.webapp.entity.Submission;
import com.webapp.repository.AssignmentRepository;
import com.webapp.repository.SubmissionRepository;
import com.webapp.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;

@Service
public class SubmissionServiceImpl implements SubmissionService {

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private SNSServiceImpl snsService;

    @Override
    public ResponseEntity<Object> submitAssignment(Submission submission) {
        long submissionCount = submissionRepository.countByAssignmentIDAndUserEmail(submission.getAssignmentID(), submission.getUserEmail());
        Assignment assignment = assignmentRepository.findByAssignmentID(submission.getAssignmentID());

        if (assignment.getDeadline().before(Timestamp.from(Instant.now()))){
            return new ResponseEntity<>(new HashMap<>(), new HttpHeaders(), HttpStatus.FORBIDDEN);
        }

        if(submissionCount < assignment.getAttempts()){
            Submission savedAssignment = submissionRepository.save(submission);

            String json = "{\"url\":\"" + submission.getSubmissionUrl() + "\",\"email\":\"" + submission.getUserEmail() + "\"}";
            snsService.pubTopic(json);

            return new ResponseEntity<>(savedAssignment, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(new HashMap<>(), new HttpHeaders(), HttpStatus.FORBIDDEN);
    }
}
