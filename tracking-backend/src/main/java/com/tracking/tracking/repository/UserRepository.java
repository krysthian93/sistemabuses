package com.tracking.tracking.repository;

import com.tracking.tracking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository("manageUser")
public interface UserRepository extends JpaRepository<User, Serializable> {
    public abstract User findByUsername(String username);
}
