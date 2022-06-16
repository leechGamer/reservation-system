package com.reservation.reservationsystem.config;

import com.reservation.reservationsystem.entity.customer.Customer;
import com.reservation.reservationsystem.exception.AuthenticationException;
import com.reservation.reservationsystem.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TokenProvider {

    private static final String SECRET_KEY = "secret-key-reservation-system";

    public String create(Customer customer) {
        Date expiryDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setSubject(customer.getId())
                .setIssuer("reservation-system")
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);
        Optional.ofNullable(claims.get(SECRET_KEY)).orElseThrow(
                () -> {
                    throw new AuthenticationException(ErrorCode.INVALID_TOKEN);
                });

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(SECRET_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public boolean validateToken(String token) {
        Claims claims = parseClaims(token);
        return claims == null ? false : true;
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
