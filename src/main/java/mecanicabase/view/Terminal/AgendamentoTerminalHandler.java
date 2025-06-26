package mecanicabase.view.Terminal;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import mecanicabase.controller.AgendamentoController;
import mecanicabase.model.financeiro.Agendamento;
import mecanicabase.model.financeiro.OrdemDeServico;
import mecanicabase.model.financeiro.StatusAgendamento;
import mecanicabase.model.operacao.Servico;
import mecanicabase.model.operacao.Veiculo;
import mecanicabase.model.usuarios.Cliente;
import mecanicabase.service.financeiro.AgendamentoCrud;
import mecanicabase.service.financeiro.OrdemDeServicoCrud;
import mecanicabase.service.operacao.ServicoCrud;
import mecanicabase.service.operacao.VeiculoCrud;
import mecanicabase.service.usuarios.ClienteCrud;

public class AgendamentoTerminalHandler {

    private final Scanner scanner;
    private final AgendamentoCrud agendamentoCrud;
    private final OrdemDeServicoCrud ordemCrud;
    private final ClienteCrud clienteCrud;
    private final VeiculoCrud veiculoCrud;
    private final ServicoCrud servicoCrud;
    private final AgendamentoController agendamentoController;

    public AgendamentoTerminalHandler(
            Scanner scanner,
            AgendamentoCrud agendamentoCrud,
            OrdemDeServicoCrud ordemCrud,
            ClienteCrud clienteCrud,
            VeiculoCrud veiculoCrud,
            ServicoCrud servicoCrud,
            AgendamentoController agendamentoController
    ) {
        this.scanner = scanner;
        this.agendamentoCrud = agendamentoCrud;
        this.ordemCrud = ordemCrud;
        this.clienteCrud = clienteCrud;
        this.veiculoCrud = veiculoCrud;
        this.servicoCrud = servicoCrud;
        this.agendamentoController = agendamentoController;
    }

    public void menu() {
        while (true) {
            System.out.println("\n=== MENU AGENDAMENTO ===");
            System.out.println("1 - Criar Agendamento");
            System.out.println("2 - Listar Agendamentos");
            System.out.println("3 - Atualizar Status do Agendamento");
            System.out.println("4 - Cancelar Agendamento");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            String opcao = scanner.nextLine();
            switch (opcao) {
                case "1" ->
                    criar();
                case "2" ->
                    listar();
                case "3" ->
                    atualizarStatus();
                case "4" ->
                    cancelar();
                case "0" -> {
                    return;
                }
                default ->
                    System.out.println("Opção inválida.");
            }
        }
    }

    private void criar() {
        try {
            List<Cliente> clientes = clienteCrud.listarTodos();
            if (clientes.isEmpty()) {
                System.out.println("Nenhum cliente encontrado.");
                return;
            }

            for (int i = 0; i < clientes.size(); i++) {
                System.out.printf("[%d] %s\n", i + 1, clientes.get(i));
            }
            System.out.print("Escolha o cliente: ");
            Cliente cliente = clientes.get(Integer.parseInt(scanner.nextLine()) - 1);

            List<Veiculo> veiculos = veiculoCrud.buscarPorFiltro(cliente.getId().toString());
            if (veiculos.isEmpty()) {
                System.out.println("Este cliente não possui veículos.");
                return;
            }

            for (int i = 0; i < veiculos.size(); i++) {
                System.out.printf("[%d] %s\n", i + 1, veiculos.get(i));
            }
            System.out.print("Escolha o veículo: ");
            Veiculo veiculo = veiculos.get(Integer.parseInt(scanner.nextLine()) - 1);

            List<Servico> servicos = servicoCrud.listarTodos();
            for (int i = 0; i < servicos.size(); i++) {
                System.out.printf("[%d] %s\n", i + 1, servicos.get(i));
            }
            System.out.print("Escolha o serviço: ");
            Servico servico = servicos.get(Integer.parseInt(scanner.nextLine()) - 1);

            System.out.print("Descreva o problema: ");
            String problema = scanner.nextLine();

            System.out.print("Data e hora desejada (yyyy-MM-ddTHH:mm): ");
            LocalDateTime data = LocalDateTime.parse(scanner.nextLine());

            OrdemDeServico ordem = ordemCrud.criar(true, cliente.getId());

            Agendamento agendamento = agendamentoController.criarAgendamentoComAlocacao(
                    data, servico, problema, veiculo, ordem
            );

            System.out.println("✅ Agendamento criado: " + agendamento.getId());

        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.out.println("❌ Entrada inválida. Verifique os números digitados.");
        } catch (DateTimeParseException e) {
            System.out.println("❌ Data no formato incorreto. Use yyyy-MM-ddTHH:mm");
        } catch (RuntimeException e) {
            System.out.println("❌ Erro inesperado: " + e.getMessage());
        }
    }

    private void listar() {
        List<Agendamento> agendamentos = agendamentoCrud.buscarComFiltros(null, null, null, null, null);
        if (agendamentos.isEmpty()) {
            System.out.println("Nenhum agendamento encontrado.");
            return;
        }
        agendamentos.forEach(System.out::println);
    }

    private void atualizarStatus() {
        List<Agendamento> agendamentos = agendamentoCrud.buscarComFiltros(null, null, null, null, null);
        if (agendamentos.isEmpty()) {
            System.out.println("Nenhum agendamento encontrado.");
            return;
        }

        for (int i = 0; i < agendamentos.size(); i++) {
            System.out.printf("[%d] %s\n", i + 1, agendamentos.get(i));
        }

        System.out.print("Escolha o agendamento: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;
        Agendamento ag = agendamentos.get(index);

        System.out.println("Status atual: " + ag.getStatus());
        System.out.println("1 - ABERTO\n2 - CANCELADO\n3 - CONCLUIDO");
        System.out.print("Novo status: ");
        StatusAgendamento novo = StatusAgendamento.values()[Integer.parseInt(scanner.nextLine()) - 1];

        agendamentoCrud.atualizar(ag.getId().toString(), true,
                ag.getData(), ag.getDescricaoProblema(),
                ag.getVeiculo().getId(),
                ag.getElevador().getId(),
                ag.getFuncionario().getId(),
                ag.getServico().getId(),
                ag.getOrdemDeServico().getId(),
                novo
        );

        System.out.println("✅ Status atualizado.");
    }

    private void cancelar() {
        List<Agendamento> agendamentos = agendamentoCrud.buscarComFiltros(null, null, null, null, null);
        if (agendamentos.isEmpty()) {
            System.out.println("Nenhum agendamento encontrado.");
            return;
        }

        for (int i = 0; i < agendamentos.size(); i++) {
            System.out.printf("[%d] %s\n", i + 1, agendamentos.get(i));
        }

        System.out.print("Escolha o agendamento para cancelar: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;
        Agendamento ag = agendamentos.get(index);

        agendamentoCrud.atualizar(ag.getId().toString(), true,
                ag.getData(), ag.getDescricaoProblema(),
                ag.getVeiculo().getId(),
                ag.getElevador().getId(),
                ag.getFuncionario().getId(),
                ag.getServico().getId(),
                ag.getOrdemDeServico().getId(),
                StatusAgendamento.CANCELADO
        );

        System.out.println("✅ Agendamento cancelado com taxa de 20% aplicada à OS.");
    }
}
