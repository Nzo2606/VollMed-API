package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.user.AuthenticationData;
import med.voll.api.domain.user.User;
import med.voll.api.infra.security.TokenData;
import med.voll.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // “Essa classe recebe requisições HTTP.”
@RequestMapping("/login") // Define a URL do controller.

///  Classe responsável por chamar e realizar a:
///         - obtenção e atribuição dos dados para um objeto dto
///         - autenticação dos usuários
///         - geração de tokens

public class AuthenticationController


       {


    @Autowired
    AuthenticationManager manager;
    /// Classe do Spring responsável por chamar o `AuthenticationService`.
    ///
    /// Ele:
    ///  - recebe login e senha;
    ///  - verifica se o usuário existe;
    ///  - valida a senha;
    ///  - autentica o usuário.

    @Autowired
    TokenService tokenService;
       /// Classe criada responsável por criar e validar os tokens.


    @PostMapping
    public ResponseEntity log_in(@RequestBody @Valid AuthenticationData data){
    /// @RequestBody = “Pegue o JSON da requisição e transforme em objeto Java.”
    /// @Valid = Executa validações.

        var authenticationToken = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        /// Tratamento dos dados po meio do tipo 'UsernamePasswordAuthenticationToken',
        /// pois o Spring não entende o DTO `AuthenticationData`.

        var authentication = manager.authenticate(authenticationToken);
        /// Com isso, o Spring automaticamente:
        ///
        /// 1. Chama o 'AuthenticationService'
        ///
        /// 2. O service usa o repository.
        ///
        /// 3. O repository faz:
        ///     SELECT * FROM usuarios WHERE login = ?
        ///
        /// 4. Spring pega:
        ///     - senha digitada;
        ///     - senha do banco.
        ///
        /// 5. Compara usando BCrypt
        ///
        /// 6. Se estiver correto:
        ///     - autentica o usuário.
        ///    Se estiver errado:
        ///     - lança erro 403.

        var tokenJWT = tokenService.createToken((User) authentication.getPrincipal());
        return ResponseEntity.ok(new TokenData(tokenJWT));
    }   

}
