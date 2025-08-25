package com.cdac.security;

import com.cdac.entities.User;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Component
@Slf4j
public class JwtUtils {

    @Value("${jwt.secret.key}")
    private String jwtSecret;

    @Value("${jwt.expiration.time}")
    private int jwtExpirationMs;

    private SecretKey key;

    @PostConstruct
    public void init() {
        log.info("Key {} Exp Time {}",jwtSecret,jwtExpirationMs);

        key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }


    public String generateJwtToken(Authentication authentication) {
        log.info("generate jwt token " + authentication);
        User userPrincipal =
                (User) authentication.getPrincipal();
        System.out.println(userPrincipal.getAuthorities());
        return Jwts.builder()
                // Use email as JWT subject for reliable lookup
                .subject(userPrincipal.getEmail())
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime()
                        + jwtExpirationMs))

                .claim("authorities", getAuthoritiesInString(userPrincipal.getAuthorities()))
                .claim("userId", userPrincipal.getId())
                .claim("userName", userPrincipal.getUsername())


                .signWith(key, Jwts.SIG.HS256)

                .compact();


    }


    public String getUserNameFromJwtToken(Claims claims) {
        return claims.getSubject();
    }


    public Claims validateJwtToken(String jwtToken) {

        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(jwtToken)
                    .getPayload();
        } catch (Exception e) {
            log.error("JWT validation failed: {}", e.getMessage());
            throw new RuntimeException("Invalid JWT token");
        }
    }

    private List<String> getAuthoritiesInString(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }


    public List<GrantedAuthority> getAuthoritiesFromClaims(Claims claims) {

        List<String> authorityNamesFromJwt =
                (List<String>) claims.get("authorities");
        List<GrantedAuthority> authorities =
                authorityNamesFromJwt.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        authorities.forEach(System.out::println);
        return authorities;
    }



    public Authentication populateAuthenticationTokenFromJWT(String jwt) {

        Claims payloadClaims = validateJwtToken(jwt);

        String email = getUserNameFromJwtToken(payloadClaims);

        List<GrantedAuthority> authorities = getAuthoritiesFromClaims(payloadClaims);

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(email, null, authorities);

        System.out.println("is authenticated " + token.isAuthenticated());// true
        return token;

    }

}
