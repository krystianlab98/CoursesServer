package com.github.course.features.user.repo;

import com.github.course.features.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    boolean existsByUsername(String username);

    User findByEmail(String email);

    @Transactional
    void deleteByUsername(String username);

}
