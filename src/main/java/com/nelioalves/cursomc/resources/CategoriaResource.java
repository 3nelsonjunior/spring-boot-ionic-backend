package com.nelioalves.cursomc.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
	public ResponseEntity<Categoria> find(@PathVariable Integer id) { 
		Categoria obj = service.find(id);
		return ResponseEntity.ok().body(obj); //body = corpo da resposta vai o objeto
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Categoria objCategoria){ //RequestBody faz json ser convertido para JAVA
		objCategoria = service.insert(objCategoria);
		//pegar id da URL: fromCurrentRequest atribuindo via buildAndExpand
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("{/id}").buildAndExpand(objCategoria.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT) 
	public ResponseEntity<Void> update(@RequestBody Categoria objCategoria, @PathVariable Integer id){
		objCategoria.setId(id);
		objCategoria = service.update(objCategoria);
		return ResponseEntity.noContent().build();
	}

}
