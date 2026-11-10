package i.tolist.dto.request.user;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record PasswordUpdateRequest (
        
        
        @NotBlank
        @Email
        String email,
        
        @NotBlank
        String oldPassword,
        
        @NotBlank
        String newPassword
        
        
){
}
