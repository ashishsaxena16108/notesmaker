package com.keepnotes.notemaker.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.keepnotes.notemaker.dao.UserRepository;
import com.keepnotes.notemaker.entities.User;
import com.keepnotes.notemaker.helper.EmailService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ForgotController {
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;
    @RequestMapping("/forgot")
    public String emailForm(){
        
        return "forgotpassword/emailform";
    }
    @PostMapping("/send-otp")
    public String sendOtp(Model model,HttpSession session,@RequestParam("email")String email){
        Random random = new Random();
        int otp=random.nextInt(8999)+1000;
        String subject="Otp verification from NotesMaker";
        String message ="This is the OTP for email "+email+".OTP:"+otp;
        boolean f = emailService.sendEmail(subject, message, email);
        if(f==true){
            model.addAttribute("message", "OTP successfully sent");
            session.setAttribute("myotp", otp);
            session.setAttribute("email", email);
            return "forgotpassword/verify-otp";
        }
        else{
        model.addAttribute("message", "Please check your email");

        return "forgotpassword/emailform";
        }
    }
    @PostMapping("verify-otp")
    public String verifyOtp(Model model,@RequestParam("otp")int otp,HttpSession httpSession){
         int myOtp=(int)httpSession.getAttribute("myotp");
         httpSession.removeAttribute("myotp");
         String email=(String) httpSession.getAttribute("email");
        if(myOtp==otp){
            
            User user=this.userRepository.getUserbyEmail(email);
            if(user==null){
               model.addAttribute("message", "This email does not exist.");
               return "forgotpassword/emailform";
            }
            else{
            return  "forgotpassword/change_form";
            }
        }
        else{
            model.addAttribute("message","You have entered wrong otp");
            return "forgotpassword/verify-otp";
        }
    }
    @PostMapping("/change-password")
    public String changePassword(@RequestParam("newpassword")String newpassword,HttpSession session){
        String email=(String) session.getAttribute("email");
        User user = this.userRepository.getUserbyEmail(email);
        user.setPassword(this.bCryptPasswordEncoder.encode(newpassword));
        this.userRepository.save(user);
        return "redirect:/signin?change=password changed successfully..";
    }
}
