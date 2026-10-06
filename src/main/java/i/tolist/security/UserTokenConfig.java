package i.tolist.security;

import com.auth0.jwt.algorithms.Algorithm;
import i.tolist.dto.request.AuthUserRequest;
import i.tolist.entities.User;
import i.tolist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.security.core.AuthenticationException;


import java.time.Duration;
import java.time.Instant;



@Component
public class UserTokenConfig {
    
    @Value("${spring.security.token.secret}")
    private String secretKey;
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public UserTokenConfig(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    public String execute(AuthUserRequest authUserRequest) throws AuthenticationException {
        
        User userExists = userRepository.findByEmail(authUserRequest.getEmail())
                .orElseThrow(()-> new UsernameNotFoundException("Incorrect credentials"));
                
        boolean passwordMatches = passwordEncoder.matches(authUserRequest.getPassword(), userExists.getPassword());
        
        if (!passwordMatches) {
            throw new BadCredentialsException("Incorrect credentials");
        }
        
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        
        return com.auth0.jwt.JWT.create()
                .withIssuer("toList")
                .withSubject(userExists.getId().toString())
                .withClaim("roles", java.util.List.of("ROLE_" + userExists.getRoleType().name()))
                .withExpiresAt(Instant.now().plus(Duration.ofHours(8)))
                .sign(algorithm);
        
        
    }
}
