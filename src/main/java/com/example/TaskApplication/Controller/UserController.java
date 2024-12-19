package com.example.TaskApplication.Controller;

import com.example.TaskApplication.Modal.User;
import com.example.TaskApplication.Security.JwtUtil;
import com.example.TaskApplication.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @Autowired
  UserService userService;

  @PostMapping("/Login")
  public String login(@RequestBody User user) {
    User existingUser = userService.addUser(user.getUsername(), user.getPassword());

    if (existingUser != null) {
      return JwtUtil.createToken(user.getUsername());
    } else {
      throw new RuntimeException("Invalid username or password");
    }
  }

}
