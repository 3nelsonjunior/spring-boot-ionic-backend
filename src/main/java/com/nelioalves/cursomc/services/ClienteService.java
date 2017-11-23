package com.nelioalves.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.dto.ClienteDTO;
import com.nelioalves.cursomc.repositories.ClienteRepository;
import com.nelioalves.cursomc.services.exceptions.DataIntegrityException;
import com.nelioalves.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo; //acessando o repository
	
	//serviço que busca categoria
	public Cliente find(Integer id) {
		Cliente obj = repo.findOne(id);
		if(obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id.: "+ id
					+ ", Tipo.: " + Cliente.class.getName());
		}
		return obj;
	}
	
	
	public List<Cliente> findAll(){
		return repo.findAll();
	}
	
	public Cliente update(Cliente objCliente) {
		Cliente newObj = find(objCliente.getId()); //verifica se o id existe
		updateData(newObj, objCliente);
		return repo.save(objCliente);
	}

	public void delete(Integer id) {
		find(id); //verifica se o id existe
		try {
			repo.delete(id);
		}
		catch(DataIntegrityViolationException e){
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas!");
		}		
	}
	
	//Paginação - começa com 0 a contagem
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	//instacia uma categoria a partir de uma DTO
	public Cliente fromDTO(ClienteDTO objClienteDTO) {
		return new Cliente(objClienteDTO.getId(), objClienteDTO.getNome(), objClienteDTO.getEmail(), null, null);
	}
	
	private void updateData(Cliente newObj, Cliente objCliente) {
		newObj.setNome(objCliente.getNome());
		newObj.setEmail(objCliente.getEmail());
	}

}
