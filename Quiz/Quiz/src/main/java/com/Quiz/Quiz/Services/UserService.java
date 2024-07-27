package com.Quiz.Quiz.Services;

import com.Quiz.Quiz.Models.User;
import com.Quiz.Quiz.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        user.setScore(0);
        return userRepository.save(user);
    }

    public User loginUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public User updateUserScore(Long userId, int score) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setScore(score);
            userRepository.save(user);
        }
        return user;
    }
}
