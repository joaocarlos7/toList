package i.tolist.config;


import i.tolist.entities.User;
import i.tolist.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import static i.tolist.entities.enums.RoleType.ADMIN;


@Configuration
public class DataInitializer {
    
    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if(userRepository.findByRoleType(ADMIN).isEmpty()) {
                User userAdmin = new User();
                
                userAdmin.setName("admin");
                userAdmin.setEmail("empty@empty.com");
                userAdmin.setRoleType(ADMIN);
                userAdmin.setActive(true);
                userAdmin.setPassword(passwordEncoder.encode("12345"));
                userRepository.save(userAdmin);
                
                System.out.println("User Admin has been created");
            }
        };
    }
    
}
