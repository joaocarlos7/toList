package i.tolist.service;


import i.tolist.dto.request.UserCreateRequest;
import i.tolist.dto.response.UserResponse;
import i.tolist.entities.User;
import i.tolist.entities.enums.RoleType;
import i.tolist.exception.EmailAlreadyExists;
import i.tolist.exception.EmailNotFound;
import i.tolist.exception.UserIdNotFound;
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
    
    @Transactional
    public UserResponse create(UserCreateRequest user) {
        
        if(userRepository.existsByEmail(user.email())) {
            throw new EmailAlreadyExists("Email already exists");
        }
        
        User userEntity = userMapper.mapEntity(user);
        userEntity.setPassword(passwordEncoder.encode(user.password()));
        userEntity.setRoleType(RoleType.USER);
        User savedUser = userRepository.save(userEntity);
        
        return userMapper.mapRequest(savedUser);
    }
    
    @Transactional
    public UserResponse update(UUID userId, UserCreateRequest user) {
        
        User userUpdate = userRepository.findById(userId)
                .orElseThrow(() -> new UserIdNotFound(userId));
        
        boolean emailExists = userRepository.existsByEmail(user.email())
                && !userUpdate.getEmail().equals(user.email());
        
        if(emailExists) {
            throw new EmailAlreadyExists("Email already exists");
        }
        
        userUpdate.setName(user.name());
        userUpdate.setEmail(user.email());
        userUpdate.setPassword(passwordEncoder.encode(user.password()));
        userUpdate.setActive(user.active());
        
        return userMapper.mapRequest(userRepository.save(userUpdate));
    }
    
    @Transactional
    public UserResponse findById(UUID userId) {
        
        return userRepository.findById(userId)
                .map(userMapper::mapRequest)
                .orElseThrow(() -> new UserIdNotFound(userId));
        
    }
    
    @Transactional
    public UserResponse findByEmail(String email) {
        
        return userRepository.findByEmail(email)
                .map(userMapper::mapRequest)
                .orElseThrow(()-> new EmailNotFound(email));
    }
    
    @Transactional
    public void delete(UUID userId) {
        
        userRepository.deleteById(userId);
    }
    
    
}
