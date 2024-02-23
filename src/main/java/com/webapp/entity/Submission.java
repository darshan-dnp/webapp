package com.webapp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "assignment_id")
    @JsonProperty("assignment_id")
    private UUID assignmentID;

    @Column(name = "submission_url")
    @JsonProperty("submission_url")
    private String submissionUrl;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="submission_date", nullable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Timestamp submissionDate;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="submission_updated", nullable = false, updatable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Timestamp submissionUpdated;

    @Column(name = "user_email")
    @JsonIgnore
    private String userEmail;

    public Submission(UUID id, UUID assignmentID, String submissionUrl) {
        this.id = id;
        this.assignmentID = assignmentID;
        this.submissionUrl = submissionUrl;
    }
}
