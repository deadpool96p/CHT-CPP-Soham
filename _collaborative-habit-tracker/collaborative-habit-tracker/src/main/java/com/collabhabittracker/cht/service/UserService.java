package com.collabhabittracker.cht.service;

import com.collabhabittracker.cht.model.User;
import com.collabhabittracker.cht.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
    
    // FIXED: Remove username parameter since User entity doesn't have it
    public User createUser(String email, String password, String name) {
        User user = new User(email, password, name);
        return userRepository.save(user);
    }
}