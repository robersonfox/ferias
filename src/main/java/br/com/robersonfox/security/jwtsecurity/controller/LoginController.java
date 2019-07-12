/*
 * Created Date: 12/07/2019
 * Author: robersonfox
 * 
 * Copyright (c) 2019
*/

package br.com.robersonfox.security.jwtsecurity.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping
    public ResponseEntity<String> doLogin(@RequestBody final Login user) {

        String usuario = user.getLogin();
        String senha = user.getSenha();
        
        Login dummyLogin = new Login();
        dummyLogin.setLogin(usuario);
        dummyLogin.setSenha(senha);

        Example<Login> criterio = Example.of(dummyLogin);
        Login login = loginRepo.findOne(criterio);

        if (login != null) {
            JwtUser jwtUser = new JwtUser();
            jwtUser.setId(login.getId());
            jwtUser.setUserName(login.getLogin());
            jwtUser.setRole(login.getPessoa().getGrupo().getNome());
            
            String jwt = jwtGenerator.generate(jwtUser);

            return ResponseEntity.status(HttpStatus.OK).header("Autorization", "Token " + jwt).build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}