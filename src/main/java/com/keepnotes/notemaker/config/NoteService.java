package com.keepnotes.notemaker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.keepnotes.notemaker.dao.UserRepository;
import com.keepnotes.notemaker.entities.User;

public class NoteService implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserbyEmail(username);
        if(user==null)
        throw new UsernameNotFoundException("User not found!");
        NoteUserDetails noteUserDetails = new NoteUserDetails(user);
        return noteUserDetails;
    }
    
}
