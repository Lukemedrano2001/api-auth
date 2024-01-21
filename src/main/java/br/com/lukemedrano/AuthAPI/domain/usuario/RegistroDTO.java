package br.com.lukemedrano.AuthAPI.domain.usuario;

public record RegistroDTO(String login, String senha, UsuarioRole role) {

}