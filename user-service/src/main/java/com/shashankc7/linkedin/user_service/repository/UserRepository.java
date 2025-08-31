package com.shashankc7.linkedin.user_service.repository;

import com.shashankc7.linkedin.user_service.entitiy.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>
{
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
