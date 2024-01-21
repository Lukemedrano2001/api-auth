package br.com.lukemedrano.AuthAPI.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
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
public class SegurancaConfig {
	// Security Filter Chain: Esta corrente/cadeia define as políticas de segurança aplicadas às requisições HTTP na aplicação.
	// csrf: Desativa a proteção CSRF (Cross-Site Request Forgery), apropriado para APIs RESTful.
	// sessionManagement: Configura a política de gerenciamento de sessão como STATELESS, indicando que a aplicação não deve criar ou usar sessões HTTP.
	
	@Autowired
	private FiltroSeguranca filtroSeguranca;
	
	@Bean
	public SecurityFilterChain cadeiaFiltrosSeguranca(HttpSecurity httpSecurity) throws Exception {
		return  httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                		.requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                		.requestMatchers(HttpMethod.POST, "/api/auth/registro").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/produtos").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/produtos").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/produtos").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(filtroSeguranca, UsernamePasswordAuthenticationFilter.class)
                .build();
	}
	
	@Bean
	public AuthenticationManager gerenciadorAutenticacao(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public PasswordEncoder codificadorSenha() {
		return new BCryptPasswordEncoder();
	}
}