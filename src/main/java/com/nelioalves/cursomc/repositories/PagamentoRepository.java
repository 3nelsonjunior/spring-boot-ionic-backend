package com.nelioalves.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nelioalves.cursomc.domain.Pagamento;

//Interface que herda JPARepository(capaz de acessar um objeto de um tipo, com base num tipo de dado identificador)
@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer>{

}
