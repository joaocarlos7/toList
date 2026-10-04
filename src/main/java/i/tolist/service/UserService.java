package i.tolist.service;


import i.tolist.dto.request.UserCreateRequest;
import i.tolist.dto.response.UserResponse;
import i.tolist.mappers.UserMapper;
import i.tolist.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    
    
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        
    }
    
    @Transactional()
    public UserResponse create(UserCreateRequest user) {}
    
    @Transactional()
    public UserResponse update(UUID userId, UserCreateRequest user) {}
    
    @Transactional()
    public UserResponse findById(UUID userId) {}
    
    @Transactional()
    public UserResponse findByEmail(String email) {}
    
    
    @Transactional()
    public void delete(UUID userId) {}
    
    
}
