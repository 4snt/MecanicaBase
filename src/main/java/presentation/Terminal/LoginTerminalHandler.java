package presentation.Terminal;

import java.util.Scanner;

import domain.entities.usuarios.Colaborador;
import domain.usecases.usuarios.administrador.LoginAdministradorUseCase;
import domain.usecases.usuarios.funcionario.LoginFuncionarioUseCase;
import infra.auth.Session;

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

        boolean sucesso = switch (tipo) {
            case "1" ->
                new LoginFuncionarioUseCase().login(email, senha);
            case "2" ->
                new LoginAdministradorUseCase().login(email, senha);
            default -> {
                System.out.println("Tipo inválido.");
                yield false;
            }
        };

        if (sucesso) {
            Colaborador pessoa = Session.getPessoaLogado();
            System.out.println("Login realizado com sucesso. Bem-vindo, " + pessoa.getNome() + "!");
        } else {
            System.out.println("Credenciais inválidas.");
        }

        return sucesso;
    }
}
