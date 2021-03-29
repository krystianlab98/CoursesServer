package com.github.course.features.user;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserDto {

    private String username;
    private String email;
    private String password;
    private Set<String> roles;

    public UserDto() {
    }

    public UserDto(String username, String email, String password, Set<String> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }
}
