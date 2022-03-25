package com.example.projis.controller;

import com.example.projis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class MainController {
    @Autowired private UserService serviceUsers;


    @GetMapping("")
    public String showHomePage(){
        return "login";
    }

    @PostMapping("/login")
    protected void loginPage(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {

        String username = request.getParameter("Username");
        String password = request.getParameter("Password");

        int status = serviceUsers.loginOnApp(username,password);
        if (status == -1){
            response.sendRedirect("http://localhost:8080/errorLogin");
        }
        if(status == 0){
            Integer id = serviceUsers.getIdFromEmail(username);
            response.sendRedirect("http://localhost:8080/employee/"+id);

        }
        if (status == 1){
            response.sendRedirect("http://localhost:8080/users");
        }
    }

    @GetMapping("/errorLogin")
    public String errorLogin(){
        return "error_login";
    }

}
