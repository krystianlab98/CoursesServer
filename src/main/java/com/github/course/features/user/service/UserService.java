package com.github.course.features.user.service;

import com.github.course.core.security.jwt.JwtProvider;
import com.github.course.core.security.jwt.exceptions.CustomException;
import com.github.course.features.user.model.User;
import com.github.course.features.user.repo.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserService {

    UserDao userDao;
    PasswordEncoder passwordEncoder;
    JwtProvider jwtProvider;
    AuthenticationManager authenticationManager;

    @Autowired
    public UserService(UserDao userDao, PasswordEncoder passwordEncoder, JwtProvider jwtProvider, AuthenticationManager authenticationManager) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
    }

    public String signin(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return jwtProvider.generateToken(username, userDao.findByUsername(username).getRoles());
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public void delete(String username) {
        userDao.deleteByUsername(username);
    }

    public User search(String username) {
        User user = userDao.findByUsername(username);
        if (user == null) {
            throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
        }
        return user;
    }

    public User whoami(HttpServletRequest request) {
        return userDao.findByUsername(jwtProvider.getUsername(jwtProvider.resolveToken(request)));
    }

    public String refresh(String username) {
        return jwtProvider.generateToken(username, userDao.findByUsername(username).getRoles());
    }

    public String signup(User user) {
        if (!userDao.existsByUsername(user.getUsername())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userDao.save(user);
            return jwtProvider.generateToken(user.getUsername(), user.getRoles());
        } else {
            throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

}
