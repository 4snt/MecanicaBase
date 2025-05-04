package presentation.Terminal;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import domain.entities.financeiro.CategoriaDespesa;
import domain.usecases.financeiro.categoria_despesa.AtualizarCategoriaDespesaUseCase;
import domain.usecases.financeiro.categoria_despesa.CriarCategoriaDespesaUseCase;
import domain.usecases.financeiro.categoria_despesa.ListarCategoriaDespesaUseCase;
import domain.usecases.financeiro.categoria_despesa.RemoverCategoriaDespesaUseCase;

/**
 * Handler de terminal para gerenciamento de categorias de despesa. Permite
 * criar, listar, atualizar e remover categorias.
 */
public class CategoriaDespesaTerminalHandler {

    private final Scanner scanner;
    private final CriarCategoriaDespesaUseCase criar = new CriarCategoriaDespesaUseCase();
    private final ListarCategoriaDespesaUseCase listar = new ListarCategoriaDespesaUseCase();
    private final AtualizarCategoriaDespesaUseCase atualizar = new AtualizarCategoriaDespesaUseCase();
    private final RemoverCategoriaDespesaUseCase remover = new RemoverCategoriaDespesaUseCase();

    /**
     * Construtor que recebe o scanner utilizado para entrada de dados via
     * terminal.
     *
     * @param scanner Scanner padrão usado para leitura do terminal.
     */
    public CategoriaDespesaTerminalHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Exibe o menu de categorias de despesa e redireciona para as opções
     * correspondentes.
     */
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

    /**
     * Cria uma nova categoria de despesa com título informado pelo usuário.
     */
    private void criar() {
        System.out.print("Digite o título da nova categoria: ");
        String titulo = scanner.nextLine().trim();

        if (titulo.isBlank()) {
            System.out.println("Título inválido.");
            return;
        }

        CategoriaDespesa categoria = criar.use(titulo);
        System.out.println("✅ Categoria criada com ID: " + categoria.getId());
    }

    /**
     * Lista todas as categorias de despesa cadastradas no sistema.
     */
    private void listar() {
        List<CategoriaDespesa> categorias = listar.use();

        if (categorias.isEmpty()) {
            System.out.println("Nenhuma categoria cadastrada.");
            return;
        }

        System.out.println("\n=== CATEGORIAS CADASTRADAS ===");
        categorias.forEach(c -> System.out.println(c));
    }

    /**
     * Permite atualizar o título de uma categoria de despesa existente, a
     * partir do ID.
     */
    private void atualizar() {
        listar();
        System.out.print("Digite o ID da categoria a ser atualizada: ");
        String idStr = scanner.nextLine().trim();

        try {
            UUID id = UUID.fromString(idStr);
            System.out.print("Novo título: ");
            String novoTitulo = scanner.nextLine().trim();

            if (novoTitulo.isBlank()) {
                System.out.println("Título inválido.");
                return;
            }

            CategoriaDespesa atualizada = atualizar.use(id, novoTitulo);
            if (atualizada == null) {
                System.out.println("❌ Categoria não encontrada.");
            } else {
                System.out.println("✅ Categoria atualizada com sucesso.");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("❌ ID inválido.");
        }
    }

    /**
     * Remove uma categoria de despesa do sistema com base no ID informado pelo
     * usuário.
     */
    private void remover() {
        listar();
        System.out.print("Digite o ID da categoria a ser removida: ");
        String idStr = scanner.nextLine().trim();

        try {
            UUID id = UUID.fromString(idStr);
            boolean sucesso = remover.use(id);

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
