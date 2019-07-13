/*
 * Created Date: 12/07/2019
 * Author: robersonfox
 * 
 * Copyright (c) 2019
*/

package br.com.robersonfox.security.jwtsecurity.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.robersonfox.security.jwtsecurity.model.app.Ferias;
import br.com.robersonfox.security.jwtsecurity.model.app.Pessoa;
import br.com.robersonfox.security.jwtsecurity.repository.EquipeRepo;
import br.com.robersonfox.security.jwtsecurity.repository.FeriasRepo;
import br.com.robersonfox.security.jwtsecurity.repository.PessoaRepo;

@RestController
@RequestMapping("/rest/ferias")
public class FeriasController {

    @Autowired
    private FeriasRepo feriasRepo;

    @Autowired
    private PessoaRepo pessoaRepo;

    @Autowired
    private EquipeRepo equipeRepo;

    @PutMapping
    public ResponseEntity<String> inserir(@RequestBody final Ferias ferias) {
        LocalDate periodoInicial = convertToLocalDateViaInstant(ferias.getDataInicio());
        LocalDate periodoFinal = convertToLocalDateViaInstant(ferias.getDataFim());
        Long idFuncionario = ferias.getPessoa().getId();

        Pessoa funcionario = pessoaRepo.findOne(idFuncionario);

        if (funcionario != null) {
            // VERIFICAR SE FUN TEM MAIS QUE UM ANO DE CONTRATAÇÃO
            LocalDate hoje = LocalDate.now();
            LocalDate dataContratacao = convertToLocalDateViaInstant(funcionario.getDataContratacao());

            long diasContratado = ChronoUnit.DAYS.between(dataContratacao, hoje);

            if (diasContratado < 365) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Funcionário não tem tempo de contratação suficiente. " + diasContratado);
            }

            // VERIFICAR O NÚMERO DE INTEGRANTES DA EQUIPE
            // SE MENOR OU IRGUAL A QUATRO, NÃO PERMITIR QUE DOIS SAIAM DE FÉRIAS
            Equipe equipe = equipeRepo.findOne(funcionario.getEquipe().getId());
            


        }



        return null;
    }

    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
          .atZone(ZoneId.systemDefault())
          .toLocalDate();
    }

 
}
