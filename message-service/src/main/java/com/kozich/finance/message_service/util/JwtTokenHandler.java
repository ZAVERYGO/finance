package com.kozich.finance.message_service.util;

import com.kozich.finance.message_service.config.properites.JWTProperty;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class JwtTokenHandler {

    private final JWTProperty property;

    public JwtTokenHandler(JWTProperty property) {
        this.property = property;
    }

    public String generateAccessToken(UserDetails user) {
        return generateAccessToken(user.getUsername());
    }

    public String generateAccessToken(String name) {
        return Jwts.builder().setSubject(name).setIssuer(property.getIssuer()).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7))) // 1 week
                .signWith(SignatureAlgorithm.HS512, property.getSecret()).compact();
    }

    public String getUserName(String token) {
        Claims claims = Jwts.parser().setSigningKey(property.getSecret()).parseClaimsJws(token).getBody();

        return claims.getSubject();
    }

    public Date getExpirationDate(String token) {
        Claims claims = Jwts.parser().setSigningKey(property.getSecret()).parseClaimsJws(token).getBody();

        return claims.getExpiration();
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(property.getSecret()).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature - {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty - {}", ex.getMessage());
        }
        return false;
    }
}
