package com.keepnotes.notemaker.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.keepnotes.notemaker.dao.NotesRepository;
import com.keepnotes.notemaker.dao.UserRepository;
import com.keepnotes.notemaker.entities.Notes;
import com.keepnotes.notemaker.entities.User;
import com.keepnotes.notemaker.helper.Message;



@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    NotesRepository notesRepository;
    @ModelAttribute
    public void common(Model model,Principal principal){
       User user = userRepository.getUserbyEmail(principal.getName());
       
        model.addAttribute("user", user);

    }
    @GetMapping("/dashboard")
    public String dashboard(Model model,Principal principal){
         model.addAttribute("title", "Dashboard-NotesMaker");
         List<Notes> notes= this.notesRepository.findNotesbyUserId(this.userRepository.getUserbyEmail(principal.getName()).getUser_id());
         model.addAttribute("notes", notes);
        return "user/dashboard";
    }
    @PostMapping("/new")
    public String newNote(@ModelAttribute Notes notes,Principal principal){
        User user = userRepository.getUserbyEmail(principal.getName());
        notes.setDate(Date.valueOf(LocalDate.now()));
        notes.setTime(Time.valueOf(LocalTime.now()));
        notes.setUser(user);
        user.getNotes().add(notes);
        userRepository.save(user);
        return "redirect:dashboard";
    }
    @GetMapping("/delete/{n_id}")
    public String deleteNote(@PathVariable("n_id") int n_id,Principal principal){
        Optional<Notes> notOption = this.notesRepository.findById(n_id);
        Notes note = notOption.get();
        User user = userRepository.getUserbyEmail(principal.getName());
        if(user.getUser_id() == note.getUser().getUser_id()){
            note.setUser(null);
            this.notesRepository.deleteById(n_id);
        }
        return "redirect:/user/dashboard";
    }
    @PostMapping("/update/{n_id}")
    public String updateNote(@PathVariable("n_id") int n_id,Principal principal,Model model){
        Optional<Notes> notOption = this.notesRepository.findById(n_id);
        Notes note = notOption.get();
        model.addAttribute("note", note);
        return "user/savenote";
    }
    @PostMapping("/save")
    public String saveNote(@ModelAttribute Notes note,Principal principal){
       
        User user = this.userRepository.getUserbyEmail(principal.getName());
        note.setDate(Date.valueOf(LocalDate.now()));
        note.setTime(Time.valueOf(LocalTime.now()));
        note.setUser(user);
        this.notesRepository.save(note);
        return "redirect:/user/dashboard";
    }
    @GetMapping("/profile")
    public String profile(Principal principal,Model model){
        model.addAttribute("title", "Profile-NotesMaker");
        return "user/profile";
    }
    @GetMapping("/update")
    public String updateProfile(Principal principal,Model model){
        User user = this.userRepository.getUserbyEmail(principal.getName());
        model.addAttribute("title", "Update Profile-NotesMaker");
        model.addAttribute("user", user);
        return "user/upprofile";
    }
    @PostMapping("/updateprofile")
    public String saveprofile(@ModelAttribute User user,Principal principal,@RequestParam("profileimage")MultipartFile file) throws IOException{

        User olduser = this.userRepository.getUserbyEmail(principal.getName());
        if(!file.isEmpty()){



            //update new photo
            File saveFile = new ClassPathResource("static/img").getFile();
            Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            user.setImage(file.getOriginalFilename());
        }
        else{
            user.setImage(olduser.getImage());
        }
        user.setNotes(olduser.getNotes());
        this.userRepository.save(user);
        return "redirect:/user/profile";
    }
    @GetMapping("/settings")
    public String setting(Model model){
       model.addAttribute("title", "Setting-NoteMaker");
       return "user/settings";
    }
    @PostMapping("/change-password")
    public String changepassword(Model model,@RequestParam("oldPassword")String oldPassword,@RequestParam("newPassword")String newPassword,Principal principal){
        User currentUser = this.userRepository.getUserbyEmail(principal.getName());
       
        if(this.bCryptPasswordEncoder.matches(oldPassword, currentUser.getPassword())){
            currentUser.setPassword(this.bCryptPasswordEncoder.encode(newPassword));
            this.userRepository.save(currentUser);
            
            model.addAttribute("message",new Message("Your password successfully changed","success"));
        }
        else{
           
           model.addAttribute("message",new Message("Please enter correct old password","danger"));
        }
        
        return "redirect:/user/settings";
    }
}
