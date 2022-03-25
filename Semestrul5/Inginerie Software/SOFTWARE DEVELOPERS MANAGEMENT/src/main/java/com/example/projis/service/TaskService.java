package com.example.projis.service;

import com.example.projis.exceptions.TaskNotFoundException;
import com.example.projis.model.Task;
import com.example.projis.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired private TaskRepository repository;

    public List<Task> listAllByUserId(Integer userId){
        List<Task> list = new ArrayList<>();
        Iterable<Task> all = repository.findAll();
        for (Task task:all) {
            if(task.getIdUser() == userId){
                list.add(task);
            }
        }
        if(list.isEmpty()) {
            return null;
        }
        return list;
    }

    public void save(Task task) {
        repository.save(task);
    }
    public List<Task> listAll(){
        return (List<Task>) repository.findAll();
    }

    public Task getTaskById(Integer id) throws TaskNotFoundException {
        Optional<Task> result = repository.findById(id);
        if(result.isPresent()){
            return result.get();
        }
        throw new TaskNotFoundException("Task with id: "+id+" is not found!");
    }


    public void deleteTaskByID(Integer id) throws TaskNotFoundException {
        if(repository.existsById(id)) {
            repository.deleteById(id);
        }else{
            throw new TaskNotFoundException("Task with id: "+id+" is not found!");
        }
    }

    public Integer getUserIdByTaskID(int id) throws TaskNotFoundException {
        Optional<Task> byId = repository.findById(id);
        if(byId.isPresent()){
            return byId.get().getIdUser();
        }else{
            throw new TaskNotFoundException("Task with id: "+id+" is not found!");
        }
    }

}
