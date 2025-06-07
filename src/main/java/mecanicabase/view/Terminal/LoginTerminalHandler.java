package mecanicabase.view.Terminal;

import java.util.Scanner;
import mecanicabase.infra.auth.Session;
import mecanicabase.model.usuarios.Colaborador;
import mecanicabase.service.usuarios.AdministradorCrud;
import mecanicabase.service.usuarios.FuncionarioCrud;

/**
 * Handler responsável por realizar o processo de login via terminal. Permite
 * login como Funcionário ou Administrador com verificação de e-mail e senha.
 */
public class LoginTerminalHandler {

    private final Scanner scanner;
    private final FuncionarioCrud funcionarioCrud = new FuncionarioCrud();
    private final AdministradorCrud administradorCrud = new AdministradorCrud();

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

        Colaborador colaborador = null;

        if (tipo.equals("1")) {
            colaborador = funcionarioCrud.login(email, senha);
        } else if (tipo.equals("2")) {
            colaborador = administradorCrud.login(email, senha);
        } else {
            System.out.println("Tipo inválido.");
            return false;
        }

        if (colaborador != null) {
            Session.setPessoaLogado(colaborador);
            System.out.println("Login realizado com sucesso. Bem-vindo, " + colaborador.getNome() + "!");
            return true;
        } else {
            System.out.println("Credenciais inválidas.");
            return false;
        }
    }
}
