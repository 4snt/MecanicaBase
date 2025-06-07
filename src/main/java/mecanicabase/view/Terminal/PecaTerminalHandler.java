package mecanicabase.view.Terminal;

import java.util.List;
import java.util.Scanner;
import mecanicabase.model.operacao.EntradaPeca;
import mecanicabase.model.operacao.Peca;
import mecanicabase.service.operacao.entrada_peca.CriarManualEntradaPecaUseCase;
import mecanicabase.service.operacao.peca.AtualizaPecaUseCase;
import mecanicabase.service.operacao.peca.CriarPecaUseCase;
import mecanicabase.service.operacao.peca.ListaPecaUseCase;
import mecanicabase.service.operacao.peca.RemovePecaUseCase;

/**
 * Handler de terminal para gestão de peças. Permite criar, listar, atualizar,
 * remover peças e registrar entradas manuais.
 */
public class PecaTerminalHandler {

    private final Scanner scanner;
    private final CriarPecaUseCase criarPecaUseCase = new CriarPecaUseCase();
    private final ListaPecaUseCase listaPecaUseCase = new ListaPecaUseCase();
    private final AtualizaPecaUseCase atualizaPecaUseCase = new AtualizaPecaUseCase();
    private final RemovePecaUseCase removePecaUseCase = new RemovePecaUseCase();
    private final CriarManualEntradaPecaUseCase criarManualEntradaPecaUseCase = new CriarManualEntradaPecaUseCase();

    /**
     * Construtor que recebe o scanner para entrada de dados.
     */
    public PecaTerminalHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Exibe o menu principal de ações com peças.
     */
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
                case "2":
                    listar();
                case "3":
                    atualizar();
                case "4":
                    remover();
                case "5":
                    entradaManual();
                case "0": {
                    return;
                }
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    /**
     * Cria uma nova peça com nome, valor e quantidade inicial.
     */
    private void criar() {
        System.out.print("Nome da peça: ");
        String nome = scanner.nextLine();
        System.out.print("Valor da peça: ");
        float valor = Float.parseFloat(scanner.nextLine());
        System.out.print("Quantidade inicial: ");
        int quantidade = Integer.parseInt(scanner.nextLine());

        Peca peca = criarPecaUseCase.use(nome, valor, quantidade);
        System.out.println("✅ Peça criada com ID: " + peca.getId());
    }

    /**
     * Lista todas as peças cadastradas com suas informações principais.
     */
    private void listar() {
        List<Peca> pecas = listaPecaUseCase.use();
        if (pecas.isEmpty()) {
            System.out.println("Nenhuma peça cadastrada.");
            return;
        }

        for (int i = 0; i < pecas.size(); i++) {
            Peca p = pecas.get(i);
            System.out.printf("[%d] %s - Estoque: %d - Valor: R$%.2f%n", i + 1, p.getNome(), p.getQuantidade(), p.getValor());
        }
    }

    /**
     * Atualiza os dados de uma peça selecionada pelo índice.
     */
    private void atualizar() {
        List<Peca> pecas = listaPecaUseCase.use();
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

        Peca atualizada = atualizaPecaUseCase.use(peca.getId().toString(), nome, valor, quantidade);
        System.out.println(atualizada != null ? "✅ Peça atualizada." : "❌ Erro ao atualizar peça.");
    }

    /**
     * Remove uma peça selecionada da lista.
     */
    private void remover() {
        List<Peca> pecas = listaPecaUseCase.use();
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
        boolean sucesso = removePecaUseCase.use(peca.getId().toString());
        System.out.println(sucesso ? "✅ Peça removida." : "❌ Erro ao remover peça.");
    }

    /**
     * Registra uma entrada manual de peças (ex: compra de fornecedor).
     */
    private void entradaManual() {
        List<Peca> pecas = listaPecaUseCase.use();
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
            EntradaPeca entrada = criarManualEntradaPecaUseCase.use(peca.getId(), fornecedor, custo, quantidade);
            System.out.println("✅ Entrada registrada com ID: " + entrada.getId());
        } catch (Exception e) {
            System.out.println("❌ Erro ao registrar entrada: " + e.getMessage());
        }
    }
}
