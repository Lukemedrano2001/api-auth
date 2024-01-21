package br.com.lukemedrano.AuthAPI.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import br.com.lukemedrano.AuthAPI.domain.usuario.Usuario;

@Service
public class TokenService {
	// Token: O token é utilizado para autenticar e autorizar as solicitações do usuário na aplicação. Ou seja, o token transita do servidor para o cliente e vice-versa.
	// Secret Key: É uma criptografia de chave simétrica para gerar os tokens, criptografar e descriptografar os hashs.
	
	@Value("${api.security.token.secret}")
	private String secret;
	
	public String gerarToken(Usuario usuario) {
		try {
			Algorithm algoritmo = Algorithm.HMAC256(secret);
			String token = JWT.create()
					.withIssuer("auth-api")
					.withSubject(usuario.getLogin())
					.withExpiresAt(genExpirationDate())
					.sign(algoritmo);
			
			return token;
		} catch(JWTCreationException exception) {
			throw new RuntimeException("Erro gerar token", exception);
		}
	}
	
	public String validarToken(String token) {
		try {
			Algorithm algoritmo = Algorithm.HMAC256(secret);
			return JWT.require(algoritmo)
					.withIssuer("auth-api")
					.build()
					.verify(token)
					.getSubject();
		} catch(JWTVerificationException exception) {
			return "";
		}
	}
	
	private Instant genExpirationDate() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
}