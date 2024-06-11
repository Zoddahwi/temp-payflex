package com.fintechedge.userservice.configuration;

import com.fintechedge.userservice.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.function.Function;


@Component
public class JwtService {
    @Value("${jwt_secret}")
    private String secretKey;
    @Value("${jwt_expiration_in_days}")
    private long jwtExpiration;
    @Value("${jwt_refresh_expiration_in_days}")
    private long refreshExpiration;


    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

//    public Claims parseToken(String token) {
//        JwtParser jwtParser = (JwtParser) Jwts.parser().setSigningKey(secretKey);
//        return jwtParser.parseClaimsJws(token).getBody();
//    }

    public Claims parseToken(String token) {
        JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(secretKey).build();
        return jwtParser.parseClaimsJws(token).getBody();
    }

//    public Mono<String> validateToken(String token) {
//        JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(secretKey).build();
//        String subject = jwtParser.parseClaimsJws(token).getBody().getSubject();
//        return subject != null ? Mono.just(token) : Mono.empty();
//    }


    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateRefreshToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

//    public Mono<String> validateToken(String token) {
//        JwtParser jwtParser = Jwts.parser()
//                .setSigningKey(secretKey)
//                .parseClaimsJws(token);
//        String subject = jwtParser.getBody().getSubject();
//        return subject != null ? Mono.just(token) : Mono.empty();
//    }


    public Mono<String> validateToken(String token) {
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build();
        String subject = jwtParser
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return subject != null ? Mono.just(token) : Mono.empty();
    }

    public boolean isTokenValid(String token) {
        return isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }

    private Date getExpiration(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

//    public boolean isTokenValid(String token) {
//        return !isTokenExpired(token);
//    }

//   ]
//    private Claims getAllClaimsFromToken(String token) {
//        return Jwts
//                .parser()
//                .setSigningKey(getSignInKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    private Key getSignInKey() {
//        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
//
//    private String buildToken(
//            Map<String, Object> extraClaims,
//            User user,
//            long expiration) {
//        return Jwts
//                .builder()
//                .setClaims(extraClaims)
//                .setSubject(user.getEmail())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + expiration))
//                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    // validate token
//    public Boolean validateToken(String token, UserDetails userDetails) {
//        final String username = getUsernameFromToken(token);
//        return (username.equals(userDetails.getUsername()) && isTokenExpired(token));
//    }

}
