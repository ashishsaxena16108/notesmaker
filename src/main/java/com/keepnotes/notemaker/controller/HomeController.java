package com.keepnotes.notemaker.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.keepnotes.notemaker.dao.UserRepository;
import com.keepnotes.notemaker.entities.User;

@Controller
public class HomeController {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/")
    public String home(Model m){
        m.addAttribute("title", "Home-NoteMaker");
        return "home";
    }
     @GetMapping("/signup")
    public String signup(Model m){
        m.addAttribute("title", "SignUp-NoteMaker");
        m.addAttribute("user",new User());
        return "signup";
    }
    @PostMapping("/register")
    public String register(Model m,@ModelAttribute("user") User user){
       
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setImage("pngprofile.jpg");
        User result= this.userRepository.save(user);
        m.addAttribute("user",result);
        return "signin";
    }
    @GetMapping("/signin")
    public String signin(Model model){
        model.addAttribute("title", "SignIn-NoteMaker");
        return "signin";
    }
}
