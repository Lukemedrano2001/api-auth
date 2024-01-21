package br.com.lukemedrano.AuthAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.lukemedrano.AuthAPI.domain.usuario.Usuario;

public interface IUsuarioRepositorio extends JpaRepository<Usuario, String> {
	UserDetails findByLogin(String login);
}