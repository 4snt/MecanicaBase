package mecanicabase.view.Terminal;

import java.util.List;
import java.util.Scanner;
import mecanicabase.model.financeiro.OrdemDeServico;
import mecanicabase.model.financeiro.PecaItem;
import mecanicabase.model.financeiro.StatusOrdemDeServico;
import mecanicabase.model.operacao.Peca;
import mecanicabase.service.financeiro.OrdemDeServicoCrud;
import mecanicabase.service.operacao.PecaCrud;

public class OrdemDeServicoTerminalHandler {

    private final Scanner scanner;
    private final OrdemDeServicoCrud crud = new OrdemDeServicoCrud();
    private final PecaCrud pecaCrud;

    public OrdemDeServicoTerminalHandler(Scanner scanner, boolean usarFlyweight) {
        this.scanner = scanner;
        this.pecaCrud = new PecaCrud(usarFlyweight); // ✅ modo configurável
    }

    public void menu() {
        while (true) {
            System.out.println("\n=== MENU ORDEM DE SERVIÇO ===");
            System.out.println("1 - Listar Ordens de Serviço");
            System.out.println("2 - Adicionar Peça à OS");
            System.out.println("3 - Finalizar Ordem de Serviço");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            String opcao = scanner.nextLine();
            switch (opcao) {
                case "1" ->
                    listar();
                case "2" ->
                    adicionarPeca();
                case "3" ->
                    finalizar();
                case "0" -> {
                    return;
                }
                default ->
                    System.out.println("Opção inválida.");
            }
        }
    }

    private void listar() {
        List<OrdemDeServico> ordens = crud.listarTodos();
        if (ordens.isEmpty()) {
            System.out.println("Nenhuma ordem de serviço encontrada.");
            return;
        }
        ordens.forEach(System.out::println);
    }

    private void adicionarPeca() {
        List<OrdemDeServico> ordens = crud.listarTodos();
        if (ordens.isEmpty()) {
            System.out.println("Nenhuma OS encontrada.");
            return;
        }

        for (int i = 0; i < ordens.size(); i++) {
            System.out.printf("[%d] %s\n", i + 1, ordens.get(i));
        }

        System.out.print("Escolha a OS: ");
        int indexOS = Integer.parseInt(scanner.nextLine()) - 1;
        OrdemDeServico os = ordens.get(indexOS);

        List<Peca> pecas = pecaCrud.listarTodos();
        if (pecas.isEmpty()) {
            System.out.println("Nenhuma peça cadastrada.");
            return;
        }

        for (int i = 0; i < pecas.size(); i++) {
            System.out.printf("[%d] %s\n", i + 1, pecas.get(i));
        }

        System.out.print("Escolha a peça: ");
        int indexPeca = Integer.parseInt(scanner.nextLine()) - 1;
        Peca peca = pecas.get(indexPeca);

        System.out.print("Quantidade: ");
        int qtd = Integer.parseInt(scanner.nextLine());

        try {
            PecaItem item = crud.venderPeca(os.getId(), peca.getId(), qtd);
            System.out.println("✅ Peça adicionada com sucesso!");
        } catch (RuntimeException e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    private void finalizar() {
        List<OrdemDeServico> ordens = crud.listarTodos();
        if (ordens.isEmpty()) {
            System.out.println("Nenhuma OS encontrada.");
            return;
        }

        for (int i = 0; i < ordens.size(); i++) {
            System.out.printf("[%d] %s\n", i + 1, ordens.get(i));
        }

        System.out.print("Escolha a OS para finalizar: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;
        OrdemDeServico os = ordens.get(index);

        try {
            crud.atualizar(os.getId().toString(), true, null, StatusOrdemDeServico.CONCLUIDO);
            System.out.println("✅ Ordem de serviço finalizada!");
        } catch (RuntimeException e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }
}
