package br.com.lukemedrano.AuthAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lukemedrano.AuthAPI.domain.produto.Produto;

public interface IProdutoRepositorio extends JpaRepository<Produto, String> {

}
