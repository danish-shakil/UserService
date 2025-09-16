package com.demo.UserService.controller;

import com.demo.UserService.exception.UserAlreadyExistsException;
import com.demo.UserService.exception.UserNotFoundException;
import com.demo.UserService.model.User;
import com.demo.UserService.service.UserService;
import com.demo.UserService.userRepository.UserRepo;
import com.demo.UserService.userRequests.UserDTO;
import com.demo.UserService.userResponse.UserResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepo userRepository;
    private final UserService userService;

    public UserController(UserRepo userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/home")
    public String home() {
        return "Welcome! You are logged in.";
    }

    @GetMapping("/getAllUsers")
    public List<UserResponseDTO> getAllUsers() {
         return userRepository.findAll().stream().map(this::convertToDTO).toList();
    }

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("UserName "+userDTO.getUsername()+ " already exists");
        }
        User user = userService.register(userDTO);
        return ResponseEntity.ok("User registered successfully.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User not found with the ID: "+id));

        return ResponseEntity.ok(convertToDTO(user));
    }

    private UserResponseDTO convertToDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        return dto;
    }
}
