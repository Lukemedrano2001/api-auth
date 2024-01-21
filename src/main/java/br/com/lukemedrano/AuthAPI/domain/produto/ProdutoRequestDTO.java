package br.com.lukemedrano.AuthAPI.domain.produto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProdutoRequestDTO(
		@NotBlank
		String nome,
		
		@Min(0)
		@NotNull
		int precoEmCentavos
	) {
}