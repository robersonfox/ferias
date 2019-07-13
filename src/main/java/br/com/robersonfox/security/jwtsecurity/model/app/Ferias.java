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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Ferias implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;

    private Date dataInicio;
    private Date dataFim;


    public Ferias() {
    }

    public Ferias(Long id, Pessoa pessoa, Date dataInicio, Date dataFim) {
        this.id = id;
        this.pessoa = pessoa;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pessoa getPessoa() {
        return this.pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Date getDataInicio() {
        return this.dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return this.dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public Ferias id(Long id) {
        this.id = id;
        return this;
    }

    public Ferias pessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    public Ferias dataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
        return this;
    }

    public Ferias dataFim(Date dataFim) {
        this.dataFim = dataFim;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Ferias)) {
            return false;
        }
        Ferias ferias = (Ferias) o;
        return Objects.equals(id, ferias.id) && Objects.equals(pessoa, ferias.pessoa) && Objects.equals(dataInicio, ferias.dataInicio) && Objects.equals(dataFim, ferias.dataFim);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pessoa, dataInicio, dataFim);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", pessoa='" + getPessoa() + "'" +
            ", dataInicio='" + getDataInicio() + "'" +
            ", dataFim='" + getDataFim() + "'" +
            "}";
    }

}