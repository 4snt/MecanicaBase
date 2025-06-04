package com.mycompany.mecanicabase.view.Terminal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import com.mycompany.mecanicabase.model.financeiro.Agendamento;
import com.mycompany.mecanicabase.model.financeiro.OrdemDeServico;
import com.mycompany.mecanicabase.model.financeiro.StatusAgendamento;
import com.mycompany.mecanicabase.model.operacao.Servico;
import com.mycompany.mecanicabase.model.operacao.Veiculo;
import com.mycompany.mecanicabase.model.usuarios.Cliente;
import com.mycompany.mecanicabase.service.financeiro.agendamento.AtualizaAgendamentoUseCase;
import com.mycompany.mecanicabase.service.financeiro.agendamento.CriarAgendamentoUseCase;
import com.mycompany.mecanicabase.service.financeiro.agendamento.ListarAgendamentoUseCase;
import com.mycompany.mecanicabase.service.financeiro.ordem_de_servico.CriarOrdemDeServicoUseCase;
import com.mycompany.mecanicabase.service.operacao.servico.ListaServicoUseCase;
import com.mycompany.mecanicabase.service.operacao.veiculo.ListaVeiculoUseCase;
import com.mycompany.mecanicabase.service.usuarios.cliente.ListaClienteUseCase;

/**
 * Handler de terminal para gestão de agendamentos. Permite criar, listar,
 * atualizar (status) e cancelar agendamentos. O cancelamento gera uma cobrança
 * de 20% do valor do serviço na OS.
 */
public class AgendamentoTerminalHandler {

    private final Scanner scanner;
    private final CriarAgendamentoUseCase criarAgendamento = new CriarAgendamentoUseCase();
    private final ListarAgendamentoUseCase listarAgendamentos = new ListarAgendamentoUseCase();
    private final AtualizaAgendamentoUseCase atualizaAgendamento = new AtualizaAgendamentoUseCase();
    private final CriarOrdemDeServicoUseCase criarOS = new CriarOrdemDeServicoUseCase();
    private final ListaClienteUseCase listarClientes = new ListaClienteUseCase();
    private final ListaVeiculoUseCase listarVeiculos = new ListaVeiculoUseCase();
    private final ListaServicoUseCase listarServicos = new ListaServicoUseCase();

    public AgendamentoTerminalHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Exibe o menu de opções para gerenciamento de agendamentos via terminal.
     */
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
                case "1":
                    criar();
                case "2":
                    listar();
                case "3":
                    atualizarStatus();
                case "4":
                    cancelar();
                case "0": {
                    return;
                }
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    /**
     * Cria um novo agendamento vinculando um cliente, veículo, serviço e ordem
     * de serviço.
     */
    private void criar() {
        List<Cliente> clientes = listarClientes.use("");
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente encontrado.");
            return;
        }

        for (int i = 0; i < clientes.size(); i++) {
            System.out.printf("[%d] %s\n", i + 1, clientes.get(i));
        }
        System.out.print("Escolha o cliente: ");
        int indexCliente = Integer.parseInt(scanner.nextLine()) - 1;
        Cliente cliente = clientes.get(indexCliente);

        List<Veiculo> veiculos = listarVeiculos.use(cliente.getId().toString());
        if (veiculos.isEmpty()) {
            System.out.println("Este cliente não possui veículos.");
            return;
        }

        for (int i = 0; i < veiculos.size(); i++) {
            System.out.printf("[%d] %s\n", i + 1, veiculos.get(i));
        }
        System.out.print("Escolha o veículo: ");
        int indexVeiculo = Integer.parseInt(scanner.nextLine()) - 1;
        Veiculo veiculo = veiculos.get(indexVeiculo);

        List<Servico> servicos = listarServicos.use();
        for (int i = 0; i < servicos.size(); i++) {
            System.out.printf("[%d] %s\n", i + 1, servicos.get(i));
        }
        System.out.print("Escolha o serviço: ");
        int indexServico = Integer.parseInt(scanner.nextLine()) - 1;
        Servico servico = servicos.get(indexServico);

        System.out.print("Descreva o problema: ");
        String problema = scanner.nextLine();

        System.out.print("Data e hora desejada (yyyy-MM-ddTHH:mm): ");
        LocalDateTime data = LocalDateTime.parse(scanner.nextLine());

        OrdemDeServico ordem = criarOS.use(cliente.getId());

        try {
            Agendamento agendamento = criarAgendamento.use(data, servico, problema, veiculo, ordem);
            System.out.println("✅ Agendamento criado: " + agendamento.getId());
        } catch (RuntimeException e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    /**
     * Lista todos os agendamentos cadastrados no sistema.
     */
    private void listar() {
        List<Agendamento> agendamentos = listarAgendamentos.use(null, null, null, null, null);
        if (agendamentos.isEmpty()) {
            System.out.println("Nenhum agendamento encontrado.");
            return;
        }
        agendamentos.forEach(System.out::println);
    }

    /**
     * Permite alterar o status de um agendamento existente.
     */
    private void atualizarStatus() {
        List<Agendamento> agendamentos = listarAgendamentos.use(null, null, null, null, null);
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
        int opcao = Integer.parseInt(scanner.nextLine());

        try {
            StatusAgendamento novo = StatusAgendamento.values()[opcao - 1];
            atualizaAgendamento.use(ag.getId(), null, null, null, null, null, null, novo);
            System.out.println("✅ Status atualizado com sucesso.");
        } catch (Exception e) {
            System.out.println("❌ Erro ao atualizar: " + e.getMessage());
        }
    }

    /**
     * Cancela um agendamento e aplica taxa de 20% do serviço na OS vinculada.
     */
    private void cancelar() {
        List<Agendamento> agendamentos = listarAgendamentos.use(null, null, null, null, null);
        if (agendamentos.isEmpty()) {
            System.out.println("Nenhum agendamento encontrado.");
            return;
        }

        for (int i = 0; i < agendamentos.size(); i++) {
            System.out.printf("[%d] %s\n", i + 1, agendamentos.get(i));
        }

        System.out.print("Escolha o agendamento para cancelar: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;

        if (index < 0 || index >= agendamentos.size()) {
            System.out.println("Opção inválida.");
            return;
        }

        Agendamento ag = agendamentos.get(index);

        try {
            atualizaAgendamento.use(
                    ag.getId(),
                    null, null, null, null, null, null,
                    StatusAgendamento.CANCELADO
            );
            System.out.println("✅ Agendamento cancelado com taxa de 20% aplicada à OS.");
        } catch (Exception e) {
            System.out.println("❌ Falha ao cancelar: " + e.getMessage());
        }
    }
}
