package com.nelioalves.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service; //acessando o serviço
	
	//value incluido para acesssar via 'categorias/id' no navegador
	@RequestMapping(value="/{id}", method=RequestMethod.GET) 
	//retorna um objeto do tipo Respose Entity, já encapsula varias informações de uma resposta HTTP para um serviço REST  
	//find recebe um id(PathVariable = para o id da URL ir para o id da variavel )
	public ResponseEntity<?> find(@PathVariable Integer id) { 
		Categoria obj = service.buscar(id);
		return ResponseEntity.ok().body(obj); //body = corpo da resposta vai o objeto
	}

}
