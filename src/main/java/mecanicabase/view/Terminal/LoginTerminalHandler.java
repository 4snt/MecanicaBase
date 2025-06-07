package mecanicabase.view.Terminal;

import java.util.Scanner;
import mecanicabase.infra.auth.Session;
import mecanicabase.model.usuarios.Colaborador;
import mecanicabase.service.usuarios.administrador.LoginAdministradorUseCase;
import mecanicabase.service.usuarios.funcionario.LoginFuncionarioUseCase;

/**
 * Handler responsável por realizar o processo de login via terminal. Permite
 * login como Funcionário ou Administrador com verificação de e-mail e senha.
 */
public class LoginTerminalHandler {

    private final Scanner scanner;

    /**
     * Constrói o manipulador de login com um scanner para entrada de dados.
     *
     * @param scanner Scanner para capturar entradas do usuário no terminal.
     */
    public LoginTerminalHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Executa o processo de login para Funcionário ou Administrador.
     *
     * @return true se o login for bem-sucedido, false caso contrário.
     */
    public boolean login() {
        System.out.println("=== TIPO DE LOGIN ===");
        System.out.println("1 - Funcionário");
        System.out.println("2 - Administrador");
        System.out.print("Escolha: ");
        String tipo = scanner.nextLine();

        System.out.print("E-mail: ");
        String email = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        boolean sucesso = false;

        if (tipo.equals("1")) {
            sucesso = new LoginFuncionarioUseCase().login(email, senha);
        } else if (tipo.equals("2")) {
            sucesso = new LoginAdministradorUseCase().login(email, senha);
        } else {
            System.out.println("Tipo inválido.");
            return false;
        }

        if (sucesso) {
            Colaborador pessoa = Session.getPessoaLogado();
            System.out.println("Login realizado com sucesso. Bem-vindo, " + pessoa.getNome() + "!");
        } else {
            System.out.println("Credenciais inválidas.");
        }

        return sucesso;
    }
}
