package mecanicabase.infra.benchmark;

import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import mecanicabase.model.financeiro.OrdemDeServico;
import mecanicabase.model.financeiro.StatusOrdemDeServico;
import mecanicabase.model.operacao.Peca;
import mecanicabase.model.operacao.Veiculo;
import mecanicabase.model.usuarios.Cliente;
import mecanicabase.service.financeiro.OrdemDeServicoCrud;
import mecanicabase.service.operacao.PecaCrud;
import mecanicabase.service.operacao.VeiculoCrud;
import mecanicabase.service.usuarios.ClienteCrud;

public class BenchmarkPecasAplicadas {

    private static final List<String> nomes = Arrays.asList("João", "Maria", "Pedro", "Ana", "Lucas", "Beatriz", "Carlos", "Fernanda");
    private static final List<String> sobrenomes = Arrays.asList("Silva", "Souza", "Oliveira", "Pereira");
    private static final List<String> marcas = Arrays.asList("Volkswagen", "Fiat", "Honda", "Chevrolet");
    private static final List<String> modelos = Arrays.asList("Gol", "Palio", "Civic", "Onix");

    public static void executarTodosBenchmarks() {
        int[] quantidades = {10000}; 
            //50000, 
            //100000};
        for (int qtd : quantidades) {
            executarBenchmarkCorrigido(qtd, false); // sem flyweight
            executarBenchmarkCorrigido(qtd, true);  // com flyweight
        }
    }

    public static void executarBenchmarkCorrigido(int quantidade, boolean usarFlyweight) {
        System.out.println("\n🔁 Benchmark " + (usarFlyweight ? "COM Flyweight" : "SEM Flyweight") + " (" + quantidade + " OS)");

        PecaCrud pecaCrud = new PecaCrud(usarFlyweight); // ✅ construtor novo

        ClienteCrud clienteCrud = new ClienteCrud();
        VeiculoCrud veiculoCrud = new VeiculoCrud();
        OrdemDeServicoCrud osCrud = new OrdemDeServicoCrud();
        Random rand = new Random();

        List<Peca> pecasBase = pecaCrud.getInstancias();
        if (pecasBase.isEmpty()) {
            System.out.println("⚠️ Nenhuma peça encontrada no sistema. Carregue via CSV antes de rodar o benchmark.");
            return;
        }

        for (Peca p : pecasBase) {
            p.setQuantidade(quantidade);
        }

        Runtime runtime = Runtime.getRuntime();

        try {
            System.gc();
            Thread.sleep(100);
        } catch (InterruptedException ignored) {
        }

        long memoriaAntes = runtime.totalMemory() - runtime.freeMemory();
        long tempoAntes = System.nanoTime();

        long tempoPecasNano = 0;
        int totalPecasVendidas = 0;

        try {
            System.gc();
            Thread.sleep(100);
            long memoriaAntesPecas = runtime.totalMemory() - runtime.freeMemory();
            long tempoAntesPecas = System.nanoTime();

            for (int i = 0; i < quantidade; i++) {
                String nomeCompleto = nomes.get(rand.nextInt(nomes.size())) + " " + sobrenomes.get(rand.nextInt(sobrenomes.size()));
                Cliente cliente = clienteCrud.criar(true, nomeCompleto, "Rua X", "3199" + i, nomeCompleto + "@ex.com", "000.000.000-00");

                String placa = "XYZ" + String.format("%04d", i);
                String modeloCompleto = marcas.get(rand.nextInt(marcas.size())) + " " + modelos.get(rand.nextInt(modelos.size()));
                veiculoCrud.criar(true, "Prata", 2020, placa, modeloCompleto, cliente.getId());

                OrdemDeServico os = osCrud.criar(true, cliente.getId());

                for (int j = 0; j < rand.nextInt(2) + 1; j++) {
                    int index = rand.nextInt(pecasBase.size());
                    Peca base = pecasBase.get(index);

                    Peca peca = pecaCrud.criar(false, base.getNome(), base.getValor(), 0);
                    peca.adicionarEstoque(10);

                    long t1 = System.nanoTime();
                    osCrud.venderPeca(os.getId(), peca.getId(), 1);
                    long t2 = System.nanoTime();

                    tempoPecasNano += (t2 - t1);
                    totalPecasVendidas++;
                }

                osCrud.atualizar(os.getId().toString(), true, cliente.getId(), StatusOrdemDeServico.ABERTO);
            }

            System.gc();
            Thread.sleep(100);
            long tempoDepoisPecas = System.nanoTime();
            long memoriaDepoisPecas = runtime.totalMemory() - runtime.freeMemory();

            long tempoDepois = System.nanoTime();
            long memoriaDepois = runtime.totalMemory() - runtime.freeMemory();

            long tempoTotalMs = (tempoDepois - tempoAntes) / 1_000_000;
            long memoriaTotalKb = (memoriaDepois - memoriaAntes) / 1024;

            long tempoPecasMs = (tempoDepoisPecas - tempoAntesPecas) / 1_000_000;
            long memoriaPecasBytes = memoriaDepoisPecas - memoriaAntesPecas;
            long memoriaPecasKb = memoriaPecasBytes / 1024;
            long memoriaPorPecaBytes = totalPecasVendidas > 0 ? memoriaPecasBytes / totalPecasVendidas : 0;

            System.out.printf("✅ Benchmark %s: %d OS criadas\n", usarFlyweight ? "COM Flyweight" : "SEM Flyweight", quantidade);
            System.out.printf("⏱️ Tempo total: %d ms | Tempo só instanciando peças: %d ms\n", tempoTotalMs, tempoPecasMs);
            System.out.printf("📦 Memória total: %d KB | Memória peças: %d KB (%d bytes) | Média por peça: %d bytes\n",
                    memoriaTotalKb, memoriaPecasKb, memoriaPecasBytes, memoriaPorPecaBytes);
            if (usarFlyweight) {
                System.out.printf("📦 Peças compartilhadas: %d\n", pecaCrud.getTotalCompartilhadosFlyweight());
            }

            try (FileWriter fw = new FileWriter("data/medicoes_aplicadas.txt", true)) {
                fw.write("Benchmark Peças em OS - " + (usarFlyweight ? "COM Flyweight" : "SEM Flyweight") + " [" + quantidade + " OS]\n");
                fw.write("Tempo total: " + tempoTotalMs + " ms\n");
                fw.write("Tempo peças: " + tempoPecasMs + " ms\n");
                fw.write("Memória total: " + memoriaTotalKb + " KB\n");
                fw.write("Memória peças: " + memoriaPecasKb + " KB (" + memoriaPecasBytes + " bytes)\n");
                fw.write("Média por peça: " + memoriaPorPecaBytes + " bytes\n");
                if (usarFlyweight) {
                    fw.write("Peças compartilhadas: " + pecaCrud.getTotalCompartilhadosFlyweight() + "\n");
                }
                fw.write("\n");
            }

        } catch (Exception e) {
            System.out.println("❌ Erro durante execução do benchmark: " + e.getMessage());
            e.printStackTrace();
        }

        limparEntidadesCriadas();
    }

    public static void limparEntidadesCriadas() {
        Cliente.instances.clear();
        Veiculo.instances.clear();
        OrdemDeServico.instances.clear();
        System.gc();
    }
}
