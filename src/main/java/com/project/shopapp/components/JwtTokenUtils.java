package com.project.shopapp.components;

import com.project.shopapp.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;

import java.security.Key;
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

    public String generateToken(User user) {
        // properties => claims //
        Map<String,Object> claims = new HashMap<>();
        claims.put("phoneNumber", user.getPhoneNumber());
        try {
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setSubject(user.getPhoneNumber())
                    .setExpiration(new Date(System.currentTimeMillis() + expiration*1000))
                    // signWith(key, thuật toán)
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();
            return token;
        } catch (Exception e) {
            System.out.println("Can not create token, error:"+e.getMessage());
            return null;
        }
    }

    private Key getSignInKey(){
        // Mã hóa key bang base 64 //
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllclaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJwt(token)
                .getBody();
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = this.extractAllclaims(token);
        return claimsResolver.apply(claims);
    }

    // Check expiration //
    private boolean isTokenExpired(String token) {
        final Date expirationDate = this.extractClaims(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }
}
