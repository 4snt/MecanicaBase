package domain.usecases.usuarios.funcionario;

import domain.entities.usuarios.Funcionario;
import infra.auth.Session;

public class LoginFuncionarioUseCase {

    /**
     * Tenta realizar o login de um funcionário com e-mail e senha.
     * @param email E-mail fornecido.
     * @param senha Senha fornecida.
     * @return true se o login for bem-sucedido, false caso contrário.
     */
    public boolean login(String email, String senha) {
        for (Funcionario funcionario : Funcionario.instances) {
            if (funcionario.getEmail().equalsIgnoreCase(email) &&
                funcionario.compararSenha(senha)) {
                Session.setFuncionarioLogado(funcionario);
                return true;
            }
        }
        return false;
    }
}
