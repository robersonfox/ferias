/*
 * Created Date: 12/07/2019
 * Author: robersonfox
 * 
 * Copyright (c) 2019
 */

package br.com.robersonfox.security.jwtsecurity.model.app;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;    

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long matricula;
    
    private String nome;
    private Date dataNascimento;

    @ManyToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    private Date dataContratacao;

    @ManyToOne
    @JoinColumn(name = "foto_id")
    private FotoFuncionario foto;    

    @ManyToOne
    @JoinColumn(name = "equipe_id")
    private Equipe equipe;

    @ManyToOne
    @JoinColumn(name = "grupo_id")
    private Grupo grupo;

    @JsonIgnoreProperties({"pessoa"})
    @OneToMany(mappedBy = "pessoa")
    private Set<Ferias> ferias;


    public Pessoa() {
    }

    public Pessoa(Long id, Long matricula, String nome, Date dataNascimento, Endereco endereco, Date dataContratacao, FotoFuncionario foto, Equipe equipe, Grupo grupo, Set<Ferias> ferias) {
        this.id = id;
        this.matricula = matricula;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
        this.dataContratacao = dataContratacao;
        this.foto = foto;
        this.equipe = equipe;
        this.grupo = grupo;
        this.ferias = ferias;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMatricula() {
		return matricula;
	}

	public void setMatricula(Long matricula) {
		this.matricula = matricula;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Date getDataContratacao() {
		return dataContratacao;
	}

	public void setDataContratacao(Date dataContratacao) {
		this.dataContratacao = dataContratacao;
	}

	public FotoFuncionario getFoto() {
		return foto;
	}

	public void setFoto(FotoFuncionario foto) {
		this.foto = foto;
	}

	public Equipe getEquipe() {
		return equipe;
	}

	public void setEquipe(Equipe equipe) {
		this.equipe = equipe;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Set<Ferias> getFerias() {
		return ferias;
	}

	public void setFerias(Set<Ferias> ferias) {
		this.ferias = ferias;
	}

	 
    
     
 
}
