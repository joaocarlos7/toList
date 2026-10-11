package i.tolist.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailUpdateRequest (
        
        @NotBlank
        @Email
        String email,
        
        @NotBlank
        String password,
        
        @NotBlank
        String newEmail
) {
}
