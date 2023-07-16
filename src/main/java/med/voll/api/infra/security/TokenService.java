package med.voll.api.infra.security;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import med.voll.api.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;
    public String buildToken(User user){
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.create().withIssuer("API Voll.med")
                .withSubject(user.getLogin())
                .withExpiresAt(expirationDate())
                .sign(algoritmo);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Ops! Houve um erro ao gerar token jwt", exception);
        }
    }

    private Instant expirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}