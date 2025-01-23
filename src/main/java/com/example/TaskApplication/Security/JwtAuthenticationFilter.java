package com.example.TaskApplication.Security;


import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class JwtAuthenticationFilter implements Filter {
  private JwtUtil jwtUtil;

  // Constructor that accepts JwtUtil as a dependency
  public JwtAuthenticationFilter(JwtUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    // No initialization needed
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;

    // Get the 'Authorization' header from the request
    String authorizationHeader = httpRequest.getHeader("Authorization");

    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      String token = authorizationHeader.substring(7); // Extract the token

      try {
        // Validate the token using the JwtUtil class
        String username = jwtUtil.parseToken(token);
        // Set the username in the request attributes for later use
        httpRequest.setAttribute("username", username);
      } catch (Exception e) {
        // If the token is invalid or expired, return 401 Unauthorized with more detailed error
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.setContentType("application/json");
        String errorMessage = "{\"error\": \"Invalid or expired token\", \"message\": \"" + e.getMessage() + "\"}";
        httpResponse.getWriter().write(errorMessage);
        return;
      }
    } else {
      // If token is missing, return 401 Unauthorized
      httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      httpResponse.setContentType("application/json");
      httpResponse.getWriter().write("{\"error\": \"Authorization token is required\"}");
      return;
    }

    // Continue with the request chain
    chain.doFilter(request, response);
  }

  @Override
  public void destroy() {
    // Cleanup if needed
  }
}
