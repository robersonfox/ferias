/*
 * Created Date: 12/07/2019
 * Author: robersonfox
 * 
 * Copyright (c) 2019
*/

package br.com.robersonfox.security.jwtsecurity.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.robersonfox.security.jwtsecurity.helper.ZXingHelper;
import br.com.robersonfox.security.jwtsecurity.model.app.Pessoa;
import br.com.robersonfox.security.jwtsecurity.repository.PessoaRepo;

@RestController
@RequestMapping("/rest/pessoa")
public class PessoaController {

    @Autowired
    private PessoaRepo pessoaRepo;
    
    // private JwtValidator validator = new JwtValidator();

    @GetMapping
    public List<Pessoa> getAll(HttpServletRequest httpServletRequest) {
        return pessoaRepo.findAll();
    }
    
    @GetMapping("/sairaodeferias/{xMeses}")
    public List<Pessoa> getFunSairaoFerias(@PathVariable("xMeses") Integer xMeses) {
    	Integer prazo = 24 - xMeses;
    	
    	List<Pessoa> funcionarios = pessoaRepo.findFuncionariosSairaoDeFerias(prazo);
    	return funcionarios;
    }
    
    @RequestMapping(value = "qrcode/{id}", method = RequestMethod.GET)
	public void qrcode(@PathVariable("id") Long id, HttpServletResponse response) throws Exception {
    	Pessoa p = pessoaRepo.findOne(id);
    	
    	ZXingHelper.getPNG(p.toString(), response);
	}
}

