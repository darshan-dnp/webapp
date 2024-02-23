package com.webapp.repository;

import com.webapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmailID(String emailID);
    boolean existsByEmailID(String emailID);
}
