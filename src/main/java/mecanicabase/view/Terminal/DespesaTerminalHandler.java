package mecanicabase.view.Terminal;

import java.util.List;
import java.util.Scanner;
import mecanicabase.model.financeiro.CategoriaDespesa;
import mecanicabase.model.financeiro.Despesa;
import mecanicabase.service.financeiro.categoria_despesa.ListarCategoriaDespesaUseCase;
import mecanicabase.service.financeiro.despesa.AtualizarDespesaUseCase;
import mecanicabase.service.financeiro.despesa.CriarDespesaUseCase;
import mecanicabase.service.financeiro.despesa.ListarDespesaUseCase;
import mecanicabase.service.financeiro.despesa.RemoverDespesaUseCase;

/**
 * Handler de terminal responsável por operações relacionadas a despesas.
 * Permite criar, listar, atualizar e remover despesas do sistema.
 */
public class DespesaTerminalHandler {

    private final Scanner scanner;

    private final CriarDespesaUseCase criarDespesa = new CriarDespesaUseCase();
    private final ListarDespesaUseCase listarDespesas = new ListarDespesaUseCase();
    private final AtualizarDespesaUseCase atualizarDespesa = new AtualizarDespesaUseCase();
    private final RemoverDespesaUseCase removerDespesa = new RemoverDespesaUseCase();
    private final ListarCategoriaDespesaUseCase listarCategorias = new ListarCategoriaDespesaUseCase();

    /**
     * Construtor do handler, recebe um Scanner para leitura do terminal.
     *
     * @param scanner Scanner padrão usado para entrada de dados no terminal.
     */
    public DespesaTerminalHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Exibe o menu principal de opções para gerenciamento de despesas.
     */
    public void menu() {
        while (true) {
            System.out.println("\n=== MENU DESPESAS ===");
            System.out.println("1 - Criar Despesa");
            System.out.println("2 - Listar Despesas");
            System.out.println("3 - Atualizar Despesa");
            System.out.println("4 - Remover Despesa");
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
                case "0": {
                    return;
                }
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    /**
     * Cria uma nova despesa associada a uma categoria existente.
     */
    private void criar() {
        List<CategoriaDespesa> categorias = listarCategorias.use();
        if (categorias.isEmpty()) {
            System.out.println("Nenhuma categoria cadastrada.");
            return;
        }

        System.out.println("Escolha uma categoria:");
        for (int i = 0; i < categorias.size(); i++) {
            System.out.printf("[%d] %s\n", i + 1, categorias.get(i).getTitulo());
        }

        System.out.print("Número: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;
        if (index < 0 || index >= categorias.size()) {
            System.out.println("Categoria inválida.");
            return;
        }

        CategoriaDespesa categoria = categorias.get(index);

        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();

        System.out.print("Valor: ");
        float valor = Float.parseFloat(scanner.nextLine());

        try {
            Despesa d = criarDespesa.use(categoria.getId(), descricao, valor);
            System.out.println("✅ Despesa criada: " + d.getId());
        } catch (Exception e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    /**
     * Lista todas as despesas existentes, sem filtros.
     */
    private void listar() {
        List<Despesa> despesas = listarDespesas.use(null, null, null, null, null);
        if (despesas.isEmpty()) {
            System.out.println("Nenhuma despesa encontrada.");
            return;
        }

        despesas.forEach(System.out::println);
    }

    /**
     * Atualiza a descrição ou valor de uma despesa existente.
     */
    private void atualizar() {
        List<Despesa> despesas = listarDespesas.use(null, null, null, null, null);
        if (despesas.isEmpty()) {
            System.out.println("Nenhuma despesa encontrada.");
            return;
        }

        for (int i = 0; i < despesas.size(); i++) {
            System.out.printf("[%d] %s\n", i + 1, despesas.get(i));
        }

        System.out.print("Escolha a despesa: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;
        if (index < 0 || index >= despesas.size()) {
            System.out.println("Opção inválida.");
            return;
        }

        Despesa d = despesas.get(index);

        System.out.print("Nova descrição (ENTER para manter): ");
        String novaDescricao = scanner.nextLine();
        if (novaDescricao.isBlank()) {
            novaDescricao = null;
        }

        System.out.print("Novo valor (ENTER para manter): ");
        String novoValorStr = scanner.nextLine();
        Float novoValor = novoValorStr.isBlank() ? null : Float.parseFloat(novoValorStr);

        Despesa atualizada = atualizarDespesa.use(d.getId(), novaDescricao, novoValor);
        System.out.println(atualizada != null ? "✅ Despesa atualizada." : "❌ Erro ao atualizar.");
    }

    /**
     * Remove uma despesa selecionada pelo usuário.
     */
    private void remover() {
        List<Despesa> despesas = listarDespesas.use(null, null, null, null, null);
        if (despesas.isEmpty()) {
            System.out.println("Nenhuma despesa encontrada.");
            return;
        }

        for (int i = 0; i < despesas.size(); i++) {
            System.out.printf("[%d] %s\n", i + 1, despesas.get(i));
        }

        System.out.print("Escolha a despesa para remover: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;
        if (index < 0 || index >= despesas.size()) {
            System.out.println("Opção inválida.");
            return;
        }

        Despesa d = despesas.get(index);
        boolean ok = removerDespesa.use(d.getId());
        System.out.println(ok ? "✅ Despesa removida." : "❌ Falha ao remover.");
    }
}
