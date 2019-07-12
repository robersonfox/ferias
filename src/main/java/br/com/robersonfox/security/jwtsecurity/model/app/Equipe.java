/*
 * Created Date: 12/07/2019
 * Author: robersonfox
 * 
 * Copyright (c) 2019
 */

package br.com.robersonfox.security.jwtsecurity.model.app;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Equipe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;
    
    private Date dataCadastro;

    public Equipe() {
    }

    public Equipe(Long id, String nome, Date dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.dataCadastro = dataCadastro;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataCadastro() {
        return this.dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Equipe id(Long id) {
        this.id = id;
        return this;
    }

    public Equipe nome(String nome) {
        this.nome = nome;
        return this;
    }

    public Equipe dataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Equipe)) {
            return false;
        }
        Equipe equipe = (Equipe) o;
        return Objects.equals(id, equipe.id) && Objects.equals(nome, equipe.nome) && Objects.equals(dataCadastro, equipe.dataCadastro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, dataCadastro);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", nome='" + getNome() + "'" +
            ", dataCadastro='" + getDataCadastro() + "'" +
            "}";
    }

}