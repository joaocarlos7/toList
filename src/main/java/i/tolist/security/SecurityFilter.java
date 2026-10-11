package i.tolist.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import i.tolist.providers.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class SecurityFilter extends OncePerRequestFilter {
    
    private final JwtProvider jwtProvider;
    public SecurityFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
        throws ServletException, IOException {
        
        String header = request.getHeader("Authorization");
        
        if(header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
            String token = header.substring(7).trim();
            
            DecodedJWT decodedJWT = jwtProvider.validateToken(token);
            
            if(decodedJWT == null) {
            SecurityContextHolder.clearContext();
            
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
            }
            
            List<String> roles = decodedJWT
                    .getClaim("roles")
                    .asList(String.class);
            
            if (roles == null || roles.isEmpty()) {
                
                SecurityContextHolder.clearContext();
                
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            
            var authorities = roles
                    .stream()
                    .map(SimpleGrantedAuthority::new)
                    .toList();
            
            var authentication =
                    new UsernamePasswordAuthenticationToken(
                            decodedJWT.getSubject(), null, authorities);
            
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            
            context.setAuthentication(authentication);
            
            SecurityContextHolder.setContext(context);
            
            filterChain.doFilter(request, response);
            
        }
}


