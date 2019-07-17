/*
 * Created Date: 12/07/2019
 * Author: robersonfox
 * 
 * Copyright (c) 2019
*/

package br.com.robersonfox.security.jwtsecurity.controller;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.robersonfox.security.jwtsecurity.helper.Criptador;
import br.com.robersonfox.security.jwtsecurity.helper.SendEmailHelper;
import br.com.robersonfox.security.jwtsecurity.helper.ZXingHelper;
import br.com.robersonfox.security.jwtsecurity.model.app.Endereco;
import br.com.robersonfox.security.jwtsecurity.model.app.Equipe;
import br.com.robersonfox.security.jwtsecurity.model.app.Login;
import br.com.robersonfox.security.jwtsecurity.model.app.Pessoa;
import br.com.robersonfox.security.jwtsecurity.repository.EnderecoRepo;
import br.com.robersonfox.security.jwtsecurity.repository.EquipeRepo;
import br.com.robersonfox.security.jwtsecurity.repository.LoginRepo;
import br.com.robersonfox.security.jwtsecurity.repository.PessoaRepo;

@RestController
@RequestMapping("/rest/pessoa")
public class PessoaController {
    protected static final Logger logger = Logger.getLogger(PessoaController.class);

    @Autowired
    private PessoaRepo pessoaRepo;

    @Autowired
    private EnderecoRepo enderecoRepo;

    @Autowired
    private EquipeRepo equipeRepo;

    @Autowired
    private LoginRepo loginRepo;

    @PutMapping
    public ResponseEntity<?> inserirPessoa(@RequestBody final Pessoa pessoa) throws NoSuchAlgorithmException {
        String matricula = (String) createId();

        Endereco endereco = trataEndereco(pessoa.getEndereco());
        Equipe equipe = validaEquipe(pessoa.getEquipe());

        if (equipe == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Equipe não encontrada");
        }

        pessoa.setEquipe(equipe);
        pessoa.setId(null);
        pessoa.setMatricula(Long.parseLong(matricula));
        pessoa.setEndereco(endereco);
        pessoa.setDataContratacao(new Date());
        
        Login login = null;

        try {
            login = tratalogin(pessoa.getLogin());
        } catch (Exception e) {
            e.printStackTrace();
        }

        pessoa.setLogin(login);

        Pessoa p = pessoaRepo.save(pessoa);

        if (p != null && p.getId() != null) {
            SendEmailHelper sendEmail = new SendEmailHelper();
            p.getLogin().setSenha(null);

            try {
                sendEmail.sendEmailWithAttachment(pessoa.getLogin().getEmail(), p.getId());
            } catch (MessagingException e) {
                ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("O funcionário foi cadastrado, porém, não foi possível enviar email para este.");
            } catch (IOException e) {
                logger.error(e);
            }

            return ResponseEntity.status(HttpStatus.OK).body(p);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não foi possível inserir");
        }
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

    @GetMapping(value = "qrcode/{id}")
    public void qrcode(@PathVariable("id") Long id, HttpServletResponse response) throws Exception {
        Pessoa p = pessoaRepo.findOne(id);

        ZXingHelper.getPNG(p.toString(), response);
    }

    private static synchronized Serializable createId() {
        Long verificador = System.currentTimeMillis() / 1000;
        String UID = String.valueOf(verificador);

        do {
            verificador = geraValidator(verificador);
        } while (verificador.toString().length() > 1);

        return String.valueOf(UID + "" + verificador);
    }

    private static Long geraValidator(Long verificador) {
        Long resultado = 0L;
        for (int i = 0; i < verificador.toString().length(); i++) {
            char v = verificador.toString().charAt(i);
            Long vl = Long.parseLong(String.valueOf(v));
            resultado = resultado + vl;
        }
        return resultado;
    }

    private Endereco trataEndereco(Endereco endereco) {
        Example<Endereco> criterio = Example.of(endereco);
        Endereco e = enderecoRepo.findOne(criterio);

        // Atualiza dados
        if (e != null) {
            endereco.setId(e.getId());
        }

        return enderecoRepo.save(endereco);
    }

    private Equipe validaEquipe(Equipe equipe) {
        Example<Equipe> criterio = Example.of(equipe);
        Equipe e = equipeRepo.findOne(criterio);

        if (e == null) {
            return null;
        }

        return e;
    }

    private Login tratalogin(Login login) throws NoSuchAlgorithmException {
        Criptador criptador = new Criptador();
        String senha = criptador.encoder(login.getSenha());
 
        login.setSenha(senha);
        
		return loginRepo.save(login);
	}
}
