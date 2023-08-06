package com.junseok.wantedpreonboardingbackend.global.util;

import com.junseok.wantedpreonboardingbackend.global.property.JwtProperty;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtProvider {

    private final JwtProperty jwtProperty;

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public JwtProvider(JwtProperty jwtProperty) {
        this.jwtProperty = jwtProperty;
    }

    public String createJwt(String subject, String email) {
        Claims claims = Jwts.claims()
                .setSubject(subject);
        claims.put("email", email);

        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + jwtProperty.getExpiredTime()))
                .signWith(key)
                .compact();
    }

    public Map<String, Object> parseJwt(String jwt) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt);
            return claims.getBody();
        } catch (UnsupportedJwtException e) {
            throw new UnsupportedJwtException(e.getMessage());
        } catch (MalformedJwtException e) {
            throw new MalformedJwtException(e.getMessage());
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(null, null, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
