package com.example.TaskApplication.repository;

import com.example.TaskApplication.Modal.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTokenRepo extends JpaRepository<UserToken, String> {
   UserToken findByUsername(String username);
}
