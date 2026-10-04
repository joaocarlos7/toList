package i.tolist.mappers;

import i.tolist.dto.request.UserCreateRequest;
import i.tolist.dto.response.UserResponse;
import i.tolist.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    
    public UserResponse mapRequest(User userEntity) {
        
        return new UserResponse(
                userEntity.getId(),
                userEntity.getName(),
                userEntity.getEmail(),
                userEntity.isActive()
        );
    }
    
    public User mapEntity(UserCreateRequest userCreateRequest) {
        
        User user = new User();
        
        user.setId(userCreateRequest.id());
        user.setName(userCreateRequest.name());
        user.setEmail(userCreateRequest.email());
        user.setPassword(userCreateRequest.password());
        user.setActive(userCreateRequest.active());
        
        return user;
        
    }
}
