package mecanicabase.view.Terminal;

import java.util.List;
import java.util.Scanner;
import mecanicabase.model.financeiro.CategoriaDespesa;
import mecanicabase.model.financeiro.Despesa;
import mecanicabase.service.financeiro.CategoriaDespesaCrud;
import mecanicabase.service.financeiro.DespesaCrud;

/**
 * Handler de terminal responsável por operações relacionadas a despesas.
 */
public class DespesaTerminalHandler {

    private final Scanner scanner;
    private final DespesaCrud despesaCrud;
    private final CategoriaDespesaCrud categoriaCrud;

    public DespesaTerminalHandler(Scanner scanner, DespesaCrud despesaCrud, CategoriaDespesaCrud categoriaCrud) {
        this.scanner = scanner;
        this.despesaCrud = despesaCrud;
        this.categoriaCrud = categoriaCrud;
    }

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
                case "1" ->
                    criar();
                case "2" ->
                    listar();
                case "3" ->
                    atualizar();
                case "4" ->
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
        List<CategoriaDespesa> categorias = categoriaCrud.listarTodos();
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
            Despesa d = despesaCrud.criar(true, categoria.getId(), descricao, valor);
            System.out.println("✅ Despesa criada: " + d.getId());
        } catch (Exception e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    private void listar() {
        List<Despesa> despesas = despesaCrud.listarTodos();
        if (despesas.isEmpty()) {
            System.out.println("Nenhuma despesa encontrada.");
            return;
        }

        despesas.forEach(System.out::println);
    }

    private void atualizar() {
        List<Despesa> despesas = despesaCrud.listarTodos();
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
        Float novoValor = novoValorStr.isBlank() ? null : Float.valueOf(novoValorStr);

        Despesa atualizada = despesaCrud.atualizar(d.getId().toString(), true, novaDescricao, novoValor);
        System.out.println(atualizada != null ? "✅ Despesa atualizada." : "❌ Erro ao atualizar.");
    }

    private void remover() {
        List<Despesa> despesas = despesaCrud.listarTodos();
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
        boolean ok = despesaCrud.removerPorId(d.getId().toString());
        System.out.println(ok ? "✅ Despesa removida." : "❌ Falha ao remover.");
    }
}
