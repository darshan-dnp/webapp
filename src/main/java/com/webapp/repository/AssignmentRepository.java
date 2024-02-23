package com.webapp.repository;

import com.webapp.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, UUID> {
    List<Assignment> findAllByUserEmail(String email);

    Assignment findByAssignmentID(UUID assignmentID);

    void deleteByAssignmentID(UUID assignmentID);
}
