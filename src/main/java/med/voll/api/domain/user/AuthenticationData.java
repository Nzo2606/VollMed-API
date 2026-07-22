package med.voll.api.domain.user;

import org.springframework.security.core.Authentication;

///
/// Record com papel DTO de atrbuição dos dados recebidos
/// do AuthenticationController (login) para um objeto
///

public record AuthenticationData(String login, String password)

{
}
