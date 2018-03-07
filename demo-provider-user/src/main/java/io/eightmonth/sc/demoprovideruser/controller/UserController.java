package io.eightmonth.sc.demoprovideruser.controller;

import io.eightmonth.sc.demoprovideruser.dao.UserRepository;
import io.eightmonth.sc.demoprovideruser.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id){
        return userRepository.findOne(id);
    }
}
