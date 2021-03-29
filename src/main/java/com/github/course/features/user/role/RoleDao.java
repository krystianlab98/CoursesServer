package com.github.course.features.user.role;

import org.springframework.stereotype.Component;

@Component
public
class RoleDao {
    private final RoleRepository roleRepository;

    public RoleDao(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByRole(String role) {
        return roleRepository.findByRole(role);
    }

    public void save(Role role) {
        roleRepository.saveAndFlush(role);
    }

}
