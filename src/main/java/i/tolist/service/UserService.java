package i.tolist.service;


import i.tolist.dto.request.EmailUpdateRequest;
import i.tolist.dto.request.PasswordUpdateRequest;
import i.tolist.dto.request.UserCreateRequest;
import i.tolist.dto.request.UserUpdateRequest;
import i.tolist.dto.response.UserResponse;
import i.tolist.entities.User;
import i.tolist.entities.enums.RoleType;
import i.tolist.exception.EmailAlreadyExists;
import i.tolist.exception.EmailNotFound;
import i.tolist.exception.InvalidCredentials;
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
    public UserResponse update(UUID userId, UserUpdateRequest user) {
        
        User userUpdate = userRepository.findById(userId)
                .orElseThrow(() -> new UserIdNotFound(userId));
        
        boolean emailExists = userRepository.existsByEmail(user.email())
                && !userUpdate.getEmail().equals(user.email());
        
        if(emailExists) {
            throw new EmailAlreadyExists("Email already exists");
        }
        
        userUpdate.setName(user.name());
        userUpdate.setEmail(user.email());
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
    
    @Transactional
    public void updatePassword(PasswordUpdateRequest passwordUpdateRequest) {
        
        User user = userRepository.findByEmail(passwordUpdateRequest.email())
                .orElseThrow(() -> new EmailNotFound(passwordUpdateRequest.email()));
        
        boolean emailExists = userRepository.existsByEmail(passwordUpdateRequest.email());
        if (!emailExists) {
            throw new EmailNotFound(passwordUpdateRequest.email());
        }
        
        
        boolean passwordMatches = passwordEncoder.matches(passwordUpdateRequest.oldPassword(), user.getPassword());
        
        if (!passwordMatches) {
            throw new InvalidCredentials("Invalid Credentials");
            
        }
        user.setPassword(passwordEncoder.encode(passwordUpdateRequest.newPassword()));
        userMapper.mapRequest(userRepository.save(user));
        
    }
    
    @Transactional
    public void updateEmail(EmailUpdateRequest emailUpdateRequest) {
        
        User user = userRepository.findByEmail(emailUpdateRequest.email())
                .orElseThrow(() -> new EmailNotFound(emailUpdateRequest.email()));
        
        boolean emailExists = userRepository.existsByEmail(emailUpdateRequest.email());
        boolean alreadyExists = userRepository.existsByEmail(emailUpdateRequest.newEmail());
        
        
        if(!emailExists) {
            throw new EmailNotFound(emailUpdateRequest.email());
        } else if(alreadyExists) {
            throw new EmailAlreadyExists("Email already exists");
        }
        
        user.setEmail(emailUpdateRequest.newEmail());
        userMapper.mapRequest(userRepository.save(user));
        
    }
    
    
}
