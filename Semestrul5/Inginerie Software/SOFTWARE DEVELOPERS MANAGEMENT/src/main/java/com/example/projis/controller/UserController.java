package com.example.projis.controller;

import com.example.projis.exceptions.UserNotFoundException;
import com.example.projis.model.User;
import com.example.projis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.List;

@Controller
public class UserController {
    @Autowired private UserService service;

    @GetMapping("/users")
    public String showUserList(Model model){
        List <User> listUsers = service.listAll();
        model.addAttribute("listUsers",listUsers);
       return  "users";
    }
    @GetMapping("/users/newUser")
    public String showNewForm(Model model){
        model.addAttribute("user",new User());
        model.addAttribute("pageTitle","Add New User");
        return "user_form";
    }
    @PostMapping("/users/save")
    public String saveUser(User user, RedirectAttributes redirectAttributes){
        service.save(user);
        redirectAttributes.addFlashAttribute("message","The user has been served successfuly !");
        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable("id") Integer id,Model model){
        try {
            User user = service.getUserByID(id);
            model.addAttribute("user",user);
            model.addAttribute("pageTitle","Edit User with ID: "+ id );
            return "user_form";
        } catch (UserNotFoundException e) {
            return "error_edit";
        }
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id){
        try {
            service.deleteUserByID(id);
            return "redirect:/users";
        } catch (UserNotFoundException e) {
            return "error_edit";
        }
    }

    @GetMapping("/intermediar/{id}")
    public String chatLoginUser(@PathVariable Integer id){
        try {
            User user = service.getUserByID(id);
            String nume = user.getLastName();
            String prenume = user.getFirstName();
            String chatUsername = nume + "_"+ prenume;
            return "redirect:/chat/"+chatUsername;
        } catch (UserNotFoundException e) {
            return "error_edit";
        }
    }
}
