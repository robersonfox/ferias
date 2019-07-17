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
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;    

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
    private Long matricula;
	
	@NotBlank
	private String nome;
	
	@NotNull
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

	@NotNull
	@ManyToOne
	@JoinColumn(name = "grupo_id")
	private Grupo grupo;

	@JsonIgnoreProperties({"pessoa"})
	@OneToMany(mappedBy = "pessoa")
	private Set<Ferias> ferias;
	
	@OneToOne
	@JsonIgnoreProperties({"pessoa"})
	@JoinColumn(name = "login_id")
	private Login login;

	public Pessoa() {}

	public Pessoa(Long id, Long matricula, String nome, Date dataNascimento, Endereco endereco, String email, 
			Date dataContratacao, FotoFuncionario foto, Equipe equipe, 
			Grupo grupo, Set<Ferias> ferias, Login login) {
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
		this.login = login;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMatricula() {
		return this.matricula;
	}

	public void setMatricula(Long matricula) {
		this.matricula = matricula;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataNascimento() {
		return this.dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Endereco getEndereco() {
		return this.endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Date getDataContratacao() {
		return this.dataContratacao;
	}

	public void setDataContratacao(Date dataContratacao) {
		this.dataContratacao = dataContratacao;
	}

	public FotoFuncionario getFoto() {
		return this.foto;
	}

	public void setFoto(FotoFuncionario foto) {
		this.foto = foto;
	}

	public Equipe getEquipe() {
		return this.equipe;
	}

	public void setEquipe(Equipe equipe) {
		this.equipe = equipe;
	}

	public Grupo getGrupo() {
		return this.grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Set<Ferias> getFerias() {
		return this.ferias;
	}

	public void setFerias(Set<Ferias> ferias) {
		this.ferias = ferias;
	}

	public Pessoa id(Long id) {
		this.id = id;
		return this;
	}

	public Pessoa matricula(Long matricula) {
		this.matricula = matricula;
		return this;
	}

	public Pessoa nome(String nome) {
		this.nome = nome;
		return this;
	}

	public Pessoa dataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
		return this;
	}

	public Pessoa endereco(Endereco endereco) {
		this.endereco = endereco;
		return this;
	}

	public Pessoa dataContratacao(Date dataContratacao) {
		this.dataContratacao = dataContratacao;
		return this;
	}

	public Pessoa foto(FotoFuncionario foto) {
		this.foto = foto;
		return this;
	}

	public Pessoa equipe(Equipe equipe) {
		this.equipe = equipe;
		return this;
	}

	public Pessoa grupo(Grupo grupo) {
		this.grupo = grupo;
		return this;
	}


	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}
	
	
	public Pessoa ferias(Set<Ferias> ferias) {
		this.ferias = ferias;
		return this;
	}

	@Override
	public String toString() {
		return "{" +
			" id='" + getId() + "'" +
			", matricula='" + getMatricula() + "'" +
			", nome='" + getNome() + "'" +
			", dataNascimento='" + getDataNascimento() + "'" +
			", endereco='" + getEndereco() + "'" +
			", dataContratacao='" + getDataContratacao() + "'" +
			", foto='" + getFoto() + "'" +
			", equipe='" + getEquipe() + "'" +
			", grupo='" + getGrupo() + "'" +
			", ferias='" + getFerias() + "'" +
			"}";
	}
}
