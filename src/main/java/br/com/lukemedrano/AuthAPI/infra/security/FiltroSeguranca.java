package br.com.lukemedrano.AuthAPI.infra.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.lukemedrano.AuthAPI.repositories.IUsuarioRepositorio;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FiltroSeguranca extends OncePerRequestFilter {

	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private IUsuarioRepositorio usuarioRepositorio;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		var token = recuperarToken(request);
		
		if(token != null) {
			var login = tokenService.validarToken((String) token);
			UserDetails usuario = usuarioRepositorio.findByLogin(login);
			
			var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		
		filterChain.doFilter(request, response);
	}

	private Object recuperarToken(HttpServletRequest request) {
		var authHeader = request.getHeader("Authorization");
		
		if(authHeader == null) {
			return null;
		} else {
			return authHeader.replace("Bearer ", "");
		}
	}
}