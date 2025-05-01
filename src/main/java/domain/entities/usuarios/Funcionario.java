// Funcionario.java
package domain.entities.usuarios;

import java.util.ArrayList;
import java.util.List;

public class Funcionario extends Pessoa {
    private static List<Funcionario> instances = new ArrayList<>();
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
