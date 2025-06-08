package mecanicabase.view.Terminal;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import mecanicabase.model.financeiro.CategoriaDespesa;
import mecanicabase.service.financeiro.CategoriaDespesaCrud;

/**
 * Handler de terminal para gerenciamento de categorias de despesa. Usa o CRUD
 * genérico.
 */
public class CategoriaDespesaTerminalHandler {

    private final Scanner scanner;
    private final CategoriaDespesaCrud crud = new CategoriaDespesaCrud() {
    };

    public CategoriaDespesaTerminalHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    public void menu() {
        while (true) {
            System.out.println("\n=== MENU CATEGORIAS DE DESPESA ===");
            System.out.println("1 - Criar Categoria");
            System.out.println("2 - Listar Categorias");
            System.out.println("3 - Atualizar Categoria");
            System.out.println("4 - Remover Categoria");
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
                case "0":
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private void criar() {
        System.out.print("Digite o título da nova categoria: ");
        String titulo = scanner.nextLine().trim();

        if (titulo.isBlank()) {
            System.out.println("Título inválido.");
            return;
        }

        CategoriaDespesa categoria = crud.criar(true, titulo); // ✅ passa `true` explicitamente
        System.out.println("✅ Categoria criada com ID: " + categoria.getId());
    }

    private void listar() {
        List<CategoriaDespesa> categorias = crud.listarTodos();

        if (categorias.isEmpty()) {
            System.out.println("Nenhuma categoria cadastrada.");
            return;
        }

        System.out.println("\n=== CATEGORIAS CADASTRADAS ===");
        categorias.forEach(System.out::println);
    }

    private void atualizar() {
        listar();
        System.out.print("Digite o ID da categoria a ser atualizada: ");
        String idStr = scanner.nextLine().trim();

        try {
            UUID id = UUID.fromString(idStr);
            CategoriaDespesa existente = crud.buscarPorId(id);
            if (existente == null) {
                System.out.println("❌ Categoria não encontrada.");
                return;
            }

            System.out.print("Novo título: ");
            String novoTitulo = scanner.nextLine().trim();

            if (novoTitulo.isBlank()) {
                System.out.println("Título inválido.");
                return;
            }

            crud.atualizar(idStr, true, novoTitulo); // ✅ passa `true` explicitamente
            System.out.println("✅ Categoria atualizada com sucesso.");

        } catch (IllegalArgumentException e) {
            System.out.println("❌ ID inválido.");
        }
    }

    private void remover() {
        listar();
        System.out.print("Digite o ID da categoria a ser removida: ");
        String idStr = scanner.nextLine().trim();

        try {
            boolean sucesso = crud.removerPorId(idStr);
            if (sucesso) {
                System.out.println("✅ Categoria removida.");
            } else {
                System.out.println("❌ Categoria não encontrada.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("❌ ID inválido.");
        }
    }
}
