package br.com.lukemedrano.AuthAPI.controllers;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.lukemedrano.AuthAPI.domain.produto.Produto;
import br.com.lukemedrano.AuthAPI.domain.produto.ProdutoRequestDTO;
import br.com.lukemedrano.AuthAPI.repositories.IProdutoRepositorio;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
	@Autowired
	private IProdutoRepositorio produtoRepositorio;
	
	@GetMapping
	public ResponseEntity<?> obterTodosProdutos() {
		Iterable<Produto> todosProdutos = produtoRepositorio.findAll();
		return ResponseEntity.ok(todosProdutos);
	}
	
	@PostMapping
	public ResponseEntity<?> novoProduto(@Valid @RequestBody ProdutoRequestDTO dadosDTO, UriComponentsBuilder uriBuilder) {
		Produto novoProduto = new Produto(dadosDTO);
		produtoRepositorio.save(novoProduto);
		
		UriComponents uriComponents = uriBuilder.path("/api/produtos/{id}").buildAndExpand(novoProduto.getId());
	    URI uri = uriComponents.toUri();
	    
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> obterProdutoPorId(@PathVariable String id){
		Optional<Produto> optionalProduto = produtoRepositorio.findById(id);
		
		if(optionalProduto.isPresent()) {
			Produto produto = optionalProduto.get();
			return ResponseEntity.ok(produto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> alterarProduto(@PathVariable String id, @Valid @RequestBody ProdutoRequestDTO dadosDTO){
		Optional<Produto> optionalProduto = produtoRepositorio.findById(id);
		
		if(optionalProduto.isPresent()) {
			Produto produtoAtualizado = optionalProduto.get();
			
			produtoAtualizado.setNome(dadosDTO.nome());
			produtoAtualizado.setPrecoEmCentavos(dadosDTO.precoEmCentavos());
			
			produtoRepositorio.save(produtoAtualizado);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> removerProduto(@PathVariable String id){
		Optional<Produto> produto = produtoRepositorio.findById(id);
		
		if(produto.isPresent()) {
			Produto produtoRemovido = produto.get();
			
			produtoRepositorio.delete(produtoRemovido);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
