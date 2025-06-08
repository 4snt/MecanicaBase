package mecanicabase.infra.benchmark;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import mecanicabase.infra.flyweight.PecaFactory;
import mecanicabase.model.financeiro.OrdemDeServico;
import mecanicabase.model.financeiro.StatusOrdemDeServico;
import mecanicabase.model.operacao.Peca;
import mecanicabase.model.usuarios.Cliente;
import mecanicabase.service.financeiro.OrdemDeServicoCrud;
import mecanicabase.service.operacao.PecaCrud;
import mecanicabase.service.operacao.VeiculoCrud;
import mecanicabase.service.usuarios.ClienteCrud;

public class BenchmarkOrdemDeServico {

    private static final List<String> nomes = Arrays.asList(
            "João", "Maria", "Pedro", "Ana", "Lucas", "Beatriz", "Carlos", "Fernanda", "Rafael", "Juliana",
            "Gabriel", "Larissa", "Matheus", "Amanda", "Diego", "Camila", "André", "Patrícia", "Vinícius", "Renata",
            "Tiago", "Isabela", "Eduardo", "Vanessa", "Leandro", "Bruna", "Fábio", "Tatiane", "Otávio", "Érica"
    );

    private static final List<String> sobrenomes = Arrays.asList(
            "Silva", "Souza", "Oliveira", "Pereira", "Lima", "Gomes", "Ribeiro", "Almeida", "Costa", "Martins",
            "Barbosa", "Teixeira", "Melo", "Dias", "Araújo", "Rocha"
    );

    private static final List<String> marcas = Arrays.asList(
            "Volkswagen", "Fiat", "Honda", "Chevrolet", "Toyota", "Hyundai", "Ford",
            "Renault", "Nissan", "Peugeot", "Citroën", "Kia", "Mitsubishi", "Subaru"
    );

    private static final List<String> modelos = Arrays.asList(
            "Gol", "Palio", "Civic", "Onix", "Corolla", "HB20", "Ka", "Fiesta", "Fox",
            "Uno", "Sandero", "Logan", "Cruze", "Fusion", "Fit", "Toro", "Duster"
    );

    public static void executarBenchmark(int quantidade) {
        PecaCrud pecaCrud = new PecaCrud();
        ClienteCrud clienteCrud = new ClienteCrud();
        VeiculoCrud veiculoCrud = new VeiculoCrud();
        OrdemDeServicoCrud osCrud = new OrdemDeServicoCrud();
        Random rand = new Random();

        // Criar 20 instâncias únicas no cache do Flyweight
        List<Peca> pecasBase = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            String nome = "Peça " + (i + 1);
            float valor = 10 + i;
            Peca peca = PecaFactory.getPeca(nome, valor);
            peca.adicionarEstoque(quantidade); // garantir estoque
            pecaCrud.criar(false, peca); // salvar uma vez só
            pecasBase.add(peca);
        }

        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        long memoriaAntes = runtime.totalMemory() - runtime.freeMemory();
        long tempoAntes = System.nanoTime();

        for (int i = 0; i < quantidade; i++) {
            String nomeCompleto = nomes.get(rand.nextInt(nomes.size())) + " "
                    + sobrenomes.get(rand.nextInt(sobrenomes.size()));
            String endereco = "Rua Exemplo, nº " + rand.nextInt(1000);
            String telefone = "3199" + String.format("%06d", rand.nextInt(1000000));
            String email = nomeCompleto.toLowerCase().replace(" ", ".") + "@exemplo.com";
            String cpf = String.format("%03d.%03d.%03d-00", rand.nextInt(1000), rand.nextInt(1000), rand.nextInt(1000));
            Cliente cliente = clienteCrud.criar(true, nomeCompleto, endereco, telefone, email, cpf);

            String cor = "Prata";
            int ano = 2015 + rand.nextInt(10);
            String modelo = modelos.get(rand.nextInt(modelos.size()));
            String marca = marcas.get(rand.nextInt(marcas.size()));
            String placa = "XYZ" + String.format("%04d", i);
            String modeloCompleto = marca + " " + modelo;
            veiculoCrud.criar(true, cor, ano, placa, modeloCompleto, cliente.getId());

            OrdemDeServico os = osCrud.criar(true, cliente.getId());

            int itens = rand.nextInt(2) + 1;
            for (int j = 0; j < itens; j++) {
                Peca peca = pecasBase.get(rand.nextInt(pecasBase.size()));
                osCrud.venderPeca(os.getId(), peca.getId(), 1);
            }

            osCrud.atualizar(os.getId().toString(), true, cliente.getId(), StatusOrdemDeServico.ABERTO);

            if (i % 10000 == 0 && i > 0) {
                System.out.printf("▶️ %d ordens registradas...\n", i);
            }
        }

        long tempoDepois = System.nanoTime();
        long memoriaDepois = runtime.totalMemory() - runtime.freeMemory();

        long tempoMs = (tempoDepois - tempoAntes) / 1_000_000;
        long memoriaKb = (memoriaDepois - memoriaAntes) / 1024;

        System.out.printf("✅ Benchmark concluído: %d OS registradas%n", quantidade);
        System.out.printf("⏱️ Tempo: %d ms%n", tempoMs);
        System.out.printf("📦 Memória usada: %d KB%n", memoriaKb);
        System.out.printf("📦 Peças Flyweight compartilhadas: %d%n", PecaFactory.getTotalInstanciasCompartilhadas());

        try (FileWriter fw = new FileWriter("data/medicoes_ordem.txt", true)) {
            fw.write("Benchmark Ordem de Serviço com Flyweight\n");
            fw.write("OS criadas: " + quantidade + "\n");
            fw.write("Tempo: " + tempoMs + " ms\n");
            fw.write("Memória: " + memoriaKb + " KB\n");
            fw.write("Peças Flyweight: " + PecaFactory.getTotalInstanciasCompartilhadas() + "\n\n");
        } catch (IOException e) {
            System.out.println("❌ Falha ao salvar benchmark: " + e.getMessage());
        }
    }
}
