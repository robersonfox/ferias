/*
 * Created Date: 12/07/2019
 * Author: robersonfox
 * 
 * Copyright (c) 2019
*/

package br.com.robersonfox.security.jwtsecurity.controller;


import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.robersonfox.security.jwtsecurity.helper.Criptador;
import br.com.robersonfox.security.jwtsecurity.model.JwtUser;
import br.com.robersonfox.security.jwtsecurity.model.app.Login;
import br.com.robersonfox.security.jwtsecurity.repository.LoginRepo;
import br.com.robersonfox.security.jwtsecurity.security.JwtGenerator;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginRepo loginRepo;

    private JwtGenerator jwtGenerator;

    public LoginController(JwtGenerator jwtGenerator) {
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> doLogin(@RequestBody final Login user) {
        Criptador criptador = new Criptador();

        Login dummyLogin = new Login();
        String senha = criptador.encoder(user.getSenha());
        
        dummyLogin.setEmail(user.getEmail());
        dummyLogin.setSenha(senha);

        Example<Login> criterio = Example.of(dummyLogin);
        Login login = loginRepo.findOne(criterio);

        if (login != null) {
            Map<String, String> map = new HashMap<>();
            
            Long idPessoa = login.getPessoa().getId();
            String role = login.getPessoa().getGrupo().getNome();

            JwtUser jwtUser = new JwtUser();
            jwtUser.setId(login.getId());
            jwtUser.setUserName(login.getEmail());
            jwtUser.setRole(role);
            jwtUser.setIdPessoa(idPessoa);
            
            String jwt = jwtGenerator.generate(jwtUser);
            
            HttpHeaders headers = new HttpHeaders();
            headers.add("Set-Cookie","Tokiuz=" + "Token " + jwt);
            headers.add("Autorization", "Token " + jwt);
           
            map.put("Tokiuz", jwt);

            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(map);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}