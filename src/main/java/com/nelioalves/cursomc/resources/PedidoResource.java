package com.nelioalves.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nelioalves.cursomc.domain.Pedido;
import com.nelioalves.cursomc.services.PedidoService;

@RestController
@RequestMapping(value="/pedidos")
public class PedidoResource {
	
	@Autowired
	private PedidoService service; //acessando o serviço
	
	//value incluido para acesssar via 'categorias/id' no navegador
	@RequestMapping(value="/{id}", method=RequestMethod.GET) 
	//retorna um objeto do tipo Respose Entity, já encapsula varias informações de uma resposta HTTP para um serviço REST  
	//find recebe um id(PathVariable = para o id da URL ir para o id da variavel )
	public ResponseEntity<Pedido> find(@PathVariable Integer id) { 
		Pedido obj = service.find(id);
		return ResponseEntity.ok().body(obj); //body = corpo da resposta vai o objeto
	}

}
