/*
 * Created Date: 12/07/2019
 * Author: robersonfox
 * 
 * Copyright (c) 2019
 */

package br.com.robersonfox.security.jwtsecurity.model.app;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Login implements Serializable {
    private static final long serialVersionUID = 1L;    

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String login;
    private String senha;
    
    @OneToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;

    public Login() {
    }

    public Login(Long id, String login, String senha, Pessoa pessoa) {
        this.id = id;
        this.login = login;
        this.senha = senha;
        this.pessoa = pessoa;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Pessoa getPessoa() {
        return this.pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Login id(Long id) {
        this.id = id;
        return this;
    }

    public Login login(String login) {
        this.login = login;
        return this;
    }

    public Login senha(String senha) {
        this.senha = senha;
        return this;
    }

    public Login pessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Login)) {
            return false;
        }
        Login login = (Login) o;
        return Objects.equals(id, login.id) && Objects.equals(login, login.login) && Objects.equals(senha, login.senha) && Objects.equals(pessoa, login.pessoa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, senha, pessoa);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", login='" + getLogin() + "'" +
            ", senha='" + getSenha() + "'" +
            ", pessoa='" + getPessoa() + "'" +
            "}";
    }

}
