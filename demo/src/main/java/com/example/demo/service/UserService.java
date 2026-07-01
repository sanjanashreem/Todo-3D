package com.example.demo.service;

import com.example.demo.modules.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;

    public User createuser(User user){
        return userRepo.save(user);
    }

    public User getuserById(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new RuntimeException("user not found"));
    }

}
