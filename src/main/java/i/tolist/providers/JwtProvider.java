package i.tolist.providers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
public class JwtProvider {
    
    @Value("{spring.security.token.secrets")
    private String secret;
    
    
    public DecodedJWT validateToken(String token) {
        
        token = token.replace("Bearer ", "");
        
        Algorithm algorithm = Algorithm.HMAC256(secret);
        
        try {
            
            return JWT.require(algorithm)
                    .build()
                    .verify(token);
        } catch (JWTVerificationException ex) {
            
            return null;
        }
    }
}
