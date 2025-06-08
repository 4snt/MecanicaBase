package mecanicabase.view.Terminal;

import java.util.List;
import java.util.Scanner;
import mecanicabase.model.operacao.EntradaPeca;
import mecanicabase.model.operacao.Peca;
import mecanicabase.service.operacao.PecaCrud;

public class PecaTerminalHandler {

    private final Scanner scanner;
    private final PecaCrud pecaCrud = new PecaCrud();

    public PecaTerminalHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    public void menu() {
        while (true) {
            System.out.println("\n=== MENU PEÇAS ===");
            System.out.println("1 - Criar Peça");
            System.out.println("2 - Listar Peças");
            System.out.println("3 - Atualizar Peça");
            System.out.println("4 - Remover Peça");
            System.out.println("5 - Entrada Manual de Peça");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    criar();
                    break;
                case "2":
                    listar();
                    break;
                case "3":
                    atualizar();
                    break;
                case "4":
                    remover();
                    break;
                case "5":
                    entradaManual();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private void criar() {
        System.out.print("Nome da peça: ");
        String nome = scanner.nextLine();
        System.out.print("Valor da peça: ");
        float valor = Float.parseFloat(scanner.nextLine());
        System.out.print("Quantidade inicial: ");
        int quantidade = Integer.parseInt(scanner.nextLine());

        try {
            Peca peca = pecaCrud.criar(true, nome, valor, quantidade);
            System.out.println("✅ Peça criada com ID: " + peca.getId());
        } catch (RuntimeException e) {
            System.out.println("❌ Erro ao criar peça: " + e.getMessage());
        }
    }

    private void listar() {
        List<Peca> pecas = pecaCrud.listarTodos();
        if (pecas.isEmpty()) {
            System.out.println("Nenhuma peça cadastrada.");
            return;
        }

        for (int i = 0; i < pecas.size(); i++) {
            Peca p = pecas.get(i);
            System.out.printf("[%d] %s - Estoque: %d - Valor: R$%.2f%n", i + 1, p.getNome(), p.getQuantidade(), p.getValor());
        }
    }

    private void atualizar() {
        List<Peca> pecas = pecaCrud.listarTodos();
        if (pecas.isEmpty()) {
            System.out.println("Nenhuma peça cadastrada.");
            return;
        }

        listar();
        System.out.print("Escolha o número da peça para atualizar: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;
        if (index < 0 || index >= pecas.size()) {
            System.out.println("Número inválido.");
            return;
        }

        Peca peca = pecas.get(index);

        System.out.print("Novo nome (ENTER para manter): ");
        String nome = scanner.nextLine();
        nome = nome.isBlank() ? null : nome;

        System.out.print("Novo valor (ENTER para manter): ");
        String valorStr = scanner.nextLine();
        Float valor = valorStr.isBlank() ? null : Float.parseFloat(valorStr);

        System.out.print("Nova quantidade (ENTER para manter): ");
        String qtdStr = scanner.nextLine();
        Integer quantidade = qtdStr.isBlank() ? null : Integer.parseInt(qtdStr);

        try {
            Peca atualizada = pecaCrud.atualizar(peca.getId().toString(), true, nome, valor, quantidade);
            System.out.println(atualizada != null ? "✅ Peça atualizada." : "❌ Erro ao atualizar peça.");
        } catch (RuntimeException e) {
            System.out.println("❌ Erro ao atualizar: " + e.getMessage());
        }
    }

    private void remover() {
        List<Peca> pecas = pecaCrud.listarTodos();
        if (pecas.isEmpty()) {
            System.out.println("Nenhuma peça cadastrada.");
            return;
        }

        listar();
        System.out.print("Escolha o número da peça para remover: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;
        if (index < 0 || index >= pecas.size()) {
            System.out.println("Número inválido.");
            return;
        }

        Peca peca = pecas.get(index);
        boolean sucesso = pecaCrud.removerPorId(peca.getId().toString());
        System.out.println(sucesso ? "✅ Peça removida." : "❌ Erro ao remover peça.");
    }

    private void entradaManual() {
        List<Peca> pecas = pecaCrud.listarTodos();
        if (pecas.isEmpty()) {
            System.out.println("Nenhuma peça cadastrada.");
            return;
        }

        listar();
        System.out.print("Escolha o número da peça: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;
        if (index < 0 || index >= pecas.size()) {
            System.out.println("Número inválido.");
            return;
        }

        Peca peca = pecas.get(index);
        System.out.print("Nome do fornecedor: ");
        String fornecedor = scanner.nextLine();
        System.out.print("Custo unitário: ");
        float custo = Float.parseFloat(scanner.nextLine());
        System.out.print("Quantidade adquirida: ");
        int quantidade = Integer.parseInt(scanner.nextLine());

        try {
            EntradaPeca entrada = pecaCrud.registrarEntrada(peca.getId(), fornecedor, custo, quantidade);
            System.out.println("✅ Entrada registrada com ID: " + entrada.getId());
        } catch (Exception e) {
            System.out.println("❌ Erro ao registrar entrada: " + e.getMessage());
        }
    }
}
