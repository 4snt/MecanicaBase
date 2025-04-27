// Funcionario.java
package model;

public class Funcionario extends Pessoa {
    protected String funcao;
    protected String login;
    protected String senha;

    public Funcionario(String nome, String funcao, String login, String senha) {
        super(nome);
        this.funcao = funcao;
        this.login = login;
        this.senha = senha;
    }

    // Métodos de autenticação etc.
}
