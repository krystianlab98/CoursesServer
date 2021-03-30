package com.github.course.features.user.dto;

import com.github.course.features.user.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class UserDataDto {

    private String username;
    private String email;
    private String password;
    private List<Role> roles;

    public UserDataDto() {
    }

    public UserDataDto(String username, String email, String password, List<Role> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }
}
