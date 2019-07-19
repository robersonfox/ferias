/*
 * Created Date: 12/07/2019
 * Author: robersonfox
 * 
 * Copyright (c) 2019
*/

package br.com.robersonfox.security.jwtsecurity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.robersonfox.security.jwtsecurity.model.app.Pessoa;


@Repository
public interface PessoaRepo extends JpaRepository<Pessoa, Long> {
	//TODO Inserir depois funcionários que nunca tiratam férias e tem x período de contratação
	@Query(value = "SELECT * FROM ferias.pessoa p \n" 
			+ " LEFT join ferias.ferias f on f.pessoa_id = p.id\n" 
			+ " WHERE f.data_inicio BETWEEN sysdate() and (DATE_ADD(sysdate(),INTERVAL ?1 month))"
			+ " ORDER BY f.data_inicio desc", nativeQuery = true)
	List<Pessoa> findFuncionariosSairaoDeFerias(Integer prazo);
}