package br.com.ejrocha.api.entity;

public enum UsuarioPermissoes {

    ADMIN("admin"),
    USER("user");

    private String permissao;

    UsuarioPermissoes(String permissao){
        this.permissao = permissao;
    }

    public String getPermissao() {
        return permissao;
    }
}
