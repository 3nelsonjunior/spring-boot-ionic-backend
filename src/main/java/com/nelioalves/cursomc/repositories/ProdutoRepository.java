package com.nelioalves.cursomc.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.domain.Produto;

//Interface que herda JPARepository(capaz de acessar um objeto de um tipo, com base num tipo de dado identificador)
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer>{
	//se tirar a query e os paramentros relacionados a query funciona, devido o padrão de nome do método
	@Transactional(readOnly=true)
	@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat"
			+ "	WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
	Page<Produto> findDistinctByNomeContainingAndCategoriasIn(@Param("nome")  String nome, @Param("categorias") List<Categoria> categorias, Pageable pageRequest);
}
