package infra.auth;

import domain.entities.usuarios.colaborador;

/**
 * Gerencia a sessão do usuário logado no sistema.
 * Pode armazenar qualquer entidade que estenda colaborador,
 * como Funcionário, Administrador, etc.
 */
public class Session {
    private static colaborador pessoaLogado;

    public static void setPessoaLogado(colaborador pessoa) {
        pessoaLogado = pessoa;
    }

    public static colaborador getPessoaLogado() {
        return pessoaLogado;
    }

    public static void logout() {
        pessoaLogado = null;
    }
}
