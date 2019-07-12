/*
 * Created Date: 12/07/2019
 * Author: robersonfox
 * 
 * Copyright (c) 2019
*/

package br.com.robersonfox.security.jwtsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.robersonfox.security.jwtsecurity.model.app.Pessoa;


@Repository
public interface PessoaRepo extends JpaRepository<Pessoa, Long> {
    
}