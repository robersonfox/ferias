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
public class FotoFuncionario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String caminho;

    private Date dataCadastro;

    public FotoFuncionario() {
    }

    public FotoFuncionario(Long id, String caminho, Date dataCadastro) {
        this.id = id;
        this.caminho = caminho;
        this.dataCadastro = dataCadastro;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCaminho() {
        return this.caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public Date getDataCadastro() {
        return this.dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public FotoFuncionario id(Long id) {
        this.id = id;
        return this;
    }

    public FotoFuncionario caminho(String caminho) {
        this.caminho = caminho;
        return this;
    }

    public FotoFuncionario dataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof FotoFuncionario)) {
            return false;
        }
        FotoFuncionario fotoFuncionario = (FotoFuncionario) o;
        return Objects.equals(id, fotoFuncionario.id) && Objects.equals(caminho, fotoFuncionario.caminho) && Objects.equals(dataCadastro, fotoFuncionario.dataCadastro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, caminho, dataCadastro);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", caminho='" + getCaminho() + "'" +
            ", dataCadastro='" + getDataCadastro() + "'" +
            "}";
    }

}