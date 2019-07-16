package br.com.robersonfox.security.jwtsecurity.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.robersonfox.security.jwtsecurity.helper.ZXingHelper;
import br.com.robersonfox.security.jwtsecurity.model.app.Pessoa;
import br.com.robersonfox.security.jwtsecurity.repository.PessoaRepo;

@Controller
@RequestMapping("/qrcode")
public class QrCode {

    @Autowired
    private PessoaRepo pessoaRepo;

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
	public void get(@PathVariable("id") Long id, HttpServletResponse response) throws Exception {
    	Pessoa pessoa = pessoaRepo.findOne(id);
        
        pessoa.setFerias(null);
        pessoa.setFoto(null);

    	ZXingHelper.getPNG(pessoa.toString(), response);
	}
}

