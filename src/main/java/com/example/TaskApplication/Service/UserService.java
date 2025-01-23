package com.example.TaskApplication.Service;

import com.example.TaskApplication.Modal.User;
import com.example.TaskApplication.Modal.UserToken;
import com.example.TaskApplication.Security.JwtUtil;
import com.example.TaskApplication.repository.UserRepo;
import com.example.TaskApplication.repository.UserTokenRepo;
import org.apache.tomcat.util.json.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepo userRepo;

  @Autowired
  private UserTokenRepo userTokenRepo;

  @Autowired
  private JwtUtil jwtUtil;

  // Method to check credentials and generate JWT token
  public  String loginUser(String username, String password) {
    // Step 1: Check if user exists in the database by username and password
    User user = userRepo.findByUsernameAndPassword(username, password);

    // Step 2: If user exists and credentials are valid, generate JWT token
    if (user != null) {
      // Generate JWT token
      String token = jwtUtil.createToken(username);

      // Step 3: Create a UserToken object and save it to user_token table
      UserToken userToken = new UserToken(username, token);
      userTokenRepo.save(userToken);

      // Return the generated JWT token
      return token;
    } else {
      // If credentials are invalid, throw an exception
      throw new RuntimeException("Invalid username or password");
    }
  }
}
