package br.com.lukemedrano.AuthAPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.lukemedrano.AuthAPI.repositories.IUsuarioRepositorio;

@Service
public class AuthorizationService implements UserDetailsService {
	@Autowired
	private IUsuarioRepositorio usuarioRepositorio;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return usuarioRepositorio.findByLogin(username);
	}
}