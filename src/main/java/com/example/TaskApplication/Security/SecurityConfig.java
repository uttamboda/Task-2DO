package com.example.TaskApplication.Security;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

  @Bean
  public JwtUtil jwtUtil() {
    return new JwtUtil(); // Return a new instance of JwtUtil
  }

  // Register JwtAuthenticationFilter with JwtUtil
  @Bean
  public FilterRegistrationBean<JwtAuthenticationFilter> jwtAuthenticationFilter(JwtUtil jwtUtil) {
    FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();

    registrationBean.setFilter(new JwtAuthenticationFilter(jwtUtil));

    registrationBean.addUrlPatterns("/tasks/*");

    registrationBean.setOrder(1);

    return registrationBean;
  }
}
