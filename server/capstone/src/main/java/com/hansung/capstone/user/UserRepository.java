package com.hansung.capstone.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByusername(String username);

    Optional<User> findByEmail(String email);
}
