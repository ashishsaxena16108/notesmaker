package com.keepnotes.notemaker.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.keepnotes.notemaker.entities.User;

public interface UserRepository extends JpaRepository<User,Integer> {
    @Query("select u from User u where u.email = :email")
    public User getUserbyEmail(@Param("email") String email);
   
}
