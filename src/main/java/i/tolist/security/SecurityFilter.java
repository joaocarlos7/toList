package i.tolist.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import i.tolist.providers.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
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
        
        SecurityContextHolder.getContext().setAuthentication(null);
        String header = request.getHeader("Authorization");
        
        if(header != null && !header.isBlank()) {
            DecodedJWT decodedJWT = jwtProvider.validateToken(header);
            
            if(decodedJWT == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            String subjectToken = decodedJWT.getSubject();
            var roleList = decodedJWT.getClaim("roles").asList(String.class);
            
            var authorities = roleList.stream()
                    .map(SimpleGrantedAuthority::new)
                    .toList();
            
            request.setAttribute("UserId", subjectToken);
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(subjectToken, null, authorities);
            
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        
        filterChain.doFilter(request, response);
        }
    }

