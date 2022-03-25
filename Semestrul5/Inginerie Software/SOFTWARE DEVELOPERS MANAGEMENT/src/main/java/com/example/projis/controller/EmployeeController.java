package com.example.projis.controller;

import com.example.projis.exceptions.TaskNotFoundException;
import com.example.projis.model.Task;
import com.example.projis.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class EmployeeController {
    @Autowired TaskService taskService;

    @GetMapping("/employee/{id}")
    public String getTaskByClientId(@PathVariable("id") Integer id, Model model){
        List<Task> listTasks = taskService.listAllByUserId(id);
        model.addAttribute("listTasks",listTasks);
        return  "task_employee";
    }

    @GetMapping("/employee/taskfinished/{id}")
    public String getTaskByClientId(@PathVariable("id") Integer id){
        try {
            Integer idUser = taskService.getUserIdByTaskID(id);
            taskService.deleteTaskByID(id);
            return "redirect:/employee/"+ idUser;
        } catch (TaskNotFoundException e) {
            return "error_task";
        }
    }

}
