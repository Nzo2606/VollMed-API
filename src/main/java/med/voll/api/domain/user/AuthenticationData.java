package med.voll.api.domain.user;

import org.springframework.security.core.Authentication;

public record AuthenticationData(String login, String password) {
}
