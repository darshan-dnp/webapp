package com.webapp.repository;

import com.webapp.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, UUID> {
    long countByAssignmentIDAndUserEmail(UUID assignmentID, String email);
}
