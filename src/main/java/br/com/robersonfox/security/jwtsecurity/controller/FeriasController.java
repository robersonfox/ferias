/*
 * Created Date: 12/07/2019
 * Author: robersonfox
 * 
 * Copyright (c) 2019
*/

package br.com.robersonfox.security.jwtsecurity.controller;

import java.io.OutputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.robersonfox.security.jwtsecurity.helper.ZXingHelper;
import br.com.robersonfox.security.jwtsecurity.model.app.Equipe;
import br.com.robersonfox.security.jwtsecurity.model.app.Ferias;
import br.com.robersonfox.security.jwtsecurity.model.app.Pessoa;
import br.com.robersonfox.security.jwtsecurity.repository.FeriasRepo;
import br.com.robersonfox.security.jwtsecurity.repository.PessoaRepo;

@RestController
@RequestMapping("/rest/ferias")
public class FeriasController {

    @Autowired
    private FeriasRepo feriasRepo;

    @Autowired
    private PessoaRepo pessoaRepo;

    @PutMapping
    public ResponseEntity<?> inserir(@RequestBody final Ferias ferias, HttpServletResponse response) {
        Date periodoInicial = ferias.getDataInicio();
        Date periodoFinal = ferias.getDataFim();
        Long idFuncionario = ferias.getPessoa().getId();

        Pessoa funcionario = pessoaRepo.findOne(idFuncionario);

        if (funcionario == null || periodoInicial == null || periodoFinal == null) {
        	 return trataError(HttpStatus.BAD_REQUEST, "Informe todos os campos");
        }
        
        Long funcionarioEquipeId = funcionario.getEquipe().getId();
        LocalDate hoje = LocalDate.now();
        LocalDate dataContratacao = convertToLocalDateViaInstant(funcionario.getDataContratacao());
        
        // VERIFICAR SE FUN TEM MAIS QUE UM ANO DE CONTRATAÇÃO
        long diasContratado = ChronoUnit.DAYS.between(dataContratacao, hoje);

        if (diasContratado < 365) {
            return trataError(HttpStatus.BAD_REQUEST, "Funcionário não tem tempo de contratação suficiente. "+ diasContratado);
        }

        // VERIFICAR O NÚMERO DE INTEGRANTES DA EQUIPE
        // SE MENOR OU IRGUAL A QUATRO, NÃO PERMITIR QUE DOIS SAIAM DE FÉRIAS
        Equipe df1 = new Equipe();
        df1.setId(funcionarioEquipeId);
        
        Pessoa p = new Pessoa();
        p.setEquipe(df1);
        Example<Pessoa> criterio1 = Example.of(p);
        List<Pessoa> funEquipe = pessoaRepo.findAll(criterio1);

        if (funEquipe.size() < 5) {
            for (Pessoa pp : funEquipe) {
            	
                Set<Ferias> ff = pp.getFerias();
                for (Ferias f : ff) {
                    if (dataEstaContida(f.getDataInicio(), f.getDataFim(), periodoInicial, periodoFinal)) {
                        // UM MEMBRO DA EQUIPE ESTÁ TIRANDO FÉRIAS NESTE PERÍODO
                   	 return trataError(HttpStatus.BAD_REQUEST, "Um membro da mesma equipe, "+ pp.getNome() +", está tirando férias neste período. ");
                    }
                }
            }
        }
        
        Ferias resultado = feriasRepo.save(ferias);
                
        return ResponseEntity.status(HttpStatus.OK).body(resultado);
    }
    
    @GetMapping("/getbymatricula/{m}")
    public ResponseEntity<?> getallByMatricula(@PathVariable("m") Long matricula) {
    	if (matricula != null && matricula > 0) {
    		Pessoa pessoa = new Pessoa();
    		pessoa.setMatricula(matricula);
    		Example<Pessoa> criterio = Example.of(pessoa);
    		pessoa = pessoaRepo.findOne(criterio);
    		
    		Set<Ferias> fferias = pessoa.getFerias();
    		return ResponseEntity.status(HttpStatus.OK).body(fferias);
    	}
    	
    	return trataError(HttpStatus.BAD_REQUEST, "Informe a matricula");
    }
    
    @GetMapping
    public ResponseEntity<List<Ferias>> getall() {
    	List<Ferias> ferias = feriasRepo.findAll();
    	
        return ResponseEntity.status(HttpStatus.OK).body(ferias);  
    }
    
    @DeleteMapping
    public ResponseEntity<String> delete(@RequestBody final Ferias ferias) {
    	feriasRepo.delete(ferias);
    	return ResponseEntity.status(HttpStatus.OK).body("Removido");
    }
    
    public static ResponseEntity<String> trataError(HttpStatus status, String mensagem) {
    	return ResponseEntity.status(status).body(mensagem);
    }
    
    // Comparar se a data inicial está contida no limite inicial e final
    public Boolean dataEstaContida(Date limiteInicio, Date limiteFim, Date dataInicial, Date dataFinal) {
        return (dataInicial.before(limiteFim) && dataInicial.after(limiteInicio)) 
        		|| (dataFinal.before(limiteFim) && dataFinal.after(limiteInicio));   
    }

    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
          .atZone(ZoneId.systemDefault())
          .toLocalDate();
    }
    
    @RequestMapping(value = "qrcode/{id}", method = RequestMethod.GET)
	public void qrcode(@PathVariable("id") Long id, HttpServletResponse response) throws Exception {
    	Ferias ferias = feriasRepo.findOne(id);
    	
    	ZXingHelper.getPNG(ferias.toString(), response);
	}
}
