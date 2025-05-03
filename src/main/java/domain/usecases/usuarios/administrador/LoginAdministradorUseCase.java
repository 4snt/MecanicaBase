package domain.usecases.usuarios.administrador;
import domain.entities.usuarios.Administrador;
import infra.auth.Session;
public class LoginAdministradorUseCase {

    /**
     * Tenta realizar o login de um funcionário com e-mail e senha.
     * @param email E-mail fornecido.
     * @param senha Senha fornecida.
     * @return true se o login for bem-sucedido, false caso contrário.
     */
    public boolean login(String email, String senha) {
        Administrador administrador = Administrador.buscarPorEmail(email);

        String erroDeLogin = "Email ou Senha estão errados";

        if (administrador == null) {
            throw new RuntimeException(erroDeLogin);
        }

        boolean senhaCorreta = administrador.compararSenha(senha);

        if (!senhaCorreta) {
            throw new RuntimeException(erroDeLogin);
        }

        Session.setPessoaLogado(administrador);

        return true;
    }
}
