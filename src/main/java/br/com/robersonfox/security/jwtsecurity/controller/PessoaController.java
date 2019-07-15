/*
 * Created Date: 12/07/2019
 * Author: robersonfox
 * 
 * Copyright (c) 2019
*/

package br.com.robersonfox.security.jwtsecurity.controller;


import java.io.Serializable;
import java.rmi.server.UID;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.robersonfox.security.jwtsecurity.helper.ZXingHelper;
import br.com.robersonfox.security.jwtsecurity.model.app.Pessoa;
import br.com.robersonfox.security.jwtsecurity.repository.PessoaRepo;

@RestController
@RequestMapping("/rest/pessoa")
public class PessoaController {

    @Autowired
    private PessoaRepo pessoaRepo;
    
    // private JwtValidator validator = new JwtValidator();

    @PutMapping
    public ResponseEntity<?> inserirPessoa(@RequestBody final Pessoa pessoa) {
    	String matricula = (String) createId();
    	
    	pessoa.setMatricula(Long.parseLong(matricula));
    	
    	//matricula gerada dia 14/07/19 587201611
    	pessoaRepo.save(pessoa);
    	
    	
    	return null;
    }
    
    @PostMapping
    public ResponseEntity<?> inserirFoto(@RequestParam MultipartFile file) {
    	return null;
    }
    
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
    
    public static synchronized Serializable createId() {
        long now = System.currentTimeMillis();
        
        Long verificador = Long.parseLong(String.valueOf(now));
        
        String UID = String.valueOf(now).substring(5,String.valueOf(now).length());
         
        do {
        	verificador = geraValidator(verificador);
		} while (verificador.toString().length() > 1);
        
        
        return String.valueOf(UID + "" + verificador);
    }
    
    protected static Long geraValidator(Long verificador) {
    	Long resultado = 0L;
        for (int i = 0; i < verificador.toString().length(); i++) {
        	char v = verificador.toString().charAt(i);
        	Long vl = Long.parseLong(String.valueOf(v));
        	resultado = resultado + vl;
        }
        return resultado;
    }
}

