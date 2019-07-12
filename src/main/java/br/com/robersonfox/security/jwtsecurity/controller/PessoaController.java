/*
 * Created Date: 12/07/2019
 * Author: robersonfox
 * 
 * Copyright (c) 2019
*/

package br.com.robersonfox.security.jwtsecurity.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.robersonfox.security.jwtsecurity.model.app.Pessoa;
import br.com.robersonfox.security.jwtsecurity.repository.PessoaRepo;

@RestController
@RequestMapping("/rest/pessoa")
public class PessoaController {

    @Autowired
    private PessoaRepo pessoaRepo;

    @GetMapping
    public List<Pessoa> getAll() {
        return pessoaRepo.findAll();
    }


}