package domain.usecases.usuarios.administrador;

import domain.entities.usuarios.Administrador;
import infra.auth.Session;

/**
 * Caso de uso responsável por realizar o login de um administrador, validando
 * as credenciais fornecidas.
 */
public class LoginAdministradorUseCase {

    /**
     * Tenta realizar o login de um administrador utilizando email e senha. Se o
     * login for bem-sucedido, o administrador é armazenado na sessão.
     *
     * @param email E-mail fornecido pelo administrador.
     * @param senha Senha fornecida pelo administrador.
     * @return true se o login for bem-sucedido, caso contrário, false.
     * @throws RuntimeException Se o administrador não for encontrado ou a senha
     * estiver incorreta.
     */
    public boolean login(String email, String senha) {
        // Busca o administrador pelo e-mail fornecido
        Administrador administrador = Administrador.buscarPorEmail(email);

        // Mensagem de erro a ser utilizada em caso de falha no login
        String erroDeLogin = "Email ou Senha estão errados";

        // Verifica se o administrador existe
        if (administrador == null) {
            throw new RuntimeException(erroDeLogin);
        }

        // Verifica se a senha fornecida é correta
        boolean senhaCorreta = administrador.compararSenha(senha);

        // Se a senha estiver incorreta, lança uma exceção com a mensagem de erro
        if (!senhaCorreta) {
            throw new RuntimeException(erroDeLogin);
        }

        // Caso o login seja bem-sucedido, o administrador é armazenado na sessão
        Session.setPessoaLogado(administrador);

        // Retorna true indicando que o login foi bem-sucedido
        return true;
    }
}
