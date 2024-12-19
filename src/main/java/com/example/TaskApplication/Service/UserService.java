package com.example.TaskApplication.Service;

import com.example.TaskApplication.Modal.User;
import com.example.TaskApplication.Security.JwtUtil;
import com.example.TaskApplication.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  @Autowired
  private UserRepo userRepo;


  public User addUser(String username, String password) {

    User user = new User();
    user.setUsername(username);
    user.setPassword(password);

    String token = JwtUtil.createToken(username);

    user.setJwtToken(token);

    return userRepo.save(user);
  }

}
