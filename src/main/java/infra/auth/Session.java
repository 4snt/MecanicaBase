package infra.auth;

import domain.entities.usuarios.Colaborador;

/**
 * Gerencia a sessão do usuário logado no sistema.
 * Pode armazenar qualquer entidade que estenda colaborador,
 * como Funcionário, Administrador, etc.
 */
public class Session {
    private static Colaborador pessoaLogado;

    public static void setPessoaLogado(Colaborador pessoa) {
        pessoaLogado = pessoa;
    }

    public static Colaborador getPessoaLogado() {
        return pessoaLogado;
    }

    public static void logout() {
        pessoaLogado = null;
    }
}
