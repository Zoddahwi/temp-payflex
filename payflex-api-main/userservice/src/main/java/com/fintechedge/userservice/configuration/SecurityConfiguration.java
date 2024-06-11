package com.fintechedge.userservice.configuration;


import com.fintechedge.userservice.advice.ApplicationExceptionHandler;
import com.fintechedge.userservice.auth.JwtAuthenticationFilter;
import com.fintechedge.userservice.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import reactor.core.publisher.Mono;

@Configuration
@EnableReactiveMethodSecurity
public class SecurityConfiguration {


    private final ReactiveAuthenticationManager authenticationManager;
    private final ServerSecurityContextRepository securityContextRepository;

    public SecurityConfiguration(ReactiveAuthenticationManager authenticationManager,
                                 ServerSecurityContextRepository securityContextRepository) {
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
    }

//    import org.springframework.security.config.http.SessionCreationPolicy;

// ...

//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//        http
//                .addFilterAt(jwtAuthenticationFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
//                .cors().disable()
//                .csrf().disable() // Disable CSRF
//                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Added this line
//                .authorizeHttpRequests(auth -> auth.antMatchers("/token/**").permitAll())
//                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
//                .authenticationManager(authenticationManager)
//                .securityContextRepository(securityContextRepository);
//
//        return http.build();
//    }


    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .addFilterAt(jwtAuthenticationFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
                .cors().disable()
                .csrf().disable() // Disable CSRF
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/api/v1/auth/register",
                                "/api/v1/auth/login", "/api/v1/auth/send-sms-verification",
                                "/api/v1/auth/resetPassword", "/api/v1/auth/initiatePasswordReset",
                                "/api/v1/auth/updateProfile/{id}", "/api/v1/auth/verify" ,
                                "/api/v1/auth/new_login")
                        .permitAll()
                        .anyExchange()
                        .authenticated()
                )
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository);

        return http.build();
    }

    @Bean
    public ReactiveUserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> userRepository.findByUsername(username)
                .map(user -> User.withUsername(user.getUsername())
                        .password(securityPasswordEncoder().encode(user.getPassword()))
                        .roles(user.getRoles().toArray(new String[0])) // Adjusted this line
                        .build());
    }


    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

//    @Bean
//    public ApplicationExceptionHandler customApplicationExceptionHandler() {
//        return new ApplicationExceptionHandler();
//    }

//    @Bean
//    public ServerSecurityContextRepository securityContextRepository() {
//        return new WebSessionServerSecurityContextRepository();
//    }

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager(UserRepository userRepository) {
        ReactiveUserDetailsService userDetailsService = username -> userRepository.findByUsername(username)
                .map(user -> User.withUsername(user.getUsername())
                        .password(securityPasswordEncoder().encode(user.getPassword()))
                        .roles(user.getRoles().toArray(new String[0]))
                        .build());

        UserDetailsRepositoryReactiveAuthenticationManager authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        authenticationManager.setPasswordEncoder(securityPasswordEncoder());
        return authenticationManager;
    }

    @Bean
    public PasswordEncoder securityPasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
