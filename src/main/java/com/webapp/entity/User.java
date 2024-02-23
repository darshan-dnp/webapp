package com.webapp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "account")
@NoArgsConstructor
@Getter
@Setter
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int userID;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String emailID;

    @Column(name = "password")
    private String password;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="account_created", nullable = false, updatable = false)
    private Timestamp accountCreated;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="account_updated", nullable = false, updatable = false)
    private Timestamp accountUpdated;

    public User(String firstName, String lastName, String emailID, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailID = emailID;
        this.password = password;
    }
}
