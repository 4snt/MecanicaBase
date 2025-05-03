package infra.auth;

import domain.entities.usuarios.Colaborador;

/**
 * Classe responsável por gerenciar a sessão do usuário logado no sistema. Pode
 * armazenar qualquer entidade que estenda a classe {@link Colaborador}, como
 * Funcionário, Administrador, etc.
 */
public class Session {

    /**
     * Armazena o usuário logado na sessão. A variável é do tipo
     * {@link Colaborador}, podendo ser um Funcionário, Administrador, ou outro
     * tipo de colaborador.
     */
    private static Colaborador pessoaLogado;

    /**
     * Define a pessoa que está logada no sistema.
     *
     * @param pessoa A pessoa que será armazenada como logada.
     */
    public static void setPessoaLogado(Colaborador pessoa) {
        pessoaLogado = pessoa;
    }

    /**
     * Retorna a pessoa que está logada no sistema.
     *
     * @return A pessoa logada, que é uma instância de {@link Colaborador}.
     */
    public static Colaborador getPessoaLogado() {
        return pessoaLogado;
    }

    /**
     * Realiza o logout do usuário, limpando a sessão.
     */
    public static void logout() {
        pessoaLogado = null;
    }
}
