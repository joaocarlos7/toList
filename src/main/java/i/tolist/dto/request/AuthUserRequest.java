package i.tolist.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class AuthUserRequest {
    
    private String email;
    private String password;
}
