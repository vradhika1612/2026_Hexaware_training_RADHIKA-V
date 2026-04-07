package com.springboot.bookapp.config;

//import com.springboot.bookapp.service.UserService;
import com.springboot.bookapp.utility.JwtFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    // Manual constructor replacing @AllArgsConstructor
    public SecurityConfig(@Lazy JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain bookSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests((authorize) -> authorize

                        .requestMatchers(HttpMethod.OPTIONS, "/**")
                        .permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/user/sign-up")
                        .permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/auth/login")
                        .authenticated()

                        .requestMatchers(HttpMethod.GET, "/api/books/get-all")
                        .hasAnyAuthority("AUTHOR", "STUDENT")
                        .requestMatchers(HttpMethod.GET, "/api/books/get/{isbn}")
                        .hasAnyAuthority("AUTHOR", "STUDENT")

                        .requestMatchers(HttpMethod.POST, "/api/books/add")
                        .hasAuthority("AUTHOR")
                        .requestMatchers(HttpMethod.PUT, "/api/books/update/{isbn}")
                        .hasAuthority("AUTHOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/books/delete/{isbn}")
                        .hasAuthority("AUTHOR")

                        .anyRequest().permitAll()
                );

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}