package i.tolist.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record UserCreateRequest (
        
        UUID id,
        
        @NotBlank
        String name,
        
        @NotBlank
        @Email
        String email,
        
        @NotBlank
        String password,
        boolean active
) {

}
