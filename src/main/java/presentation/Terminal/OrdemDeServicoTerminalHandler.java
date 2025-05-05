package presentation.Terminal;

import java.util.List;
import java.util.Scanner;

import domain.entities.financeiro.OrdemDeServico;
import domain.entities.financeiro.PecaItem;
import domain.entities.financeiro.StatusOrdemDeServico;
import domain.entities.operacao.Peca;
import domain.usecases.financeiro.ordem_de_servico.AtualizaOrdemDeServicoUseCase;
import domain.usecases.financeiro.ordem_de_servico.ListaOrdemDeServicoUseCase;
import domain.usecases.financeiro.ordem_de_servico.VenderPecaUseCase;
import domain.usecases.operacao.peca.ListaPecaUseCase;

/**
 * Handler para funcionalidades extras de Ordem de Serviço. Aqui você pode
 * listar, adicionar peça e finalizar uma OS. A criação agora é automática via
 * Agendamento.
 */
public class OrdemDeServicoTerminalHandler {

    private final Scanner scanner;
    private final ListaOrdemDeServicoUseCase listaOS = new ListaOrdemDeServicoUseCase();
    private final AtualizaOrdemDeServicoUseCase atualizaOS = new AtualizaOrdemDeServicoUseCase();
    private final ListaPecaUseCase listaPeca = new ListaPecaUseCase();
    private final VenderPecaUseCase venderPeca = new VenderPecaUseCase();

    public OrdemDeServicoTerminalHandler(Scanner scanner) {
        this.scanner = scanner;
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
                case "1":
                    listar();
                case "2":
                    adicionarPeca();
                case "3":
                    finalizar();
                case "0": {
                    return;
                }
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private void listar() {
        List<OrdemDeServico> ordens = listaOS.use();
        if (ordens.isEmpty()) {
            System.out.println("Nenhuma ordem de serviço encontrada.");
            return;
        }
        ordens.forEach(System.out::println);
    }

    private void adicionarPeca() {
        List<OrdemDeServico> ordens = listaOS.use();
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

        List<Peca> pecas = listaPeca.use();
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
            PecaItem item = venderPeca.use(os.getId(), peca.getId(), qtd);
            os.addPeca(item.getId());
            System.out.println("✅ Peça adicionada com sucesso!");
        } catch (RuntimeException e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    private void finalizar() {
        List<OrdemDeServico> ordens = listaOS.use();
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
            atualizaOS.use(os.getId().toString(), null, StatusOrdemDeServico.CONCLUIDO);
            System.out.println("✅ Ordem de serviço finalizada!");
        } catch (RuntimeException e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }
}
