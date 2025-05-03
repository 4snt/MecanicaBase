package domain.usecases.financeiro.relatorios;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import domain.entities.financeiro.Agendamento;
import domain.entities.financeiro.Despesa;
import domain.entities.financeiro.OrdemDeServico;
import domain.entities.financeiro.PecaItem;
import domain.entities.financeiro.ServicoItem;
import domain.entities.financeiro.StatusOrdemDeServico;
import domain.entities.operacao.Peca;
import domain.entities.operacao.Servico;
import domain.entities.usuarios.Funcionario;

/**
 * Caso de uso para gerar um balanço financeiro e operacional no sistema com
 * base em um intervalo de datas.
 */
public class GerarBalancoUseCase {

    /**
     * Gera um resumo detalhado do período de operações entre duas datas.
     *
     * @param dataInicio Data de início
     * @param dataFinal Data final
     * @return Relatório formatado em String
     */
    public String use(LocalDateTime dataInicio, LocalDateTime dataFinal) {
        List<OrdemDeServico> osFinalizadas = buscarOSFinalizadas(dataInicio, dataFinal);
        List<OrdemDeServico> osCriadas = buscarOSCriadas(dataInicio, dataFinal);

        int canceladas = contarPorStatus(osFinalizadas, StatusOrdemDeServico.CANCELADO);
        int concluidas = contarPorStatus(osFinalizadas, StatusOrdemDeServico.CONCLUIDO);

        float totalServicos = calcularTotalServicos(osFinalizadas);
        float totalPecas = calcularTotalPecas(osFinalizadas);
        float totalGeral = totalServicos + totalPecas;

        List<String> topServicos = top3Servicos(osFinalizadas);
        List<String> topPecas = top3Pecas(osFinalizadas);
        String topFuncionario = funcionarioMaisAtivo(osFinalizadas);

        int totalFinal = canceladas + concluidas;
        float percCancelado = totalFinal > 0 ? (canceladas * 100f / totalFinal) : 0f;
        float percConcluido = totalFinal > 0 ? (concluidas * 100f / totalFinal) : 0f;

        List<Despesa> despesas = buscarDespesasNoPeriodo(dataInicio, dataFinal);
        float totalDespesas = calcularTotalDespesas(despesas);
        Map<String, Float> gastosPorCategoria = calcularGastosPorCategoria(despesas);

        float lucroLiquido = totalGeral - totalDespesas;

        return String.format(
                "BALANÇO FINANCEIRO E OPERACIONAL\n"
                + "Período: %s a %s\n\n"
                + "Totais:\n"
                + "- Ordens criadas: %d\n"
                + "- Ordens finalizadas: %d\n"
                + "- Ordens concluídas: %d (%.1f%%)\n"
                + "- Ordens canceladas: %d (%.1f%%)\n\n"
                + "Faturamento:\n"
                + "- Total em serviços: R$ %.2f\n"
                + "- Total em peças: R$ %.2f\n"
                + "- Faturamento total: R$ %.2f\n\n"
                + "Top 3 serviços:\n%s\n\n"
                + "Top 3 peças:\n%s\n\n"
                + "Funcionário com mais agendamentos:\n%s\n\n"
                + "Despesas:\n"
                + "- Total de despesas: R$ %.2f\n"
                + "- Lucro líquido: R$ %.2f\n\n"
                + "Gastos por categoria:\n%s",
                dataInicio.toLocalDate(), dataFinal.toLocalDate(),
                osCriadas.size(), osFinalizadas.size(),
                concluidas, percConcluido, canceladas, percCancelado,
                totalServicos, totalPecas, totalGeral,
                String.join("\n", topServicos),
                String.join("\n", topPecas),
                topFuncionario,
                totalDespesas, lucroLiquido,
                formatarGastosPorCategoria(gastosPorCategoria)
        );
    }

    private List<OrdemDeServico> buscarOSFinalizadas(LocalDateTime inicio, LocalDateTime fim) {
        return OrdemDeServico.instances.stream()
                .filter(os -> os.getFinalizadoEm() != null)
                .filter(os -> !os.getFinalizadoEm().isBefore(inicio) && !os.getFinalizadoEm().isAfter(fim))
                .collect(Collectors.toList());
    }

    private List<OrdemDeServico> buscarOSCriadas(LocalDateTime inicio, LocalDateTime fim) {
        return OrdemDeServico.instances.stream()
                .filter(os -> !os.getCriadoEm().isBefore(inicio) && !os.getCriadoEm().isAfter(fim))
                .collect(Collectors.toList());
    }

    private int contarPorStatus(List<OrdemDeServico> lista, StatusOrdemDeServico status) {
        return (int) lista.stream().filter(os -> os.getStatus() == status).count();
    }

    private float calcularTotalServicos(List<OrdemDeServico> lista) {
        return lista.stream()
                .flatMap(os -> os.getServicos().stream())
                .map(ServicoItem::getValorUnitario)
                .reduce(0f, Float::sum);
    }

    private float calcularTotalPecas(List<OrdemDeServico> lista) {
        return lista.stream()
                .flatMap(os -> os.getPecas().stream())
                .map(pi -> pi.getValorUnitario() * pi.getQuantidade())
                .reduce(0f, Float::sum);
    }

    private List<String> top3Servicos(List<OrdemDeServico> lista) {
        Map<UUID, Integer> contagem = new HashMap<>();
        for (OrdemDeServico os : lista) {
            for (ServicoItem si : os.getServicos()) {
                UUID id = si.getServico().getId();
                contagem.put(id, contagem.getOrDefault(id, 0) + 1);
            }
        }
        return contagem.entrySet().stream()
                .sorted(Map.Entry.<UUID, Integer>comparingByValue().reversed())
                .limit(3)
                .map(entry -> {
                    Servico s = Servico.instances.stream()
                            .filter(serv -> serv.getId().equals(entry.getKey()))
                            .findFirst().orElse(null);
                    return s != null ? s.getTipo() + " (" + entry.getValue() + ")" : "Desconhecido";
                }).toList();
    }

    private List<String> top3Pecas(List<OrdemDeServico> lista) {
        Map<UUID, Integer> contagem = new HashMap<>();
        for (OrdemDeServico os : lista) {
            for (PecaItem pi : os.getPecas()) {
                UUID id = pi.getPeca().getId();
                contagem.put(id, contagem.getOrDefault(id, 0) + pi.getQuantidade());
            }
        }
        return contagem.entrySet().stream()
                .sorted(Map.Entry.<UUID, Integer>comparingByValue().reversed())
                .limit(3)
                .map(entry -> {
                    Peca p = Peca.instances.stream()
                            .filter(pc -> pc.getId().equals(entry.getKey()))
                            .findFirst().orElse(null);
                    return p != null ? p.getNome() + " (" + entry.getValue() + ")" : "Desconhecida";
                }).toList();
    }

    private String funcionarioMaisAtivo(List<OrdemDeServico> lista) {
        Map<UUID, Integer> contagem = new HashMap<>();
        for (OrdemDeServico os : lista) {
            for (Agendamento ag : os.getAgendamentos()) {
                if (ag != null && ag.getFuncionario() != null) {
                    UUID id = ag.getFuncionario().getId();
                    contagem.put(id, contagem.getOrDefault(id, 0) + 1);
                }
            }
        }
        return contagem.entrySet().stream()
                .sorted(Map.Entry.<UUID, Integer>comparingByValue().reversed())
                .findFirst()
                .map(entry -> {
                    Funcionario f = Funcionario.instances.stream()
                            .filter(fun -> fun.getId().equals(entry.getKey()))
                            .findFirst().orElse(null);
                    return f != null ? f.getNome() + " (" + entry.getValue() + " agendamentos)" : "Desconhecido";
                }).orElse("Nenhum");
    }

    private List<Despesa> buscarDespesasNoPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return Despesa.instances.stream()
                .filter(d -> !d.getCriadoEm().isBefore(inicio) && !d.getCriadoEm().isAfter(fim))
                .collect(Collectors.toList());
    }

    private float calcularTotalDespesas(List<Despesa> despesas) {
        return despesas.stream()
                .map(Despesa::getValor)
                .reduce(0f, Float::sum);
    }

    private Map<String, Float> calcularGastosPorCategoria(List<Despesa> despesas) {
        Map<String, Float> mapa = new HashMap<>();
        for (Despesa d : despesas) {
            String cat = d.getCategoria() != null ? d.getCategoria().getTitulo() : "Sem categoria";
            mapa.put(cat, mapa.getOrDefault(cat, 0f) + d.getValor());
        }
        return mapa;
    }

    private String formatarGastosPorCategoria(Map<String, Float> mapa) {
        if (mapa.isEmpty()) {
            return "Nenhum gasto no período.";
        }
        return mapa.entrySet().stream()
                .map(e -> "- " + e.getKey() + ": R$ " + String.format("%.2f", e.getValue()))
                .collect(Collectors.joining("\n"));
    }
}
