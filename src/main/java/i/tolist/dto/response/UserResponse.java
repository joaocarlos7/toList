package i.tolist.dto.response;

import i.tolist.entities.enums.RoleType;

import java.util.UUID;

public record UserResponse (
    
    UUID id,
    String name,
    String email,
    boolean active,
    RoleType roleType

){
}
