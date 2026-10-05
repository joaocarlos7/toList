package i.tolist.controller;

import i.tolist.dto.request.AuthUserRequest;
import jakarta.validation.Valid;
import i.tolist.security.UserTokenConfig;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthUserController {
    
    private final UserTokenConfig userTokenConfig;
    public AuthUserController(UserTokenConfig userTokenConfig) {
        this.userTokenConfig = userTokenConfig;
    }
    
    @PostMapping("/user")
    public ResponseEntity<String> login(@Valid @RequestBody AuthUserRequest authUserRequest) {
        
        try {
            String token = userTokenConfig.execute(authUserRequest);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
        
    }
}
