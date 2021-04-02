package com.github.course.features.user.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String email;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private boolean isEnabled;

    @ElementCollection(fetch = FetchType.EAGER)
    List<Role> roles;

    public User() {
    }


}
