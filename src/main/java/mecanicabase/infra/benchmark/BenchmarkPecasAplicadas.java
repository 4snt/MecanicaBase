package mecanicabase.infra.benchmark;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import mecanicabase.infra.flyweight.PecaFactory;
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

    /**
     * Executa o benchmark medindo a aplicação de peças em ordens de serviço,
     * reutilizando ou não as instâncias de peça conforme o uso do Flyweight. O
     * benchmark utiliza as peças já carregadas no sistema.
     *
     * @param quantidade número de ordens de serviço a serem criadas
     * @param usarFlyweight se true, ativa reutilização de instâncias via
     * PecaFactory
     */
    public static void executarBenchmark(int quantidade, boolean usarFlyweight) {
        System.out.println("\n🔁 Benchmark " + (usarFlyweight ? "COM Flyweight" : "SEM Flyweight"));

        PecaCrud pecaCrud = new PecaCrud();
        pecaCrud.setUsarFlyweight(usarFlyweight);
        ClienteCrud clienteCrud = new ClienteCrud();
        VeiculoCrud veiculoCrud = new VeiculoCrud();
        OrdemDeServicoCrud osCrud = new OrdemDeServicoCrud();
        Random rand = new Random();

        // Usa as peças já existentes no sistema
        List<Peca> pecasBase = pecaCrud.getInstancias();

        if (pecasBase.isEmpty()) {
            System.out.println("⚠️ Nenhuma peça encontrada no sistema. Carregue via CSV antes de rodar o benchmark.");
            return;
        }

        // Reabastece todas as peças com uma quantidade alta
        for (Peca p : pecasBase) {
            p.setQuantidade(quantidade); // garante que haverá peças suficientes
        }

        System.out.printf("📦 Peças disponíveis no sistema: %d\n", pecasBase.size());

        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        long memoriaAntes = runtime.totalMemory() - runtime.freeMemory();
        long tempoAntes = System.nanoTime();

        try {
            for (int i = 0; i < quantidade; i++) {
                String nomeCompleto = nomes.get(rand.nextInt(nomes.size())) + " " + sobrenomes.get(rand.nextInt(sobrenomes.size()));
                Cliente cliente = clienteCrud.criar(true, nomeCompleto, "Rua X", "3199" + i, nomeCompleto + "@ex.com", "000.000.000-00");

                String placa = "XYZ" + String.format("%04d", i);
                String modeloCompleto = marcas.get(rand.nextInt(marcas.size())) + " " + modelos.get(rand.nextInt(modelos.size()));
                veiculoCrud.criar(true, "Prata", 2020, placa, modeloCompleto, cliente.getId());

                OrdemDeServico os = osCrud.criar(true, cliente.getId());

                for (int j = 0; j < rand.nextInt(2) + 1; j++) {
                    Peca peca = pecasBase.get(rand.nextInt(pecasBase.size()));
                    try {
                        osCrud.venderPeca(os.getId(), peca.getId(), 1);
                    } catch (RuntimeException e) {
                        System.out.println("⚠️ Erro ao vender peça: " + e.getMessage());
                    }
                }

                osCrud.atualizar(os.getId().toString(), true, cliente.getId(), StatusOrdemDeServico.ABERTO);
            }
        } catch (Exception e) {
            System.out.println("❌ Erro durante execução do benchmark: " + e.getMessage());
        }

        long tempoDepois = System.nanoTime();
        long memoriaDepois = runtime.totalMemory() - runtime.freeMemory();

        long tempoMs = (tempoDepois - tempoAntes) / 1_000_000;
        long memoriaKb = (memoriaDepois - memoriaAntes) / 1024;

        System.out.printf("✅ Benchmark %s: %d OS criadas\n", usarFlyweight ? "COM Flyweight" : "SEM Flyweight", quantidade);
        System.out.printf("⏱️ Tempo: %d ms\n", tempoMs);
        System.out.printf("📦 Memória: %d KB\n", memoriaKb);
        if (usarFlyweight) {
            System.out.printf("📦 Peças compartilhadas: %d\n", PecaFactory.getTotalInstanciasCompartilhadas());
        }

        try (FileWriter fw = new FileWriter("data/medicoes_aplicadas.txt", true)) {
            fw.write("Benchmark Peças em OS - " + (usarFlyweight ? "COM Flyweight" : "SEM Flyweight") + "\n");
            fw.write("Peças no sistema: " + pecasBase.size() + "\n");
            fw.write("OS criadas: " + quantidade + "\n");
            fw.write("Tempo: " + tempoMs + " ms\n");
            fw.write("Memória: " + memoriaKb + " KB\n");
            if (usarFlyweight) {
                fw.write("Peças compartilhadas: " + PecaFactory.getTotalInstanciasCompartilhadas() + "\n");
            }
            fw.write("\n");
        } catch (IOException e) {
            System.out.println("❌ Erro ao salvar benchmark: " + e.getMessage());
        }

        limparEntidadesCriadas(!usarFlyweight);
    }

    /**
     * Limpa clientes, veículos, ordens de serviço e cache compartilhado. As
     * peças existentes no sistema são preservadas.
     */
    public static void limparEntidadesCriadas(boolean limparCacheFlyweight) {
        Cliente.instances.clear();
        Veiculo.instances.clear();
        OrdemDeServico.instances.clear();
        System.gc();
    }
}
