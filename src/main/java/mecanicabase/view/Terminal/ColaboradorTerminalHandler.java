package mecanicabase.view.Terminal;

import java.util.Scanner;
import mecanicabase.model.usuarios.Administrador;
import mecanicabase.model.usuarios.Funcionario;
import mecanicabase.model.usuarios.TipoFuncionario;
import mecanicabase.service.usuarios.AdministradorCrud;
import mecanicabase.service.usuarios.FuncionarioCrud;

/**
 * Handler de terminal para gestão de colaboradores. Permite criar, listar,
 * atualizar e remover Funcionários e Administradores.
 */
public class ColaboradorTerminalHandler {

    private final Scanner scanner;
    private final FuncionarioCrud funcionarioCrud = new FuncionarioCrud();
    private final AdministradorCrud administradorCrud = new AdministradorCrud();

    public ColaboradorTerminalHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    public void menu() {
        while (true) {
            System.out.println("\n=== MENU COLABORADORES ===");
            System.out.println("1 - Criar Funcionário");
            System.out.println("2 - Criar Administrador");
            System.out.println("3 - Listar Funcionários");
            System.out.println("4 - Listar Administradores");
            System.out.println("5 - Atualizar Funcionário");
            System.out.println("6 - Atualizar Administrador");
            System.out.println("7 - Remover Funcionário");
            System.out.println("8 - Remover Administrador");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            String opcao = scanner.nextLine();
            switch (opcao) {
                case "1" ->
                    criarFuncionario();
                case "2" ->
                    criarAdministrador();
                case "3" ->
                    listarFuncionarios();
                case "4" ->
                    listarAdministradores();
                case "5" ->
                    atualizarFuncionario();
                case "6" ->
                    atualizarAdministrador();
                case "7" ->
                    removerFuncionario();
                case "8" ->
                    removerAdministrador();
                case "0" -> {
                    return;
                }
                default ->
                    System.out.println("Opção inválida.");
            }
        }
    }

    private void criarFuncionario() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();
        System.out.print("Salário: ");
        float salario = Float.parseFloat(scanner.nextLine());

        TipoFuncionario[] tipos = TipoFuncionario.values();
        System.out.println("Funções disponíveis:");
        for (int i = 0; i < tipos.length; i++) {
            System.out.println((i + 1) + " - " + tipos[i].name());
        }
        System.out.print("Escolha a função pelo número: ");
        int escolha = Integer.parseInt(scanner.nextLine());
        if (escolha < 1 || escolha > tipos.length) {
            System.out.println("Escolha inválida.");
            return;
        }
        TipoFuncionario funcao = tipos[escolha - 1];

        Funcionario f = funcionarioCrud.criar(true, nome, funcao, email, senha, cpf, telefone, endereco, salario);
        System.out.println("Funcionário criado com ID: " + f.getId());
    }

    private void criarAdministrador() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();

        Administrador a = administradorCrud.criar(true, nome, email, senha, cpf, telefone, endereco);
        System.out.println("Administrador criado com ID: " + a.getId());
    }

    private void listarFuncionarios() {
        funcionarioCrud.listarTodos().forEach(System.out::println);
    }

    private void listarAdministradores() {
        administradorCrud.listarTodos().forEach(System.out::println);
    }

    private void atualizarFuncionario() {
        System.out.print("ID do funcionário: ");
        String id = scanner.nextLine();
        System.out.print("Novo nome (ou ENTER): ");
        String nome = scanner.nextLine();
        System.out.print("Nova senha (ou ENTER): ");
        String senha = scanner.nextLine();
        System.out.print("Novo telefone (ou ENTER): ");
        String telefone = scanner.nextLine();
        System.out.print("Novo endereço (ou ENTER): ");
        String endereco = scanner.nextLine();
        System.out.print("Novo salário (ou ENTER): ");
        String salarioStr = scanner.nextLine();

        Float salario = salarioStr.isBlank() ? null : Float.parseFloat(salarioStr);

        TipoFuncionario[] tipos = TipoFuncionario.values();
        System.out.println("Funções disponíveis:");
        for (int i = 0; i < tipos.length; i++) {
            System.out.println((i + 1) + " - " + tipos[i].name());
        }
        System.out.print("Escolha a nova função pelo número (ou ENTER para manter): ");
        String escolhaStr = scanner.nextLine();
        TipoFuncionario funcao = null;
        if (!escolhaStr.isBlank()) {
            int escolha = Integer.parseInt(escolhaStr);
            if (escolha >= 1 && escolha <= tipos.length) {
                funcao = tipos[escolha - 1];
            } else {
                System.out.println("Escolha inválida. Atualização cancelada.");
                return;
            }
        }

        funcionarioCrud.atualizar(id, true, nome, senha, telefone, endereco, funcao, salario);
        System.out.println("Funcionário atualizado.");
    }

    private void atualizarAdministrador() {
        System.out.print("ID do administrador: ");
        String id = scanner.nextLine();
        System.out.print("Novo nome (ou ENTER): ");
        String nome = scanner.nextLine();
        System.out.print("Nova senha (ou ENTER): ");
        String senha = scanner.nextLine();
        System.out.print("Novo telefone (ou ENTER): ");
        String telefone = scanner.nextLine();
        System.out.print("Novo endereço (ou ENTER): ");
        String endereco = scanner.nextLine();

        administradorCrud.atualizar(id, true, nome, senha, telefone, endereco);
        System.out.println("Administrador atualizado.");
    }

    private void removerFuncionario() {
        System.out.print("ID do funcionário: ");
        String id = scanner.nextLine();
        boolean removido = funcionarioCrud.removerPorId(id);
        System.out.println(removido ? "Funcionário removido." : "Funcionário não encontrado.");
    }

    private void removerAdministrador() {
        System.out.print("ID do administrador: ");
        String id = scanner.nextLine();
        boolean removido = administradorCrud.removerPorId(id);
        System.out.println(removido ? "Administrador removido." : "Administrador não encontrado.");
    }
}
