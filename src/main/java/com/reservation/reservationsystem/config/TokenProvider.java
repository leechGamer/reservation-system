package com.reservation.reservationsystem.config;

import com.reservation.reservationsystem.exception.AuthenticationException;
import com.reservation.reservationsystem.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TokenProvider {

    protected final Key key;

    public TokenProvider(@Value("${spring.jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String create(User user) {
        Date expiryDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));
        String token = Jwts.builder()
                .setSubject(user.getUsername())
                .signWith(key, SignatureAlgorithm.HS256)
                .setIssuer("reservation-system")
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .claim("roles",
                        user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(","))
                )
                .compact();

        return token;
    }

    public String createRefreshToken(User user) {
        Date expiryDate = Date.from(Instant.now().plus(3, ChronoUnit.DAYS));
        return Jwts.builder()
                .setSubject(user.getUsername())
                .signWith(key, SignatureAlgorithm.HS256)
                .setIssuer("reservation-system")
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .claim("roles",
                        user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                                .collect(Collectors.joining(","))
                )
                .compact();
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        Claims claims = parseClaims(token);
        Optional.ofNullable(claims).orElseThrow(
                () -> {
                    throw new AuthenticationException(ErrorCode.INVALID_TOKEN);
                });

        Collection<? extends SimpleGrantedAuthority> authorities =
                Arrays.stream(claims.get("roles").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
