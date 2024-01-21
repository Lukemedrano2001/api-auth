package br.com.lukemedrano.AuthAPI.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.lukemedrano.AuthAPI.domain.usuario.AuthenticationDTO;
import br.com.lukemedrano.AuthAPI.domain.usuario.LoginResponseDTO;
import br.com.lukemedrano.AuthAPI.domain.usuario.RegistroDTO;
import br.com.lukemedrano.AuthAPI.domain.usuario.Usuario;
import br.com.lukemedrano.AuthAPI.infra.security.TokenService;
import br.com.lukemedrano.AuthAPI.repositories.IUsuarioRepositorio;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
	// guilherme 1234 - ADMIN
	// coreia 5678 - ADMIN
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private IUsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO dadosDTO) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(dadosDTO.login(), dadosDTO.senha());
		var auth = authenticationManager.authenticate(usernamePassword);
		
		var token = tokenService.gerarToken((Usuario) auth.getPrincipal());
		
		return ResponseEntity.ok(new LoginResponseDTO(token));
	}
	
	@PostMapping("/registro")
	public ResponseEntity<?> registro(@RequestBody @Valid RegistroDTO dadosDTO, UriComponentsBuilder uriBuilder){
		if(usuarioRepositorio.findByLogin(dadosDTO.login()) != null) {
			return ResponseEntity.badRequest().build();
		}
		
		String senhaCriptografada = new BCryptPasswordEncoder().encode(dadosDTO.senha());
		Usuario novoUsuario = new Usuario(dadosDTO.login(), senhaCriptografada, dadosDTO.role());
		
		usuarioRepositorio.save(novoUsuario);
		
		UriComponents uriComponents = uriBuilder.path("/api/produtos/{id}").buildAndExpand(novoUsuario.getId());
        URI uri = uriComponents.toUri();
		
		return ResponseEntity.created(uri).build();
	}
}