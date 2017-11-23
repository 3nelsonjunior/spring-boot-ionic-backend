package com.nelioalves.cursomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.dto.ClienteDTO;
import com.nelioalves.cursomc.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService service; //acessando o serviço
	
	//value incluido para acesssar via 'categorias/id' no navegador
	@RequestMapping(value="/{id}", method=RequestMethod.GET) 
	//retorna um objeto do tipo Respose Entity, já encapsula varias informações de uma resposta HTTP para um serviço REST  
	//find recebe um id(PathVariable = para o id da URL ir para o id da variavel )
	public ResponseEntity<Cliente> find(@PathVariable Integer id) { 
		Cliente obj = service.find(id);
		return ResponseEntity.ok().body(obj); //body = corpo da resposta vai o objeto
	}
	
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll() { 
		List<Cliente> objListaCliente = service.findAll();
		List<ClienteDTO> objListaClienteDTO = objListaCliente.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(objListaClienteDTO); //body = corpo da resposta vai o objeto
	}
	
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(
			//requestParam para opcional, definindo os valores default
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy,
			@RequestParam(value="direction", defaultValue="ASC") String direction) { 
		Page<Cliente> objListaCliente = service.findPage(page, linesPerPage, orderBy, direction);
		Page<ClienteDTO> objListaClienteDTO = objListaCliente.map(obj -> new ClienteDTO(obj)); //a partir do Java 8, não precisa converter DTO, ja faz automático
		return ResponseEntity.ok().body(objListaClienteDTO); //body = corpo da resposta vai o objeto
	}
	
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT) 
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objClienteDTO, @PathVariable Integer id){
		Cliente objCliente = service.fromDTO(objClienteDTO);
		objCliente.setId(id);
		objCliente = service.update(objCliente);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
