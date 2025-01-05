package com.project.shopapp.components;

import com.project.shopapp.exceptions.InvalidParamException;
import com.project.shopapp.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtTokenUtils {

    @Value("${jwt.expiration}")
    private long expiration; // luu vao bien moi truong --> file .yml
    @Value("${jwt.secretKey}")
    private String secretKey; // luu vao bien moi truong --> file .yml

    public String generateToken(User user) throws InvalidParamException {
        // properties => claims //
        Map<String,Object> claims = new HashMap<>();
        claims.put("phoneNumber", user.getPhoneNumber());
        try {
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(user.getPhoneNumber())
                    .setExpiration(new Date(System.currentTimeMillis() + expiration*1000))
                    // signWith(key, thuật toán)
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();
        } catch (Exception e) {
            throw new InvalidParamException("Can not create token, error:"+e.getMessage());
        }
    }

    private Key getSignInKey(){
        // Mã hóa key bang base 64 //
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // ham generateSecretKey chi duy nhat 1 lan //
    private String generatSecretKey(){
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[32];
        random.nextBytes(keyBytes);
        return secretKey = Base64.getEncoder().encodeToString(keyBytes);
    }

    private Claims extractAllclaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = this.extractAllclaims(token);
        return claimsResolver.apply(claims);
    }

    // Check expiration //
    private boolean isTokenExpired(String token) {
        final Date expirationDate = this.extractClaims(token, Claims::getExpiration);
        return expirationDate.before (new Date());
    }

    // extract phone number from token //
    public String extractPhoneNumber(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String phoneNumber = this.extractPhoneNumber(token);
        return phoneNumber.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

}
