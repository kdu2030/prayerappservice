package com.kevin.prayerappservice.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private final static int TOKEN_VALIDITY_LENGTH_MS = 1000 * 60 * 60 * 24 * 15;

    @Value("${jwt.signing-key}")
    private String signingKey;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken (UserDetails userDetails){
        return generateToken(userDetails, new HashMap<>());
    }

    public String generateToken (UserDetails userDetails, Map<String, Object> extraClaims){
        long currentSystemTime = System.currentTimeMillis();
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(currentSystemTime))
                .expiration(new Date(currentSystemTime + TOKEN_VALIDITY_LENGTH_MS))
                .signWith(getSigningKey())
                .compact();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        JwtParser parser = Jwts.parser()
                .verifyWith(getSigningKey())
                .build();

        return parser.parseSignedClaims(token).getPayload();
    }

    private SecretKey getSigningKey(){
        byte[] keyBytes = Decoders.BASE64.decode(signingKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}