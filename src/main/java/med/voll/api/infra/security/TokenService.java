package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import med.voll.api.domain.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String createToken(User user){
        System.out.println(secret);
        try {
            var algorithm = Algorithm.HMAC256(secret); // cria senha secreta de autenticação do token (selo de autenticidade)

            return JWT.create()
                    .withIssuer("API Voll.med") // define quem criou o token
                    .withSubject(user.getLogin()) // define o login de quem pertence o token (usuário)
                    .withExpiresAt(expirationDate()) // define data de expiração
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("error on generating jwt token", exception);
        }
    }

    public String getSubject (String tokenJWT){
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)// Criamos um verificador de JWT usando nosso algoritmo.
                    .withIssuer("API Voll.med")// token deve ter sido emitido por 'API Voll.med'
                    .build() // Criamos efetivamente o objeto responsável por realizar a verificação.
                    .verify(tokenJWT) // validamos de verdade.
                    .getSubject(); // se válido, retorna o campo subject (ex: `"user@email.com"`)

        } catch (JWTVerificationException exception){ // se não, caí em exceção
            throw new RuntimeException("JWT Token is invalid or expired!");
        }
    }

    private Instant expirationDate() { // metodo responsável por determinar a data de expiração do token
        return LocalDate.now()
                .plusDays(3)
                .atStartOfDay()
                .toInstant(ZoneOffset.of("-03:00"));
    }


}
