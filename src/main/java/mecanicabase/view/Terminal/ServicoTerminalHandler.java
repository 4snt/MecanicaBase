package mecanicabase.view.Terminal;

import java.util.List;
import java.util.Scanner;
import mecanicabase.model.operacao.Servico;
import mecanicabase.model.operacao.TipoElevador;
import mecanicabase.model.usuarios.TipoFuncionario;
import mecanicabase.service.operacao.ServicoCrud;

public class ServicoTerminalHandler {

    private final Scanner scanner;
    private final ServicoCrud crud;

    // ✅ Construtor corrigido
    public ServicoTerminalHandler(Scanner scanner, ServicoCrud crud) {
        this.scanner = scanner;
        this.crud = crud;
    }

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
        System.out.print("Tipo do serviço: ");
        String tipo = scanner.nextLine();

        System.out.print("Preço: ");
        float preco = Float.parseFloat(scanner.nextLine());

        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();

        System.out.print("Duração (minutos): ");
        int duracao = Integer.parseInt(scanner.nextLine());

        TipoFuncionario tipoFuncionario = escolherTipoFuncionario();
        Boolean usaElevador = perguntarBoolean("Usa elevador? (true/false): ");

        TipoElevador tipoElevador = null;
        if (Boolean.TRUE.equals(usaElevador)) {
            tipoElevador = escolherTipoElevador();
        }

        Servico servico = crud.criar(true, tipo, preco, descricao, duracao, tipoFuncionario, tipoElevador, usaElevador);
        System.out.println("✅ Criado com ID: " + servico.getId());
    }

    private void listar() {
        List<Servico> servicos = crud.listarTodos();
        if (servicos.isEmpty()) {
            System.out.println("Nenhum serviço encontrado.");
            return;
        }
        for (int i = 0; i < servicos.size(); i++) {
            System.out.printf("[%d] %s\n", i + 1, servicos.get(i));
        }
    }

    private void atualizar() {
        List<Servico> servicos = crud.listarTodos();
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

        Float preco = perguntarFloat("Novo preço (ENTER para manter): ");

        System.out.print("Nova descrição (ENTER para manter): ");
        String descricao = scanner.nextLine();
        descricao = descricao.isBlank() ? null : descricao;

        Integer duracao = perguntarInt("Nova duração (ENTER para manter): ");

        TipoFuncionario tipoFuncionario = escolherTipoFuncionarioOptional();

        Boolean usaElevador = perguntarBoolean("Usa elevador? (ENTER para manter): ");
        TipoElevador tipoElevador = null;
        if (Boolean.TRUE.equals(usaElevador)) {
            tipoElevador = escolherTipoElevador();
        }

        Servico atualizado = crud.atualizar(
                selecionado.getId().toString(),
                true,
                tipo,
                preco,
                descricao,
                duracao,
                tipoFuncionario,
                tipoElevador,
                usaElevador
        );

        if (atualizado != null) {
            System.out.println("✅ Serviço atualizado.");
        } else {
            System.out.println("❌ Erro ao atualizar.");
        }
    }

    private void remover() {
        List<Servico> servicos = crud.listarTodos();
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
        if (crud.removerPorId(servico.getId().toString())) {
            System.out.println("✅ Serviço removido.");
        } else {
            System.out.println("❌ Erro ao remover serviço.");
        }
    }

    // 🛠️ Utilitários
    private TipoFuncionario escolherTipoFuncionario() {
        TipoFuncionario[] valores = TipoFuncionario.values();
        for (int i = 0; i < valores.length; i++) {
            System.out.printf("[%d] %s\n", i + 1, valores[i].name());
        }
        System.out.print("Escolha o número: ");
        int escolha = Integer.parseInt(scanner.nextLine());
        return (escolha > 0 && escolha <= valores.length) ? valores[escolha - 1] : null;
    }

    private TipoFuncionario escolherTipoFuncionarioOptional() {
        TipoFuncionario[] valores = TipoFuncionario.values();
        for (int i = 0; i < valores.length; i++) {
            System.out.printf("[%d] %s\n", i + 1, valores[i].name());
        }
        System.out.print("Escolha o número (ENTER para manter): ");
        String escolha = scanner.nextLine();
        if (escolha.isBlank()) {
            return null;
        }
        int index = Integer.parseInt(escolha);
        return (index > 0 && index <= valores.length) ? valores[index - 1] : null;
    }

    private TipoElevador escolherTipoElevador() {
        TipoElevador[] valores = TipoElevador.values();
        for (int i = 0; i < valores.length; i++) {
            System.out.printf("[%d] %s\n", i + 1, valores[i].name());
        }
        System.out.print("Escolha o número: ");
        int escolha = Integer.parseInt(scanner.nextLine());
        return (escolha > 0 && escolha <= valores.length) ? valores[escolha - 1] : null;
    }

    private Boolean perguntarBoolean(String label) {
        System.out.print(label);
        String entrada = scanner.nextLine();
        return entrada.isBlank() ? null : Boolean.valueOf(entrada);
    }

    private Float perguntarFloat(String label) {
        System.out.print(label);
        String entrada = scanner.nextLine();
        return entrada.isBlank() ? null : Float.valueOf(entrada);
    }

    private Integer perguntarInt(String label) {
        System.out.print(label);
        String entrada = scanner.nextLine();
        return entrada.isBlank() ? null : Integer.valueOf(entrada);
    }
}
