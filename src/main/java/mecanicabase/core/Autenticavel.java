package mecanicabase.core;

/**
 * Interface que define métodos para autenticação de usuários no sistema.
 * Implementações devem fornecer mecanismos para obter email, comparar e trocar
 * senhas.
 */
public interface Autenticavel {

    /**
     * Retorna o e-mail do usuário autenticável.
     *
     * @return e-mail do usuário
     */
    String getEmail();

    /**
     * Compara a senha informada com a senha armazenada.
     *
     * @param senha senha a ser comparada
     * @return true se a senha for igual, false caso contrário
     */
    boolean compararSenha(String senha);

    /**
     * Define uma nova senha para o usuário.
     *
     * @param novaSenha nova senha a ser definida
     */
    void setSenha(String novaSenha);

    /**
     * Troca a senha do usuário, validando a senha antiga.
     *
     * @param senhaAntiga senha atual do usuário
     * @param novaSenha nova senha a ser definida
     * @return true se a troca for bem-sucedida, false caso contrário
     */
    boolean trocarSenha(String senhaAntiga, String novaSenha);
}
