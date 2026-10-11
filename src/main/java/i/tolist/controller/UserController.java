package i.tolist.controller;


import i.tolist.dto.request.EmailUpdateRequest;
import i.tolist.dto.request.PasswordUpdateRequest;
import i.tolist.dto.request.UserCreateRequest;
import i.tolist.dto.request.UserUpdateRequest;
import i.tolist.dto.response.UserResponse;
import i.tolist.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/users")

public class UserController {
    
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    UserResponse getUser(@PathVariable UUID id) {
        
        return userService.findById(id);
    }
    
    @GetMapping("/email/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    UserResponse getUserByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }
    
    @PostMapping("/create")
    UserResponse createUser(@RequestBody UserCreateRequest userCreateRequest, HttpServletRequest request) {
        
        return userService.create(userCreateRequest);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    UserResponse updateUser(@PathVariable UUID id, @RequestBody UserUpdateRequest userUpdateRequest) {
        return userService.update(id, userUpdateRequest);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.delete(id);
        
        
        return ResponseEntity.noContent().build();
    }
    
    @PatchMapping("/passwordUpdate")
    void updateUserPassword(@RequestBody PasswordUpdateRequest passwordUpdateRequest) {
       userService.updatePassword(passwordUpdateRequest);
    }
    
    @PatchMapping("/emailUpdate")
    void updateUserEmail(@RequestBody EmailUpdateRequest emailUpdateRequest) {
        userService.updateEmail(emailUpdateRequest);
    }
    
    


}
