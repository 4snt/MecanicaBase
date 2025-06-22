package mecanicabase.view.Terminal;

import java.util.List;
import java.util.Scanner;
import mecanicabase.model.operacao.StatusVeiculo;
import mecanicabase.model.operacao.Veiculo;
import mecanicabase.model.usuarios.Cliente;
import mecanicabase.service.operacao.VeiculoCrud;

public class VeiculoTerminalHandler {

    private final Scanner scanner;
    private final VeiculoCrud veiculoCrud = new VeiculoCrud();

    public VeiculoTerminalHandler(Scanner scanner) {
        this.scanner = scanner;
    }

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
                case "1" ->
                    criar();
                case "2" ->
                    listar();
                case "3" ->
                    buscar();
                case "4" ->
                    atualizar();
                case "5" ->
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
        Cliente cliente = selecionarClientePorBusca();
        if (cliente == null) {
            return;
        }

        System.out.print("Modelo: ");
        String modelo = scanner.nextLine();

        System.out.print("Placa: ");
        String placa = scanner.nextLine();

        System.out.print("Ano de Fabricação: ");
        int ano = Integer.parseInt(scanner.nextLine());

        System.out.print("Cor: ");
        String cor = scanner.nextLine();

        Veiculo v = veiculoCrud.criar(true, cor, ano, placa, modelo, cliente.getId());
        cliente.addVeiculo(v.getId());
        System.out.println("Veículo criado com sucesso: " + v.getId());
    }

    private void listar() {
        List<Veiculo> veiculos = veiculoCrud.listarTodos();
        if (veiculos.isEmpty()) {
            System.out.println("Nenhum veículo cadastrado.");
            return;
        }
        veiculos.forEach(System.out::println);
    }

    private void buscar() {
        Veiculo v = selecionarVeiculoRecebido();
        if (v == null) {
            return;
        }
        System.out.println(v);
    }

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
        String anoStr = scanner.nextLine();
        int ano = anoStr.isBlank() ? v.getAnoFabricacao() : Integer.parseInt(anoStr);

        System.out.print("Nova Cor (ENTER para manter): ");
        String cor = scanner.nextLine();
        if (cor.isBlank()) {
            cor = v.getCor();
        }

        Cliente cliente = v.getCliente();
        System.out.print("Trocar cliente? (s/n): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("s")) {
            Cliente novo = selecionarClientePorBusca();
            if (novo != null) {
                cliente = novo;
            }
        }

        veiculoCrud.atualizar(v.getId().toString(), true, cor, ano, placa, modelo, cliente.getId(), v.getStatus());
        System.out.println("Veículo atualizado.");
    }

    private void remover() {
        Veiculo v = selecionarVeiculoRecebido();
        if (v == null) {
            return;
        }
        boolean sucesso = veiculoCrud.removerPorId(v.getId().toString());
        if (sucesso) {
            System.out.println("Veículo removido.");
        } else {
            System.out.println("Erro ao remover veículo.");
        }
    }

    private Cliente selecionarClientePorBusca() {
        System.out.print("Buscar cliente: ");
        String filtro = scanner.nextLine().toLowerCase();
        List<Cliente> encontrados = Cliente.instances.stream()
                .filter(c -> c.getNome().toLowerCase().contains(filtro)
                || c.getEmail().toLowerCase().contains(filtro)
                || c.getCpf().toLowerCase().contains(filtro))
                .toList();

        if (encontrados.isEmpty()) {
            System.out.println("Nenhum cliente encontrado.");
            return null;
        }

        for (int i = 0; i < encontrados.size(); i++) {
            System.out.printf("[%d] %s - %s%n", i + 1, encontrados.get(i).getNome(), encontrados.get(i).getEmail());
        }
        System.out.print("Escolha o cliente: ");
        int idx = Integer.parseInt(scanner.nextLine()) - 1;
        return (idx >= 0 && idx < encontrados.size()) ? encontrados.get(idx) : null;
    }

    private Veiculo selecionarVeiculoRecebido() {
        List<Veiculo> recebidos = Veiculo.instances.stream()
                .filter(v -> v.getStatus() == StatusVeiculo.RECEBIDO)
                .toList();

        if (recebidos.isEmpty()) {
            System.out.println("Nenhum veículo com status RECEBIDO.");
            return null;
        }

        for (int i = 0; i < recebidos.size(); i++) {
            Veiculo v = recebidos.get(i);
            System.out.printf("[%d] %s - %s (%s)%n", i + 1, v.getModelo(), v.getPlaca(), v.getCliente().getNome());
        }

        System.out.print("Escolha o veículo: ");
        int idx = Integer.parseInt(scanner.nextLine()) - 1;
        return (idx >= 0 && idx < recebidos.size()) ? recebidos.get(idx) : null;
    }
}
