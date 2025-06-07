package mecanicabase.view.Terminal;

import java.util.List;
import java.util.Scanner;
import mecanicabase.model.usuarios.Administrador;
import mecanicabase.model.usuarios.Funcionario;
import mecanicabase.model.usuarios.TipoFuncionario;
import mecanicabase.service.usuarios.administrador.AtualizarAdministradorUseCase;
import mecanicabase.service.usuarios.administrador.CriarAdministradorUseCase;
import mecanicabase.service.usuarios.administrador.ListarAdministradorUseCase;
import mecanicabase.service.usuarios.administrador.RemoverAdministradorUseCase;
import mecanicabase.service.usuarios.funcionario.AtualizaFuncionarioUseCase;
import mecanicabase.service.usuarios.funcionario.CriarFuncionarioUseCase;
import mecanicabase.service.usuarios.funcionario.ListaFuncionarioUseCase;
import mecanicabase.service.usuarios.funcionario.RemoveFuncionarioUseCase;

/**
 * Handler de terminal para gestão de colaboradores. Permite criar, listar,
 * atualizar e remover Funcionários e Administradores.
 */
public class ColaboradorTerminalHandler {

    private final Scanner scanner;

    // UseCases de Funcionário
    private final CriarFuncionarioUseCase criarFuncionarioUseCase = new CriarFuncionarioUseCase();
    private final ListaFuncionarioUseCase listaFuncionarioUseCase = new ListaFuncionarioUseCase();
    private final AtualizaFuncionarioUseCase atualizaFuncionarioUseCase = new AtualizaFuncionarioUseCase();
    private final RemoveFuncionarioUseCase removeFuncionarioUseCase = new RemoveFuncionarioUseCase();

    // UseCases de Administrador
    private final CriarAdministradorUseCase criarAdministradorUseCase = new CriarAdministradorUseCase();
    private final ListarAdministradorUseCase listaAdministradorUseCase = new ListarAdministradorUseCase();
    private final AtualizarAdministradorUseCase atualizarAdministradorUseCase = new AtualizarAdministradorUseCase();
    private final RemoverAdministradorUseCase removeAdministradorUseCase = new RemoverAdministradorUseCase();

    /**
     * Constrói o manipulador de terminal com um scanner para entrada de dados.
     */
    public ColaboradorTerminalHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Exibe o menu principal de gerenciamento de colaboradores.
     */
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
                case "1":
                    criarFuncionario();
                case "2":
                    criarAdministrador();
                case "3":
                    listarFuncionarios();
                case "4":
                    listarAdministradores();
                case "5":
                    atualizarFuncionario();
                case "6":
                    atualizarAdministrador();
                case "7":
                    removerFuncionario();
                case "8":
                    removerAdministrador();
                case "0": {
                    return;
                }
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    /**
     * Cria um novo funcionário com entrada de dados via terminal.
     */
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

        Funcionario f = criarFuncionarioUseCase.use(nome, funcao, email, senha, cpf, telefone, endereco, salario);
        System.out.println("Funcionário criado com ID: " + f.getId());
    }

    /**
     * Cria um novo administrador com entrada de dados via terminal.
     */
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

        Administrador a = criarAdministradorUseCase.use(nome, email, senha, cpf, telefone, endereco);
        System.out.println("Administrador criado com ID: " + a.getId());
    }

    /**
     * Lista todos os funcionários existentes.
     */
    private void listarFuncionarios() {
        List<Funcionario> funcionarios = listaFuncionarioUseCase.use(null);
        funcionarios.forEach(System.out::println);
    }

    /**
     * Lista todos os administradores existentes.
     */
    private void listarAdministradores() {
        List<Administrador> administradores = listaAdministradorUseCase.use(null);
        administradores.forEach(System.out::println);
    }

    /**
     * Atualiza os dados de um funcionário existente, com opção de manter dados
     * inalterados.
     */
    private void atualizarFuncionario() {
        System.out.print("ID do funcionário: ");
        String id = scanner.nextLine();
        System.out.print("Novo nome (ou ENTER): ");
        String nome = scanner.nextLine();
        nome = nome.isBlank() ? null : nome;

        System.out.print("Novo email (ou ENTER): ");
        String email = scanner.nextLine();
        email = email.isBlank() ? null : email;

        System.out.print("Nova senha (ou ENTER): ");
        String senha = scanner.nextLine();
        senha = senha.isBlank() ? null : senha;

        System.out.print("Novo telefone (ou ENTER): ");
        String telefone = scanner.nextLine();
        telefone = telefone.isBlank() ? null : telefone;

        System.out.print("Novo endereço (ou ENTER): ");
        String endereco = scanner.nextLine();
        endereco = endereco.isBlank() ? null : endereco;

        TipoFuncionario[] tipos = TipoFuncionario.values();
        System.out.println("Funções disponíveis:");
        for (int i = 0; i < tipos.length; i++) {
            System.out.println((i + 1) + " - " + tipos[i].name());
        }
        System.out.print("Escolha a nova função pelo número (ou ENTER para manter): ");
        String escolhaStr = scanner.nextLine();
        TipoFuncionario funcao = null;
        if (!escolhaStr.isBlank()) {
            try {
                int escolha = Integer.parseInt(escolhaStr);
                if (escolha < 1 || escolha > tipos.length) {
                    System.out.println("Escolha inválida. Atualização cancelada.");
                    return;
                }
                funcao = tipos[escolha - 1];
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Atualização cancelada.");
                return;
            }
        }

        atualizaFuncionarioUseCase.use(id, nome, email, senha, telefone, endereco, funcao);
        System.out.println("Funcionário atualizado.");
    }

    /**
     * Atualiza os dados de um administrador existente, com opção de manter
     * dados inalterados.
     */
    private void atualizarAdministrador() {
        System.out.print("ID do administrador: ");
        String id = scanner.nextLine();
        System.out.print("Novo nome (ou ENTER): ");
        String nome = scanner.nextLine();
        nome = nome.isBlank() ? null : nome;

        System.out.print("Novo email (ou ENTER): ");
        String email = scanner.nextLine();
        email = email.isBlank() ? null : email;

        System.out.print("Nova senha (ou ENTER): ");
        String senha = scanner.nextLine();
        senha = senha.isBlank() ? null : senha;

        System.out.print("Novo telefone (ou ENTER): ");
        String telefone = scanner.nextLine();
        telefone = telefone.isBlank() ? null : telefone;

        System.out.print("Novo endereço (ou ENTER): ");
        String endereco = scanner.nextLine();
        endereco = endereco.isBlank() ? null : endereco;

        atualizarAdministradorUseCase.use(id, nome, email, senha, telefone, endereco);
        System.out.println("Administrador atualizado.");
    }

    /**
     * Remove um funcionário com base no ID fornecido.
     */
    private void removerFuncionario() {
        System.out.print("ID do funcionário: ");
        String id = scanner.nextLine();
        boolean removido = removeFuncionarioUseCase.use(id);
        System.out.println(removido ? "Funcionário removido." : "Funcionário não encontrado.");
    }

    /**
     * Remove um administrador com base no ID fornecido.
     */
    private void removerAdministrador() {
        System.out.print("ID do administrador: ");
        String id = scanner.nextLine();
        boolean removido = removeAdministradorUseCase.use(id);
        System.out.println(removido ? "Administrador removido." : "Administrador não encontrado.");
    }
}
