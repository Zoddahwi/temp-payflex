//package com.fintechedge.userservice.configuration;
//
//import com.fintechedge.userservice.user.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import reactor.core.publisher.Mono;
//
//@Service
//@RequiredArgsConstructor
//public class AppUserDetailsService implements ReactiveUserDetailsService {
//
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//    @Override
//    public Mono<UserDetails> findByUsername(String username, String email) {
//        return userRepository.findByEmailOrUsername(username,email)
//                .map(user -> org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
//                        .password(passwordEncoder.encode(user.getPassword()))
//                        .build())
//                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found")));
//    }
//
//    @Override
//    public Mono<UserDetails> findByUsername(String username) {
//        return null;
//    }
//}
//
