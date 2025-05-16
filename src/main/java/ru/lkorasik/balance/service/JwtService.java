package ru.lkorasik.balance.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import ru.lkorasik.balance.data.entity.User;

import java.util.Map;
import java.util.function.Function;

public interface JwtService {
    Long extractId(String token);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    String generateToken(User user);

    String generateToken(Map<String, Object> extraClaims, User user);

    boolean isTokenValid(String token, UserDetails userDetails);
}
