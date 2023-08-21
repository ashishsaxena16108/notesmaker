package com.keepnotes.notemaker.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class Configure {
  @Bean
  public UserDetailsService getNoteService(){
    return new NoteService();
  }
  @Bean
  public BCryptPasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }
  @Bean
public DaoAuthenticationProvider customAuthenticationProvider() throws Exception{
  DaoAuthenticationProvider customAuthenticationProvider = new DaoAuthenticationProvider();
  customAuthenticationProvider.setUserDetailsService(this.getNoteService());
  customAuthenticationProvider.setPasswordEncoder(this.passwordEncoder());
  return customAuthenticationProvider;
}
  @Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
  http
    .authorizeHttpRequests(authorize->authorize
    .requestMatchers("/user/**").authenticated()
    .requestMatchers("/**").permitAll());
   
  http.formLogin(form->form.loginPage("/signin").defaultSuccessUrl("/user/dashboard").loginProcessingUrl("/dologin"));
  http.csrf(csrf->csrf.disable());
  http.authenticationProvider(customAuthenticationProvider());

  return http.build();
}
}
