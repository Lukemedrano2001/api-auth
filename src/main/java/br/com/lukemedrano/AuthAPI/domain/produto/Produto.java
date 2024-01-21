package br.com.lukemedrano.AuthAPI.domain.produto;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "produtos")
public class Produto {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	private String nome;
	
	@Column(name = "preco_em_centavos")
	private int precoEmCentavos;
	
	public Produto() {}

	public Produto(ProdutoRequestDTO dadosDTO) {
		this.nome = dadosDTO.nome();
		this.precoEmCentavos = dadosDTO.precoEmCentavos();
	}
	
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getPrecoEmCentavos() {
		return this.precoEmCentavos;
	}

	public void setPrecoEmCentavos(int precoEmCentavos) {
		this.precoEmCentavos = precoEmCentavos;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object objeto) {
		if (this == objeto)
			return true;
		if (objeto == null || getClass() != objeto.getClass())
			return false;
		Produto produto = (Produto) objeto;
		return Objects.equals(id, produto.id);
	}
}