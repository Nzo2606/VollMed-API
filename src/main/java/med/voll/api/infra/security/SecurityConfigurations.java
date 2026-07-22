package med.voll.api.infra.security;


import med.voll.api.domain.user.AuthenticationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations

/// classe responsável por guardar as configurações gerais de segurança do programa

{

    @Autowired
    SecurityFilter securityFilter;

    @Bean // Registra a segurança da aplicação no Spring para proteger as rotas HTTP.
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http // Abre o 'painel de ferramentas'

                .csrf(csrf -> csrf.disable()) //desativa a criação do CSRF token | (Cross-Site Request Forgery)
                .sessionManagement(
                        sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // definimos a política de sessão como 'STATELESS'
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(HttpMethod.POST, "/login").permitAll(); // permissão integral de acesso ao endpoint '/login' na requisição 'POST'
                    auth.anyRequest().authenticated(); // definição de todas as outras requisições como autenticadas ('authenticated')
                })
                .addFilterBefore(
                        securityFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .build(); // compacta tudo em um 'SecurityFilterChain' (cadeia de filtros de segurança)
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager(); //cria  o objeto `AuthenticationManager`
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); // ensina o Spring: “As senhas do banco usam BCrypt.”
    }
}
