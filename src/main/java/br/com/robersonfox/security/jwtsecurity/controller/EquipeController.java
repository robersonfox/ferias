/*
 * Created Date: 12/07/2019
 * Author: robersonfox
 * 
 * Copyright (c) 2019
*/

package br.com.robersonfox.security.jwtsecurity.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.robersonfox.security.jwtsecurity.model.app.Equipe;
import br.com.robersonfox.security.jwtsecurity.repository.EquipeRepo;

@RestController
@RequestMapping("/rest/equipe")
public class EquipeController {

    @Autowired
    private EquipeRepo equipeRepo;

    @PutMapping
    public ResponseEntity<Equipe> inserir(@RequestBody final Equipe equipe) {
        Equipe e = null;
        
        if (equipe.getId() != null) {
            Equipe dummyEquipe = new Equipe(equipe.getId(), null, null);
            Example<Equipe> criterio = Example.of(dummyEquipe);
            e = equipeRepo.findOne(criterio);

            equipe.setId(e.getId());
            equipe.setDataCadastro(e.getDataCadastro());
        } else {
            equipe.setDataCadastro(new Date());
        }

        e = equipeRepo.save(equipe);

        return ResponseEntity.ok(e);
    }

    @GetMapping
    public ResponseEntity<List<Equipe>> getAll() {
        return ResponseEntity.ok(equipeRepo.findAll());
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<Equipe> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(equipeRepo.findOne(id));
    }
}
