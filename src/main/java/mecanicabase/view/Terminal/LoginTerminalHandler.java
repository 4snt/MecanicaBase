package mecanicabase.view.Terminal;

import java.util.Scanner;
import mecanicabase.infra.auth.Session;
import mecanicabase.model.usuarios.Colaborador;
import mecanicabase.service.usuarios.AdministradorCrud;
import mecanicabase.service.usuarios.FuncionarioCrud;

/**
 * Handler responsável por realizar o processo de login via terminal. Permite
 * login como Funcionário ou Administrador.
 */
public class LoginTerminalHandler {

    private final Scanner scanner;
    private final FuncionarioCrud funcionarioCrud;
    private final AdministradorCrud administradorCrud;

    public LoginTerminalHandler(Scanner scanner, FuncionarioCrud funcionarioCrud, AdministradorCrud administradorCrud) {
        this.scanner = scanner;
        this.funcionarioCrud = funcionarioCrud;
        this.administradorCrud = administradorCrud;
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

        Colaborador colaborador = switch (tipo) {
            case "1" ->
                funcionarioCrud.login(email, senha);
            case "2" ->
                administradorCrud.login(email, senha);
            default -> {
                System.out.println("Tipo inválido.");
                yield null;
            }
        };

        if (colaborador != null) {
            Session.setPessoaLogado(colaborador);
            System.out.println("✅ Login realizado com sucesso. Bem-vindo, " + colaborador.getNome() + "!");
            return true;
        } else {
            System.out.println("❌ Credenciais inválidas.");
            return false;
        }
    }
}
