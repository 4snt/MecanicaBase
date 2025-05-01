package presentation.terminal;

import java.util.List;
import java.util.Scanner;

import controllers.ClienteController;
import domain.entities.usuarios.Cliente;

public class ClienteTerminalHandler {
    private final Scanner scanner;
    private final ClienteController controller = new ClienteController();

    public ClienteTerminalHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    public void menu() {
        while (true) {
            System.out.println("\n=== MENU CLIENTES ===");
            System.out.println("1 - Criar");
            System.out.println("2 - Listar");
            System.out.println("3 - Buscar por ID");
            System.out.println("4 - Atualizar");
            System.out.println("5 - Remover");
            System.out.println("0 - Voltar");

            System.out.print("Opção: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1" -> criar();
                case "2" -> listar();
                case "3" -> buscar();
                case "4" -> atualizar();
                case "5" -> remover();
                case "0" -> { return; }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private void criar() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();

        Cliente cliente = controller.criar(nome, endereco, telefone, email, cpf);
        System.out.println("Criado: " + cliente.getId());
    }

    private void listar() {
        System.out.print("Filtro (nome ou email): ");
        String filtro = scanner.nextLine();
        List<Cliente> clientes = controller.listar(filtro);
        for (Cliente c : clientes) {
            System.out.println(c.getId() + " - " + c.getNome() + " - " + c.getEmail());
        }
    }

    private void buscar() {
        System.out.print("ID: ");
        String id = scanner.nextLine();
        Cliente c = controller.buscarPorId(id);
        if (c != null) {
            System.out.println("Nome: " + c.getNome());
            System.out.println("Email: " + c.getEmail());
        } else {
            System.out.println("Não encontrado.");
        }
    }

    private void atualizar() {
        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Novo nome: ");
        String nome = scanner.nextLine();
        System.out.print("Novo endereço: ");
        String endereco = scanner.nextLine();
        System.out.print("Novo telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Novo email: ");
        String email = scanner.nextLine();
        System.out.print("Novo CPF: ");
        String cpf = scanner.nextLine();

        Cliente cliente = controller.atualizar(id, nome, endereco, telefone, email, cpf);
        if (cliente != null) System.out.println("Atualizado.");
        else System.out.println("Cliente não encontrado.");
    }

    private void remover() {
        System.out.print("ID: ");
        String id = scanner.nextLine();
        if (controller.remover(id)) System.out.println("Removido.");
        else System.out.println("Cliente não encontrado.");
    }
}
