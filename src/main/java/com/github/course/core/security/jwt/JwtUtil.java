package com.github.course.core.security.jwt;

import com.github.course.features.user.Role;
import com.github.course.features.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.token.validity}")
    private long tokenValidity;

    public User getUser(final String token) {
        try {
            Claims body = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
            User user = new User();
            user.setUsername(body.getSubject());
            Set<Role> roles = Arrays.asList(body.get("roles").toString().split(","))
                    .stream()
                    .map(Role::new)
                    .collect(Collectors.toSet());
            user.setRoles(roles);
            return user;
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage() + "=>" + e);
        }
        return null;
    }

    public String generateToke(User u) {
        Claims claims = Jwts.claims().setSubject(u.getUsername());
        claims.put("roles", u.getRoles());
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + tokenValidity;
        Date exp = new Date(expMillis);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(nowMillis))
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    public void validateToken(final String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
        } catch (Exception e) {
            //todo  exceptions
        }
    }

}
