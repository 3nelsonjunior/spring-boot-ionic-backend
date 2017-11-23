package com.nelioalves.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.nelioalves.cursomc.domain.Cidade;
import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.domain.Endereco;
import com.nelioalves.cursomc.domain.enuns.TipoCliente;
import com.nelioalves.cursomc.dto.ClienteDTO;
import com.nelioalves.cursomc.dto.ClienteNewDTO;
import com.nelioalves.cursomc.repositories.CidadeRepository;
import com.nelioalves.cursomc.repositories.ClienteRepository;
import com.nelioalves.cursomc.repositories.EnderecoRepository;
import com.nelioalves.cursomc.services.exceptions.DataIntegrityException;
import com.nelioalves.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo; //acessando o repository
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	//serviço que busca categoria
	public Cliente find(Integer id) {
		Cliente obj = repo.findOne(id);
		if(obj == null) {
			throw new ObjectNotFoundException("Pedido não encontrado! Id.: "+ id
					+ ", Tipo.: " + Cliente.class.getName());
		}
		return obj;
	}
	
	
	public List<Cliente> findAll(){
		return repo.findAll();
	}
	
	public Cliente insert(Cliente obj) {
		obj.setId(null); //garantir id vazio, devido auto incrementação
		obj = repo.save(obj);
		enderecoRepository.save(obj.getEnderecos());
		return obj; 
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
			throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionados!");
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
	
	public Cliente fromDTO(ClienteNewDTO objDTO) {
		Cliente cli = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfOuCnpj(), TipoCliente.toEnum(objDTO.getTipo()));
		Cidade cid = cidadeRepository.findOne(objDTO.getCidadeId());
		Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(), objDTO.getBairro(), objDTO.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDTO.getTelefone1());
		if(objDTO.getTelefone2() != null){
			cli.getTelefones().add(objDTO.getTelefone2());
		}
		if(objDTO.getTelefone3() != null){
			cli.getTelefones().add(objDTO.getTelefone3());
		}
		return cli;
	}
	
	private void updateData(Cliente newObj, Cliente objCliente) {
		newObj.setNome(objCliente.getNome());
		newObj.setEmail(objCliente.getEmail());
	}

}
