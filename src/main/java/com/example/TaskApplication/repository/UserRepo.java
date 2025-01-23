package com.example.TaskApplication.repository;

import com.example.TaskApplication.Modal.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,String> {
  User findByUsernameAndPassword(String username,String password);
}
