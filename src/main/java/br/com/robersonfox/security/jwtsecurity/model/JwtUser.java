package br.com.robersonfox.security.jwtsecurity.model;

public class JwtUser {
    private String userName;
    private long id;
    private String role;
    private Long idPessoa;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Long idPessoa) {
        this.idPessoa = idPessoa;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public long getId() {
        return id;
    }

    public String getRole() {
        return role;
    }
}
