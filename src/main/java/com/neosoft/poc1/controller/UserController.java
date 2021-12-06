package com.neosoft.poc1.controller;

import com.neosoft.poc1.model.User;
import com.neosoft.poc1.repo.UserRepo;
import com.neosoft.poc1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/addUser")
    public User addUser(@Valid @RequestBody User user){
        return userService.addUser(user);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Integer id){
        return userService.getUser(id);
    }

    @PutMapping("/{id}")
    public User editUser(@PathVariable Integer id, @RequestBody User user){
        return userService.editUser(user);
    }


}
