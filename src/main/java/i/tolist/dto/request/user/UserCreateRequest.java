package i.tolist.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserCreateRequest (
        
        @NotBlank
        String name,
        
        @NotBlank
        @Email
        String email,
        
        @NotBlank
        String password,
        
        @NotNull
        boolean active
) {

}

