package com.github.course.features.user.repo;

import com.github.course.features.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDao {

    UserRepository userRepository;

    @Autowired
    public UserDao(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    public User findByUsername(final String username) {
        return userRepository.findByUsername(username);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public void save(User user) {
        userRepository.save(user);
    }
}
