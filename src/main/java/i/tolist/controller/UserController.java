package i.tolist.controller;


import i.tolist.dto.request.UserCreateRequest;
import i.tolist.dto.response.UserResponse;
import i.tolist.entities.User;
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
    UserResponse getUser(@PathVariable UUID id) {
        
        return userService.findById(id);
    }
    
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    UserResponse createUser(@RequestBody UserCreateRequest userCreateRequest, HttpServletRequest request) {
        
        return userService.create(userCreateRequest);
    }
    
    
    


}
