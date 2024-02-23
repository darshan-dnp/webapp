package com.webapp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    @JsonProperty("id")
    private UUID assignmentID;

    @NotNull
    @Column(name = "name")
    @JsonProperty("name")
    private String assignmentName;

    @Min(1)
    @Max(10)
    @NotNull
    private int points;

    @Column(name = "num_of_attemps")
    @JsonProperty("num_of_attemps")
    @NotNull
    private int attempts;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Timestamp deadline;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "assignment_created", nullable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Timestamp assignmentCreated;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "assignment_updated", nullable = false, updatable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Timestamp assignmentUpdated;

    @Column(name = "user_email")
    @JsonIgnore
    private String userEmail;

    public Assignment(String assignmentName, int points, int attempts, Timestamp deadline, String userEmail) {
        this.assignmentName = assignmentName;
        this.points = points;
        this.attempts = attempts;
        this.deadline = deadline;
        this.userEmail = userEmail;
    }
}
