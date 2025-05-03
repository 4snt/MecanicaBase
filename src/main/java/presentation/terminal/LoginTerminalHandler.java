package presentation.terminal;

import java.util.Scanner;

import domain.entities.usuarios.Colaborador;
import domain.usecases.usuarios.administrador.LoginAdministradorUseCase;
import domain.usecases.usuarios.funcionario.LoginFuncionarioUseCase;
import infra.auth.Session;

public class LoginTerminalHandler {
    private final Scanner scanner;

    public LoginTerminalHandler(Scanner scanner) {
        this.scanner = scanner;
    }

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

        switch (tipo) {
            case "1" -> sucesso = new LoginFuncionarioUseCase().login(email, senha);
            case "2" -> sucesso = new LoginAdministradorUseCase().login(email, senha);
            default -> {
                System.out.println("Tipo inválido.");
                return false;
            }
        }

        if (sucesso) {
            Colaborador pessoa = Session.getPessoaLogado();
            System.out.println("Login realizado com sucesso. Bem-vindo, " + pessoa.getNome() + "!");
            return true;
        } else {
            System.out.println("Credenciais inválidas.");
            return false;
        }
    }
}
