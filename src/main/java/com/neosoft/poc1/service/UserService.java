package com.neosoft.poc1.service;

import com.neosoft.poc1.model.User;
import com.neosoft.poc1.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public User addUser(User user){
        return userRepo.save(user);
    }

    public User getUser(Integer id) {
        return userRepo.getById(id);
    }

    public User editUser(User user) {
        return userRepo.save(user);
    }
}
