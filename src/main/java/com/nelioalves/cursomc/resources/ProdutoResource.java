package com.nelioalves.cursomc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nelioalves.cursomc.domain.Produto;
import com.nelioalves.cursomc.dto.ProdutoDTO;
import com.nelioalves.cursomc.resources.utils.URL;
import com.nelioalves.cursomc.services.ProdutoService;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService service; //acessando o serviço
	
	//value incluido para acesssar via 'categorias/id' no navegador
	@RequestMapping(value="/{id}", method=RequestMethod.GET) 
	//retorna um objeto do tipo Respose Entity, já encapsula varias informações de uma resposta HTTP para um serviço REST  
	//find recebe um id(PathVariable = para o id da URL ir para o id da variavel )
	public ResponseEntity<Produto> find(@PathVariable Integer id) { 
		Produto obj = service.find(id);
		return ResponseEntity.ok().body(obj); //body = corpo da resposta vai o objeto
	}
	
	
	@RequestMapping( method=RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			//requestParam para opcional, definindo os valores default
			@RequestParam(value="nome", defaultValue="") String nome,
			@RequestParam(value="categorias", defaultValue="") String categorias,
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy,
			@RequestParam(value="direction", defaultValue="ASC") String direction) { 
		String nomeDecoded = URL.decodeParam(nome);
		List<Integer> ids = URL.decodeIntList(categorias);
		Page<Produto> list = service.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
		Page<ProdutoDTO> listDTO = list.map(obj -> new ProdutoDTO(obj)); //a partir do Java 8, não precisa converter DTO, ja faz automático
		return ResponseEntity.ok().body(listDTO); //body = corpo da resposta vai o objeto
	}

}
