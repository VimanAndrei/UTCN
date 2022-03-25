package com.example.projis.service;

import com.example.projis.exceptions.UserNotFoundException;
import com.example.projis.model.User;
import com.example.projis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired private UserRepository repo;

    public List<User> listAll(){
        return (List<User>) repo.findAll();
    }

    public void save(User user) {
        repo.save(user);
    }

    public int loginOnApp(String username, String password) {
        Iterable<User> users = repo.findAll();
        int id = -1;
        boolean isAdmin = false;
        for (User user: users ) {
            if (user.getEmail().equals(username) && user.getPassword().equals(password)) {
                id = user.getId();
                isAdmin = user.isEnable();
            }
        }
        if (id == -1){
            return -1;
        }else{
            if(isAdmin){
                return 1;

            }else{
                return 0;
            }
        }
    }

    public User getUserByID(Integer id) throws UserNotFoundException {
        Optional<User> result = repo.findById(id);
        if(result.isPresent()){
            return result.get();
        }
        throw new UserNotFoundException("User with id: "+id+" is not found!");
    }

    public void deleteUserByID(Integer id) throws UserNotFoundException {
        if(repo.existsById(id)) {
            repo.deleteById(id);
        }else{
            throw new UserNotFoundException("User with id: "+id+" is not found!");
        }
    }
    public boolean findUserByID(Integer id) throws  UserNotFoundException{
        Optional<User> byId = repo.findById(id);
        if( byId.isPresent()) {
            return true;
        }
        else{
            throw new UserNotFoundException("User with id: "+id+" is not found!");
        }

    }

    public Integer getIdFromEmail(String email){
        Iterable<User> users = repo.findAll();
        Integer id = 0;
        for (User user: users ) {
            if (user.getEmail().equals(email)) {
                id = user.getId();
            }
        }
        return id;
    }




}
