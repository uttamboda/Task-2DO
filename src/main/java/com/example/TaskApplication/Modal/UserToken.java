package com.example.TaskApplication.Modal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "user_tokens")
@Data
public class UserToken {

  @Id
  private String username;

  private String token;

  public UserToken() {}

  public UserToken(String username, String token) {
    this.username = username;
    this.token = token;
  }
}
