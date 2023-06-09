package jpkr.advancedprogrammingproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@SpringBootApplication
public class AdvancedProgrammingProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdvancedProgrammingProjectApplication.class, args);
    }

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange((authorize) -> authorize
                        .pathMatchers("/books").permitAll()
                        .anyExchange().denyAll()
                );
        return http.build();
    }
}
