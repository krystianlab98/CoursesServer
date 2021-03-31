package com.github.course.features.user.dto;

import com.github.course.features.user.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserResponseDto {

    List<Role> roles;
    private Long id;
    private String username;
    private String email;

}
