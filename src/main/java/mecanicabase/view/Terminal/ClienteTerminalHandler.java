package mecanicabase.view.Terminal;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import mecanicabase.controller.ClienteController;
import mecanicabase.model.usuarios.Cliente;

/**
 * Handler responsável pelas operações de terminal para Cliente.
 */
public class ClienteTerminalHandler {

    private final Scanner scanner;
    private final ClienteController controller;

    public ClienteTerminalHandler(Scanner scanner, ClienteController controller) {
        this.scanner = scanner;
        this.controller = controller;
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
                case "1" ->
                    criar();
                case "2" ->
                    listar();
                case "3" ->
                    buscar();
                case "4" ->
                    atualizar();
                case "5" ->
                    remover();
                case "0" -> {
                    return;
                }
                default ->
                    System.out.println("Opção inválida.");
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
        System.out.println("✅ Cliente criado com ID: " + cliente.getId());
    }

    private void listar() {
        System.out.print("Filtro (nome ou email): ");
        String filtro = scanner.nextLine();
        List<Cliente> clientes = controller.listar(filtro);

        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente encontrado.");
            return;
        }

        System.out.println("\n=== CLIENTES ENCONTRADOS ===");
        clientes.forEach(c -> System.out.println(
                c.getId() + " - " + c.getNome() + " - " + c.getEmail()
        ));
    }

    private void buscar() {
        System.out.print("ID: ");
        String id = scanner.nextLine();

        try {
            UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ ID inválido. Use um UUID válido.");
            return;
        }

        Cliente c = controller.buscarPorId(id);
        if (c != null) {
            System.out.println("Nome: " + c.getNome());
            System.out.println("Email: " + c.getEmail());
        } else {
            System.out.println("❌ Cliente não encontrado.");
        }
    }

    private void atualizar() {
        System.out.print("Filtro (nome ou email): ");
        String filtro = scanner.nextLine();
        List<Cliente> clientes = controller.listar(filtro);

        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente encontrado.");
            return;
        }

        System.out.println("\n=== CLIENTES ENCONTRADOS ===");
        for (int i = 0; i < clientes.size(); i++) {
            Cliente c = clientes.get(i);
            System.out.printf("[%d] %s - %s%n", i + 1, c.getNome(), c.getEmail());
        }

        System.out.print("Escolha o número do cliente: ");
        int indice;
        try {
            indice = Integer.parseInt(scanner.nextLine()) - 1;
            if (indice < 0 || indice >= clientes.size()) {
                System.out.println("Número inválido.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
            return;
        }

        Cliente selecionado = clientes.get(indice);

        System.out.print("Novo nome (ou ENTER): ");
        String nome = scanner.nextLine();
        nome = nome.isBlank() ? null : nome;

        System.out.print("Novo endereço (ou ENTER): ");
        String endereco = scanner.nextLine();
        endereco = endereco.isBlank() ? null : endereco;

        System.out.print("Novo telefone (ou ENTER): ");
        String telefone = scanner.nextLine();
        telefone = telefone.isBlank() ? null : telefone;

        System.out.print("Novo email (ou ENTER): ");
        String email = scanner.nextLine();
        email = email.isBlank() ? null : email;

        System.out.print("Novo CPF (ou ENTER): ");
        String cpf = scanner.nextLine();
        cpf = cpf.isBlank() ? null : cpf;

        Cliente atualizado = controller.atualizar(
                selecionado.getId().toString(),
                nome, endereco, telefone, email, cpf
        );

        if (atualizado != null) {
            System.out.println("✅ Cliente atualizado.");
        } else {
            System.out.println("❌ Erro ao atualizar o cliente.");
        }
    }

    private void remover() {
        System.out.print("Filtro (nome ou email): ");
        String filtro = scanner.nextLine();
        List<Cliente> clientes = controller.listar(filtro);

        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente encontrado.");
            return;
        }

        System.out.println("\n=== CLIENTES ENCONTRADOS ===");
        for (int i = 0; i < clientes.size(); i++) {
            Cliente c = clientes.get(i);
            System.out.printf("[%d] %s - %s%n", i + 1, c.getNome(), c.getEmail());
        }

        System.out.print("Escolha o número do cliente para remover: ");
        int indice;
        try {
            indice = Integer.parseInt(scanner.nextLine()) - 1;
            if (indice < 0 || indice >= clientes.size()) {
                System.out.println("Número inválido.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
            return;
        }

        Cliente selecionado = clientes.get(indice);
        if (controller.remover(selecionado.getId().toString())) {
            System.out.println("✅ Cliente removido.");
        } else {
            System.out.println("❌ Cliente não encontrado.");
        }
    }
}
