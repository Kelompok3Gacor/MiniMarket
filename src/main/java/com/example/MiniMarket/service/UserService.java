package com.example.MiniMarket.service;

import com.example.MiniMarket.model.User;
import com.example.MiniMarket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean register(String username, String password) {
        if (userRepository.findByUsername(username) != null) {
            return false;
        }

        User user = new User(username, password); 
        userRepository.save(user);
        return true;
    }

    public boolean login(String username, String password) {
        User user = userRepository.findByUsername(username);
        return user != null && user.getPassword().equals(password);
    }
}
