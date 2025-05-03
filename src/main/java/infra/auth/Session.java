package infra.auth;

import domain.entities.usuarios.Funcionario;

public class Session {
    private static Funcionario clienteLogado;

    public static void setFuncionarioLogado(Funcionario cliente) {
        clienteLogado = cliente;
    }

    public static Funcionario getFuncionarioLogado() {
        return clienteLogado;
    }

    public static void logout() {
        clienteLogado = null;
    }
}