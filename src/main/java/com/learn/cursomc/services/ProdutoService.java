package com.learn.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.learn.cursomc.domain.Categoria;
import com.learn.cursomc.domain.Produto;
import com.learn.cursomc.repositories.CategoriaRepository;
import com.learn.cursomc.repositories.ProdutoRepository;
import com.learn.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {
	@Autowired
	private ProdutoRepository pr;
	
	@Autowired
	private CategoriaRepository cr;
	
	public Produto find(Integer id_busca) throws ObjectNotFoundException {
		Optional<Produto> cat = pr.findById(id_busca);
//		este sem tratamento -> return cat.orElse(null);
		// OR 
		return cat.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! id: " + id_busca + ", Tipo: " + Produto.class.getName()));
	}
	
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = cr.findAllById(ids);
		return pr.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
	}
}