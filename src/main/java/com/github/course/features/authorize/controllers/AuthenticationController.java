package com.github.course.features.authorize.controllers;


import com.github.course.features.authorize.dtos.RequestDto;
import com.github.course.features.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping()
public class AuthenticationController {

    private UserService userService;

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/web/api/signin")
    public ResponseEntity<String> login(@RequestBody RequestDto requestDto) {
        try {
            String s = userService.signin(requestDto.getUsername(), requestDto.getPassword());
            return new ResponseEntity<>(s, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
