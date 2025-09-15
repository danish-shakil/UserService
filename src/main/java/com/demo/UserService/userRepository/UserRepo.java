package com.demo.UserService.userRepository;

import com.demo.UserService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    Optional<Object> findByUsername(String username);

}
