package com.example.TaskApplication.Modal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "app_user")
@Data
public class User {

  @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String username;

  private String password;

  private String jwtToken;

  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public User() {}
}

