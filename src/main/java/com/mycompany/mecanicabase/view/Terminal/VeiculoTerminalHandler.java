package com.mycompany.mecanicabase.view.Terminal;

import java.util.List;
import java.util.Scanner;
import com.mycompany.mecanicabase.model.operacao.StatusVeiculo;
import com.mycompany.mecanicabase.model.operacao.Veiculo;
import com.mycompany.mecanicabase.model.usuarios.Cliente;
import com.mycompany.mecanicabase.service.operacao.veiculo.AtualizaVeiculoUseCase;
import com.mycompany.mecanicabase.service.operacao.veiculo.CriarVeiculoUseCase;
import com.mycompany.mecanicabase.service.operacao.veiculo.ListaVeiculoUseCase;
import com.mycompany.mecanicabase.service.operacao.veiculo.RemoverVeiculoUsecase;

/**
 * Handler de terminal para operações relacionadas a veículos. Permite criar,
 * listar, buscar, atualizar e remover veículos.
 */
public class VeiculoTerminalHandler {

    private final Scanner scanner;
    private final CriarVeiculoUseCase criarVeiculoUseCase = new CriarVeiculoUseCase();
    private final AtualizaVeiculoUseCase atualizaVeiculoUseCase = new AtualizaVeiculoUseCase();
    private final ListaVeiculoUseCase listaVeiculoUseCase = new ListaVeiculoUseCase();
    private final RemoverVeiculoUsecase removerVeiculoUseCase = new RemoverVeiculoUsecase();

    /**
     * Construtor do manipulador de veículo com um scanner de entrada.
     */
    public VeiculoTerminalHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Exibe o menu de operações relacionadas a veículos.
     */
    public void menu() {
        while (true) {
            System.out.println("\n=== MENU VEÍCULOS ===");
            System.out.println("1 - Criar");
            System.out.println("2 - Listar");
            System.out.println("3 - Buscar");
            System.out.println("4 - Atualizar");
            System.out.println("5 - Remover");
            System.out.println("0 - Voltar");

            System.out.print("Opção: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    criar();
                case "2":
                    listar();
                case "3":
                    buscar();
                case "4":
                    atualizar();
                case "5":
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
     * Cria um novo veículo associado a um cliente.
     */
    private void criar() {
        System.out.print("Buscar cliente (por nome, email ou cpf): ");
        String filtro = scanner.nextLine().toLowerCase();

        List<Cliente> clientesFiltrados = Cliente.instances.stream()
                .filter(c -> c.getNome().toLowerCase().contains(filtro)
                || c.getEmail().toLowerCase().contains(filtro)
                || c.getCpf().toLowerCase().contains(filtro))
                .toList();

        if (clientesFiltrados.isEmpty()) {
            System.out.println("Nenhum cliente encontrado com esse filtro.");
            return;
        }

        System.out.println("\n=== CLIENTES ENCONTRADOS ===");
        for (int i = 0; i < clientesFiltrados.size(); i++) {
            Cliente c = clientesFiltrados.get(i);
            System.out.printf("[%d] %s - %s%n", i + 1, c.getNome(), c.getEmail());
        }

        System.out.print("Digite o número do cliente para associar ao veículo: ");
        try {
            int indice = Integer.parseInt(scanner.nextLine()) - 1;
            if (indice < 0 || indice >= clientesFiltrados.size()) {
                System.out.println("Número inválido.");
                return;
            }

            Cliente clienteSelecionado = clientesFiltrados.get(indice);

            System.out.print("Modelo: ");
            String modelo = scanner.nextLine();

            System.out.print("Placa: ");
            String placa = scanner.nextLine();

            System.out.print("Ano de Fabricação: ");
            int anoFabricacao = Integer.parseInt(scanner.nextLine());

            System.out.print("Cor: ");
            String cor = scanner.nextLine();

            Veiculo v = criarVeiculoUseCase.use(cor, anoFabricacao, placa, modelo, clienteSelecionado.getId());
            clienteSelecionado.addVeiculo(v.getId());
            System.out.println("Veículo criado com sucesso: " + v.getId());
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
        }
    }

    /**
     * Lista todos os veículos cadastrados.
     */
    private void listar() {
        List<Veiculo> veiculos = listaVeiculoUseCase.use(null);

        if (veiculos.isEmpty()) {
            System.out.println("Nenhum veículo cadastrado.");
            return;
        }

        for (Veiculo v : veiculos) {
            System.out.println("ID: " + v.getId());
            System.out.println("Modelo: " + v.getModelo());
            System.out.println("Placa: " + v.getPlaca());
            System.out.println("Ano: " + v.getAnoFabricacao());
            System.out.println("Cor: " + v.getCor());
            System.out.println("Status: " + v.getStatus());
            System.out.println("Cliente: " + (v.getCliente() != null ? v.getCliente().getNome() : "Desconhecido"));
            System.out.println("------------------------");
        }
    }

    /**
     * Exibe os dados de um veículo com status RECEBIDO.
     */
    private void buscar() {
        Veiculo v = selecionarVeiculoRecebido();
        if (v == null) {
            return;
        }

        System.out.println("Modelo: " + v.getModelo());
        System.out.println("Placa: " + v.getPlaca());
        System.out.println("Ano: " + v.getAnoFabricacao());
        System.out.println("Cor: " + v.getCor());
        System.out.println("Cliente: " + (v.getCliente() != null ? v.getCliente().getNome() : "Desconhecido"));
    }

    /**
     * Atualiza os dados de um veículo existente.
     */
    private void atualizar() {
        Veiculo v = selecionarVeiculoRecebido();
        if (v == null) {
            return;
        }

        System.out.print("Novo Modelo (ENTER para manter): ");
        String modelo = scanner.nextLine();
        if (modelo.isBlank()) {
            modelo = v.getModelo();
        }

        System.out.print("Nova Placa (ENTER para manter): ");
        String placa = scanner.nextLine();
        if (placa.isBlank()) {
            placa = v.getPlaca();
        }

        System.out.print("Novo Ano de Fabricação (ENTER para manter): ");
        String input = scanner.nextLine();
        Integer ano = input.isBlank() ? v.getAnoFabricacao() : Integer.parseInt(input);

        System.out.print("Nova Cor (ENTER para manter): ");
        String cor = scanner.nextLine();
        if (cor.isBlank()) {
            cor = v.getCor();
        }

        Cliente novoCliente = v.getCliente();
        System.out.print("Deseja trocar o cliente do veículo? (s/n): ");
        String mudarCliente = scanner.nextLine().trim().toLowerCase();
        if (mudarCliente.equals("s")) {
            novoCliente = selecionarClientePorBusca();
            if (novoCliente == null) {
                return;
            }
        }

        atualizaVeiculoUseCase.use(
                v.getId().toString(),
                novoCliente,
                modelo,
                placa,
                ano,
                cor,
                v.getStatus()
        );

        System.out.println("Veículo atualizado.");
    }

    /**
     * Remove um veículo com status RECEBIDO.
     */
    private void remover() {
        Veiculo v = selecionarVeiculoRecebido();
        if (v == null) {
            return;
        }

        removerVeiculoUseCase.use(v.getId().toString());
        System.out.println("Veículo removido.");
    }

    /**
     * Filtra e seleciona um cliente com base no nome, CPF ou email.
     */
    private Cliente selecionarClientePorBusca() {
        System.out.print("Buscar cliente (por nome, email ou cpf): ");
        String filtro = scanner.nextLine().toLowerCase();

        List<Cliente> clientesFiltrados = Cliente.instances.stream()
                .filter(c -> c.getNome().toLowerCase().contains(filtro)
                || c.getEmail().toLowerCase().contains(filtro)
                || c.getCpf().toLowerCase().contains(filtro))
                .toList();

        if (clientesFiltrados.isEmpty()) {
            System.out.println("Nenhum cliente encontrado.");
            return null;
        }

        System.out.println("\n=== CLIENTES ENCONTRADOS ===");
        for (int i = 0; i < clientesFiltrados.size(); i++) {
            Cliente c = clientesFiltrados.get(i);
            System.out.printf("[%d] %s - %s%n", i + 1, c.getNome(), c.getEmail());
        }

        System.out.print("Digite o número do novo cliente: ");
        try {
            int idx = Integer.parseInt(scanner.nextLine()) - 1;
            if (idx < 0 || idx >= clientesFiltrados.size()) {
                System.out.println("Número inválido.");
                return null;
            }
            return clientesFiltrados.get(idx);
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
            return null;
        }
    }

    /**
     * Lista veículos com status RECEBIDO e permite seleção por índice.
     */
    private Veiculo selecionarVeiculoRecebido() {
        List<Veiculo> recebidos = Veiculo.instances.stream()
                .filter(v -> v.getStatus() == StatusVeiculo.RECEBIDO)
                .toList();

        if (recebidos.isEmpty()) {
            System.out.println("Nenhum veículo com status RECEBIDO.");
            return null;
        }

        System.out.println("\n=== VEÍCULOS COM STATUS RECEBIDO ===");
        for (int i = 0; i < recebidos.size(); i++) {
            Veiculo v = recebidos.get(i);
            System.out.printf("[%d] %s - %s (%s)%n", i + 1, v.getModelo(), v.getPlaca(), v.getCliente().getNome());
        }

        System.out.print("Escolha o número do veículo: ");
        try {
            int opcao = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (opcao < 0 || opcao >= recebidos.size()) {
                System.out.println("Número inválido.");
                return null;
            }
            return recebidos.get(opcao);
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
            return null;
        }
    }
}
