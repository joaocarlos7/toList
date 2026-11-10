package i.tolist.dto.request.auth;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class AuthUserRequest {
    
    private String email;
    private String password;
}
