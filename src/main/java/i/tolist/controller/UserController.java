package i.tolist.controller;


import i.tolist.dto.request.UserCreateRequest;
import i.tolist.dto.response.UserResponse;
import i.tolist.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
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
    
    @GetMapping("/email")
    @PreAuthorize("hasRole('ADMIN')")
    UserResponse getUserByEmail(@RequestParam String email) {
        return userService.findByEmail(email);
    }
    
    @PostMapping("/create")
    UserResponse createUser(@RequestBody UserCreateRequest userCreateRequest, HttpServletRequest request) {
        
        return userService.create(userCreateRequest);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    UserResponse updateUser(@PathVariable UUID id, @RequestBody UserCreateRequest userCreateRequest) {
        return userService.update(id, userCreateRequest);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    void deleteUser(@PathVariable UUID id) {
        userService.delete(id);
    }
    
    


}
