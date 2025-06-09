package mecanicabase.view.Terminal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import mecanicabase.model.operacao.EntradaPeca;
import mecanicabase.model.operacao.Peca;
import mecanicabase.service.operacao.PecaCrud;

public class PecaTerminalHandler {

    private final Scanner scanner;
    private final PecaCrud pecaCrud = new PecaCrud();

    public PecaTerminalHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    public void menu() {
        while (true) {
            System.out.println("\n=== MENU PEÇAS ===");
            System.out.println("1 - Criar Peça");
            System.out.println("2 - Listar Peças");
            System.out.println("3 - Atualizar Peça");
            System.out.println("4 - Remover Peça");
            System.out.println("5 - Entrada Manual de Peça");
            System.out.println("6 - Importar Peças via CSV");
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
                case "5":
                    entradaManual();
                    break;
                case "6":
                    importarCSV();
                    break;

                case "0":
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private void criar() {
        System.out.print("Nome da peça: ");
        String nome = scanner.nextLine();
        System.out.print("Valor da peça: ");
        float valor = Float.parseFloat(scanner.nextLine());
        System.out.print("Quantidade inicial: ");
        int quantidade = Integer.parseInt(scanner.nextLine());

        try {
            Peca peca = pecaCrud.criar(true, nome, valor, quantidade);
            System.out.println("✅ Peça criada com ID: " + peca.getId());
        } catch (RuntimeException e) {
            System.out.println("❌ Erro ao criar peça: " + e.getMessage());
        }
    }

    private void listar() {
        List<Peca> pecas = pecaCrud.listarTodos();
        if (pecas.isEmpty()) {
            System.out.println("Nenhuma peça cadastrada.");
            return;
        }

        for (int i = 0; i < pecas.size(); i++) {
            Peca p = pecas.get(i);
            System.out.printf("[%d] %s - Estoque: %d - Valor: R$%.2f%n", i + 1, p.getNome(), p.getQuantidade(), p.getValor());
        }
    }

    private void atualizar() {
        List<Peca> pecas = pecaCrud.listarTodos();
        if (pecas.isEmpty()) {
            System.out.println("Nenhuma peça cadastrada.");
            return;
        }

        listar();
        System.out.print("Escolha o número da peça para atualizar: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;
        if (index < 0 || index >= pecas.size()) {
            System.out.println("Número inválido.");
            return;
        }

        Peca peca = pecas.get(index);

        System.out.print("Novo nome (ENTER para manter): ");
        String nome = scanner.nextLine();
        nome = nome.isBlank() ? null : nome;

        System.out.print("Novo valor (ENTER para manter): ");
        String valorStr = scanner.nextLine();
        Float valor = valorStr.isBlank() ? null : Float.parseFloat(valorStr);

        System.out.print("Nova quantidade (ENTER para manter): ");
        String qtdStr = scanner.nextLine();
        Integer quantidade = qtdStr.isBlank() ? null : Integer.parseInt(qtdStr);

        try {
            Peca atualizada = pecaCrud.atualizar(peca.getId().toString(), true, nome, valor, quantidade);
            System.out.println(atualizada != null ? "✅ Peça atualizada." : "❌ Erro ao atualizar peça.");
        } catch (RuntimeException e) {
            System.out.println("❌ Erro ao atualizar: " + e.getMessage());
        }
    }

    private void remover() {
        List<Peca> pecas = pecaCrud.listarTodos();
        if (pecas.isEmpty()) {
            System.out.println("Nenhuma peça cadastrada.");
            return;
        }

        listar();
        System.out.print("Escolha o número da peça para remover: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;
        if (index < 0 || index >= pecas.size()) {
            System.out.println("Número inválido.");
            return;
        }

        Peca peca = pecas.get(index);
        boolean sucesso = pecaCrud.removerPorId(peca.getId().toString());
        System.out.println(sucesso ? "✅ Peça removida." : "❌ Erro ao remover peça.");
    }

    private void entradaManual() {
        List<Peca> pecas = pecaCrud.listarTodos();
        if (pecas.isEmpty()) {
            System.out.println("Nenhuma peça cadastrada.");
            return;
        }

        listar();
        System.out.print("Escolha o número da peça: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;
        if (index < 0 || index >= pecas.size()) {
            System.out.println("Número inválido.");
            return;
        }

        Peca peca = pecas.get(index);
        System.out.print("Nome do fornecedor: ");
        String fornecedor = scanner.nextLine();
        System.out.print("Custo unitário: ");
        float custo = Float.parseFloat(scanner.nextLine());
        System.out.print("Quantidade adquirida: ");
        int quantidade = Integer.parseInt(scanner.nextLine());

        try {
            EntradaPeca entrada = pecaCrud.registrarEntrada(peca.getId(), fornecedor, custo, quantidade);
            System.out.println("✅ Entrada registrada com ID: " + entrada.getId());
        } catch (Exception e) {
            System.out.println("❌ Erro ao registrar entrada: " + e.getMessage());
        }
    }

    private void importarCSV() {
        File pasta = new File("data/");
        File[] arquivos = pasta.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));

        if (arquivos == null || arquivos.length == 0) {
            System.out.println("❌ Nenhum arquivo CSV encontrado na pasta ./data/");
            return;
        }

        // Lista arquivos disponíveis
        System.out.println("📄 Arquivos CSV disponíveis:");
        for (int i = 0; i < arquivos.length; i++) {
            System.out.printf("%d - %s%n", i + 1, arquivos[i].getName());
        }

        // Escolha do arquivo
        System.out.print("Escolha um arquivo pelo número: ");
        int escolha;
        try {
            escolha = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("❌ Entrada inválida.");
            return;
        }
        if (escolha < 1 || escolha > arquivos.length) {
            System.out.println("❌ Número fora do intervalo.");
            return;
        }

        String caminho = arquivos[escolha - 1].getPath();

        Runtime runtime = Runtime.getRuntime();
        runtime.gc(); // limpa lixo antes
        long memoriaAntes = runtime.totalMemory() - runtime.freeMemory();
        long tempoAntes = System.nanoTime();

        int linhaImportada = 0;
        int linhaArquivo = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linhaArquivo++;

                // Remove ponto-e-vírgula extras no final da linha
                linha = linha.replaceAll(";+$", "");

                // Divide por vírgula, que é o separador correto do seu CSV
                String[] partes = linha.split(",");

                // Remove colunas extras (caso tenha)
                if (partes.length > 3) {
                    partes = new String[]{partes[0], partes[1], partes[2]};
                }

                // Pula o cabeçalho, garantindo que tenha partes suficientes
                if (linhaArquivo == 1 && partes.length >= 2
                        && partes[0].toLowerCase().contains("nome")
                        && partes[1].toLowerCase().contains("valor")) {
                    continue;
                }

                // Linha inválida se menos que 3 colunas
                if (partes.length < 3) {
                    System.out.printf("⚠️ Linha %d inválida: %s%n", linhaArquivo, linha);
                    continue;
                }

                try {
                    String nome = partes[0].trim();
                    float valor = Float.parseFloat(partes[1].trim());
                    int quantidade = Integer.parseInt(partes[2].trim());

                    pecaCrud.criar(true, nome, valor, quantidade);
                    linhaImportada++;
                    System.out.printf("✅ [%d] %s importada com sucesso.%n", linhaImportada, nome);
                } catch (NumberFormatException e) {
                    System.out.printf("❌ [%d] Erro de formato numérico: %s%n", linhaArquivo, linha);
                } catch (RuntimeException e) {
                    System.out.printf("❌ [%d] Erro ao importar %s: %s%n", linhaArquivo, partes[0].trim(), e.getMessage());
                }
            }

            if (linhaImportada == 0) {
                System.out.println("⚠️ Nenhuma peça foi importada.");
            } else {
                System.out.printf("✅ Importação concluída: %d peças importadas com sucesso.%n", linhaImportada);
            }

        } catch (IOException e) {
            System.out.println("❌ Erro ao ler o arquivo: " + e.getMessage());
        }

        long tempoDepois = System.nanoTime();
        long memoriaDepois = runtime.totalMemory() - runtime.freeMemory();

        long tempoMs = (tempoDepois - tempoAntes) / 1_000_000;
        long memoriaKb = (memoriaDepois - memoriaAntes) / 1024;

        System.out.printf("⏱️ Tempo de execução: %d ms%n", tempoMs);
        System.out.printf("📦 Memória usada: %d KB%n", memoriaKb);

        try (var writer = new java.io.FileWriter("data/medicoes_benchmark.txt", true)) {
            writer.write("Importação de Peças (CSV)\n");
            writer.write("Peças importadas: " + linhaImportada + "\n");
            writer.write("Tempo: " + tempoMs + " ms\n");
            writer.write("Memória: " + memoriaKb + " KB\n\n");
        } catch (IOException e) {
            System.out.println("⚠️ Falha ao salvar benchmark: " + e.getMessage());
        }
    }

}
