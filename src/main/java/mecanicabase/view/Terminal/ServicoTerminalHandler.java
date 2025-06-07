package mecanicabase.view.Terminal;

import java.util.List;
import java.util.Scanner;
import mecanicabase.model.operacao.Servico;
import mecanicabase.model.operacao.TipoElevador;
import mecanicabase.model.usuarios.TipoFuncionario;
import mecanicabase.service.operacao.servico.AtualizaServicoUseCase;
import mecanicabase.service.operacao.servico.CriarServicoUseCase;
import mecanicabase.service.operacao.servico.ListaServicoUseCase;
import mecanicabase.service.operacao.servico.RemoveServicoUseCase;

/**
 * Classe responsável por lidar com interações via terminal para serviços da
 * oficina.
 */
public class ServicoTerminalHandler {

    private final Scanner scanner;
    private final CriarServicoUseCase criarServico = new CriarServicoUseCase();
    private final ListaServicoUseCase listarServico = new ListaServicoUseCase();
    private final AtualizaServicoUseCase atualizarServico = new AtualizaServicoUseCase();
    private final RemoveServicoUseCase removerServico = new RemoveServicoUseCase();

    public ServicoTerminalHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Exibe o menu principal de serviços no terminal.
     */
    public void menu() {
        while (true) {
            System.out.println("\n=== MENU SERVIÇOS ===");
            System.out.println("1 - Criar Serviço");
            System.out.println("2 - Listar Serviços");
            System.out.println("3 - Atualizar Serviço");
            System.out.println("4 - Remover Serviço");
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
     * Realiza a criação de um novo serviço, solicitando todos os dados ao
     * usuário.
     */
    private void criar() {
        System.out.print("Tipo do serviço: ");
        String tipo = scanner.nextLine();

        System.out.print("Preço: ");
        float preco = Float.parseFloat(scanner.nextLine());

        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();

        System.out.print("Duração (minutos): ");
        int duracao = Integer.parseInt(scanner.nextLine());

        TipoFuncionario tipoFuncionario = null;
        System.out.println("Tipo de funcionário necessário:");
        TipoFuncionario[] valoresFuncionario = TipoFuncionario.values();
        for (int i = 0; i < valoresFuncionario.length; i++) {
            System.out.printf("[%d] %s\n", i + 1, valoresFuncionario[i].name());
        }
        System.out.print("Escolha o número: ");
        int escolhaFuncionario = Integer.parseInt(scanner.nextLine());
        if (escolhaFuncionario > 0 && escolhaFuncionario <= valoresFuncionario.length) {
            tipoFuncionario = valoresFuncionario[escolhaFuncionario - 1];
        }

        System.out.print("Usa elevador? (true/false): ");
        Boolean usaElevador = Boolean.parseBoolean(scanner.nextLine());

        TipoElevador tipoElevador = null;
        if (usaElevador) {
            System.out.println("Tipo de elevador necessário:");
            TipoElevador[] valoresElevador = TipoElevador.values();
            for (int i = 0; i < valoresElevador.length; i++) {
                System.out.printf("[%d] %s\n", i + 1, valoresElevador[i].name());
            }
            System.out.print("Escolha o número: ");
            int escolhaElevador = Integer.parseInt(scanner.nextLine());
            if (escolhaElevador > 0 && escolhaElevador <= valoresElevador.length) {
                tipoElevador = valoresElevador[escolhaElevador - 1];
            }
        }

        Servico servico = criarServico.use(tipo, preco, descricao, duracao, tipoFuncionario, tipoElevador, usaElevador);
        System.out.println("Criado com ID: " + servico.getId());
    }

    /**
     * Lista todos os serviços cadastrados.
     */
    private void listar() {
        List<Servico> servicos = listarServico.use();
        if (servicos.isEmpty()) {
            System.out.println("Nenhum serviço encontrado.");
            return;
        }
        for (int i = 0; i < servicos.size(); i++) {
            System.out.printf("[%d] %s\n", i + 1, servicos.get(i));
        }
    }

    /**
     * Atualiza um serviço selecionado pelo usuário.
     */
    private void atualizar() {
        List<Servico> servicos = listarServico.use();
        if (servicos.isEmpty()) {
            System.out.println("Nenhum serviço encontrado.");
            return;
        }

        for (int i = 0; i < servicos.size(); i++) {
            System.out.printf("[%d] %s\n", i + 1, servicos.get(i));
        }
        System.out.print("Escolha o número do serviço: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;
        if (index < 0 || index >= servicos.size()) {
            System.out.println("Número inválido.");
            return;
        }
        Servico selecionado = servicos.get(index);

        System.out.print("Novo tipo (ENTER para manter): ");
        String tipo = scanner.nextLine();
        tipo = tipo.isBlank() ? null : tipo;

        System.out.print("Novo preço (ENTER para manter): ");
        String precoStr = scanner.nextLine();
        Float preco = precoStr.isBlank() ? null : Float.parseFloat(precoStr);

        System.out.print("Nova descrição (ENTER para manter): ");
        String descricao = scanner.nextLine();
        descricao = descricao.isBlank() ? null : descricao;

        System.out.print("Nova duração (minutos) (ENTER para manter): ");
        String duracaoStr = scanner.nextLine();
        Integer duracao = duracaoStr.isBlank() ? null : Integer.parseInt(duracaoStr);

        TipoFuncionario tipoFuncionario = null;
        System.out.println("Tipo de funcionário necessário:");
        TipoFuncionario[] valoresFuncionario = TipoFuncionario.values();
        for (int i = 0; i < valoresFuncionario.length; i++) {
            System.out.printf("[%d] %s\n", i + 1, valoresFuncionario[i].name());
        }
        System.out.print("Escolha o número (ENTER para manter): ");
        String escolhaFuncionario = scanner.nextLine();
        if (!escolhaFuncionario.isBlank()) {
            int escolha = Integer.parseInt(escolhaFuncionario);
            if (escolha > 0 && escolha <= valoresFuncionario.length) {
                tipoFuncionario = valoresFuncionario[escolha - 1];
            } else {
                System.out.println("Tipo de funcionário inválido. Mantendo o atual.");
            }
        }

        System.out.print("Usa elevador? (true/false) (ENTER para manter): ");
        String elevadorStr = scanner.nextLine();
        Boolean usaElevador = elevadorStr.isBlank() ? null : Boolean.parseBoolean(elevadorStr);

        TipoElevador tipoElevador = null;
        if (usaElevador != null && usaElevador) {
            System.out.println("Tipo de elevador necessário:");
            TipoElevador[] valoresElevador = TipoElevador.values();
            for (int i = 0; i < valoresElevador.length; i++) {
                System.out.printf("[%d] %s\n", i + 1, valoresElevador[i].name());
            }
            System.out.print("Escolha o número (ENTER para manter): ");
            String escolhaElevador = scanner.nextLine();
            if (!escolhaElevador.isBlank()) {
                int escolha = Integer.parseInt(escolhaElevador);
                if (escolha > 0 && escolha <= valoresElevador.length) {
                    tipoElevador = valoresElevador[escolha - 1];
                } else {
                    System.out.println("Tipo de elevador inválido. Mantendo o atual.");
                }
            }
        }

        Servico atualizado = atualizarServico.use(
                selecionado.getId().toString(),
                tipo, preco, descricao, duracao,
                tipoFuncionario, tipoElevador, usaElevador
        );

        if (atualizado != null) {
            System.out.println("Serviço atualizado.");
        } else {
            System.out.println("Erro ao atualizar.");
        }
    }

    /**
     * Remove um serviço selecionado da lista.
     */
    private void remover() {
        List<Servico> servicos = listarServico.use();
        if (servicos.isEmpty()) {
            System.out.println("Nenhum serviço encontrado.");
            return;
        }

        for (int i = 0; i < servicos.size(); i++) {
            System.out.printf("[%d] %s\n", i + 1, servicos.get(i));
        }
        System.out.print("Escolha o número do serviço: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;
        if (index < 0 || index >= servicos.size()) {
            System.out.println("Número inválido.");
            return;
        }

        Servico servico = servicos.get(index);
        if (removerServico.use(servico.getId().toString())) {
            System.out.println("Serviço removido.");
        } else {
            System.out.println("Erro ao remover serviço.");
        }
    }
}
