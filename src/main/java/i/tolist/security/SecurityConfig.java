package i.tolist.security;


import i.tolist.providers.JwtProvider;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity

public class SecurityConfig {
    
    private static final String PRIVATE_USERS_URL = "/users/**";
    private static final String ROLE_ADMIN = "ADMIN";
    private static final String PRIVATE_TASKS_URL = "/tasks/**";
    
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtProvider jwtProvider) throws Exception {
        
        
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                
                .sessionManagement(session ->
                        session
                                .sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        ))
                
                .exceptionHandling(ex ->
                        ex
                                .authenticationEntryPoint(
                                (request, response, exception) ->
                                        response.sendError(
                                                HttpServletResponse.SC_UNAUTHORIZED
                                        )
                        )
                )
                
                .authorizeHttpRequests(auth ->
                        auth
                        .requestMatchers(HttpMethod.POST, "/users/create").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/users").permitAll()
                        
                        .requestMatchers(HttpMethod.GET, PRIVATE_USERS_URL).hasRole(ROLE_ADMIN)
                        .requestMatchers(HttpMethod.PUT, PRIVATE_USERS_URL).hasRole(ROLE_ADMIN)
                        .requestMatchers(HttpMethod.DELETE, PRIVATE_USERS_URL).hasRole(ROLE_ADMIN)
                        
                        .requestMatchers(HttpMethod.PATCH, "/users/emailUpdate").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/users/passwordUpdate").authenticated()
                        
                        .requestMatchers(HttpMethod.GET, PRIVATE_TASKS_URL).authenticated()
                        .requestMatchers(HttpMethod.POST, PRIVATE_TASKS_URL).authenticated()
                        .requestMatchers(HttpMethod.PATCH, PRIVATE_TASKS_URL).authenticated()
                        .requestMatchers(HttpMethod.DELETE, PRIVATE_TASKS_URL).authenticated()
                        .anyRequest().authenticated()
                )
        
                .addFilterBefore(
                        new SecurityFilter(jwtProvider),
                        UsernamePasswordAuthenticationFilter.class
                );
        
        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
