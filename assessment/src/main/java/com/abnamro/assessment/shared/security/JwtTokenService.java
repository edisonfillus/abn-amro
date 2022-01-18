package com.abnamro.assessment.shared.security;

import java.time.Clock;
import java.time.Instant;
import java.util.Date;
import java.util.stream.Collectors;

import com.abnamro.assessment.users.repositories.entities.Role;
import com.abnamro.assessment.users.repositories.entities.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * Service responsible to issue and validate tokens
 */
@Component
public class JwtTokenService {

    private final Algorithm algorithm;

    private final JWTVerifier verifier;

    private final String issuer;

    private final Long ttl;

    private final Clock clock;

    @Autowired
    public JwtTokenService(
        @Value("${jwt.secret}") String secret,
        @Value("${jwt.issuer}") String issuer,
        @Value("${jwt.ttl}") Long ttl,
        Clock clock
    ) {
        algorithm = Algorithm.HMAC256(secret);
        verifier = JWT.require(algorithm).withIssuer(issuer).build();
        this.issuer = issuer;
        this.ttl = ttl;
        this.clock = clock;
    }

    public String issueToken(User user) {
        Instant now = Instant.now(clock);
        Instant expiry = Instant.now(clock).plusMillis(ttl);
        return JWT.create()
                  .withIssuer(issuer)
                  .withSubject(user.getUsername())
                  .withIssuedAt(Date.from(now))
                  .withExpiresAt(Date.from(expiry))
                  .withArrayClaim("roles", user.getRoles().stream().map(Role::getId).toArray(Integer[]::new))
                  .sign(algorithm);
    }

    public UserDetails decodeToken(String token) {
        DecodedJWT jwt = verifier.verify(token);
        return User.builder()
                   .userName(jwt.getSubject())
                   .roles(jwt.getClaim("roles").asList(Integer.class).stream().map(Role::getById).collect(Collectors.toList()))
                   .build();
    }

}
