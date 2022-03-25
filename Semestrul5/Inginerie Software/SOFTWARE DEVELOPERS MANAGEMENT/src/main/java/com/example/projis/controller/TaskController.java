package com.example.projis.controller;

import com.example.projis.exceptions.TaskNotFoundException;
import com.example.projis.exceptions.UserNotFoundException;
import com.example.projis.model.Task;
import com.example.projis.service.TaskService;
import com.example.projis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class TaskController {
    @Autowired private UserService serviceUsers;
    @Autowired private TaskService serviceTask;

    @GetMapping("/users/addtask")
    public String addTaskToUser(){

        return "add_task";
    }
    @PostMapping("/users/addtask/finish")
    public String addTask(HttpServletRequest request,
                          HttpServletResponse response){


        try {
            Integer userid;
            userid = Integer.parseInt(request.getParameter("userid"));
            String task = request.getParameter("task");
            if (serviceUsers.findUserByID(userid)){
                Task task1 = new Task();
                task1.setIdUser(userid);
                task1.setTaskName(task);
                serviceTask.save(task1);
                return "redirect:/users";
            }

        } catch (NumberFormatException | UserNotFoundException e) {
            return "error_edit";
        }

        return null;
    }

    @GetMapping("/users/viewtask")
    public String showUserList(Model model){
        List<Task> listTasks = serviceTask.listAll();
        model.addAttribute("listTasks",listTasks);
        return  "tasks";
    }

    @GetMapping("/users/viewtask/edit/{id}")
    public String editUser(@PathVariable("id") Integer id, Model model){
        try {
            Task task = serviceTask.getTaskById(id);
            model.addAttribute("task",task);
            model.addAttribute("pageTitle","Edit Task with ID: "+ id );
            return "task_form";
        } catch (TaskNotFoundException  e) {
            return "error_task";
        }
    }

    @PostMapping("/users/viewtask/save")
    public String saveUser(Task task, RedirectAttributes redirectAttributes){
        serviceTask.save(task);
        redirectAttributes.addFlashAttribute("message","The task has been served successfuly !");
        return "redirect:/users/viewtask";
    }

    @GetMapping("/users/viewtask/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id){
        try {
            serviceTask.deleteTaskByID(id);
            return "redirect:/users/viewtask";
        } catch (TaskNotFoundException e) {
            return "error_task";
        }
    }
}
