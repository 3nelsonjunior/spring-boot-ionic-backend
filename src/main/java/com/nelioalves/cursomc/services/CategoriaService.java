package com.nelioalves.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.repositories.CategoriaRepository;
import com.nelioalves.cursomc.services.exceptions.DataIntegrityException;
import com.nelioalves.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo; //acessando o repository
	
	//serviço que busca categoria
	public Categoria find(Integer id) {
		Categoria obj = repo.findOne(id);
		if(obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id.: "+ id
					+ ", Tipo.: " + Categoria.class.getName());
		}
		return obj;
	}
	
	public List<Categoria> findAll(){
		return repo.findAll();
	}
	
	public Categoria insert(Categoria objCategoria) {
		objCategoria.setId(null); //garantir id vazio, devido auto incrementação
		return repo.save(objCategoria);
	}
	
	public Categoria update(Categoria objCategoria) {
		find(objCategoria.getId()); //verifica se o id existe
		return repo.save(objCategoria);
	}

	public void delete(Integer id) {
		find(id); //verifica se o id existe
		try {
			repo.delete(id);
		}
		catch(DataIntegrityViolationException e){
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos!");
		}		
	}
	
	//Paginação - começa com 0 a contagem
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
}
