package com.github.course.features.authorize.controllers;

import com.github.course.core.security.jwt.JwtUtil;
import com.github.course.features.authorize.dtos.RequestDto;
import com.github.course.features.authorize.dtos.ResponseDto;
import com.github.course.features.user.User;
import com.github.course.features.user.UserAuthService;
import com.github.course.features.user.UserDao;
import com.github.course.features.user.exceptions.InvalidUserCredentialsException;
import com.github.course.features.user.role.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/web/api")
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    private UserAuthService userAuthService;

    @Autowired
    private UserDao userDao;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserAuthService userAuthService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userAuthService = userAuthService;
    }

    @PostMapping("/signin")
    public ResponseEntity<ResponseDto> generateJwtToken(@RequestBody RequestDto requestDto) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUsername(), requestDto.getPassword()));
        } catch (DisabledException e) {
            throw new DisabledException("User inactive");
        } catch (BadCredentialsException e) {
            new InvalidUserCredentialsException("Invalid Credentials");
        }
        UserDetails userDetails = userAuthService.loadUserByUsername(requestDto.getUsername());
        String username = userDetails.getUsername();
        String userpwd = userDetails.getPassword();
        Set<String> roles = userDetails.getAuthorities().stream().map(r -> r.getAuthority())
                .collect(Collectors.toSet());
        User user = new User();
        user.setUsername(username);
        user.setPassword(userpwd);
        user.setRoles(roles.stream().map(Role::new).collect(Collectors.toSet()));
        String token = jwtUtil.generateToken(user);
        return new ResponseEntity<ResponseDto>(new ResponseDto(token), HttpStatus.OK);
    }

    @GetMapping("/test")
    public Set<Role> geuser() {
        User user = userDao.findByUsername("user");
        return user.getRoles();
    }


}
