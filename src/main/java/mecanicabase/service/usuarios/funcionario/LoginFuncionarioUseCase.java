package mecanicabase.service.usuarios.funcionario;

import mecanicabase.infra.auth.Session;
import mecanicabase.model.usuarios.Funcionario;

public class LoginFuncionarioUseCase {

    /**
     * Tenta realizar o login de um funcionário com e-mail e senha.
     *
     * @param email E-mail fornecido.
     * @param senha Senha fornecida.
     * @return true se o login for bem-sucedido, false caso contrário.
     */
    public boolean login(String email, String senha) {
        for (Funcionario funcionario : Funcionario.instances) {
            if (funcionario.getEmail().equalsIgnoreCase(email)
                    && funcionario.compararSenha(senha)) {
                Session.setPessoaLogado(funcionario);
                return true;
            }
        }
        return false;
    }
}
