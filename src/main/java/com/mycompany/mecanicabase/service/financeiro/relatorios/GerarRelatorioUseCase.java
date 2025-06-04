package com.mycompany.mecanicabase.service.financeiro.relatorios;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.mecanicabase.model.financeiro.OrdemDeServico;
import com.mycompany.mecanicabase.model.financeiro.PecaItem;
import com.mycompany.mecanicabase.model.financeiro.ServicoItem;

/**
 * Caso de uso responsável por gerar um relatório detalhado com os serviços
 * realizados e peças vendidas em um intervalo de tempo.
 */
public class GerarRelatorioUseCase {

    /**
     * Gera um relatório de vendas e serviços com base no intervalo de datas
     * fornecido.
     *
     * @param dataInicio Data/hora inicial do intervalo de consulta.
     * @param dataFinal Data/hora final do intervalo de consulta.
     * @return Uma String formatada contendo o relatório detalhado.
     */
    public String use(LocalDateTime dataInicio, LocalDateTime dataFinal) {

        // Lista para armazenar serviços dentro do período especificado
        List<ServicoItem> servicosFeitos = new ArrayList<>();

        // Lista para armazenar peças vendidas no mesmo intervalo
        List<PecaItem> pecasVendidas = new ArrayList<>();

        // Variáveis de controle para somar valores
        float totalServicos = 0;
        float totalPecas = 0;

        // Percorre todas as ordens de serviço registradas
        for (OrdemDeServico os : OrdemDeServico.instances) {

            // Verifica todos os serviços da ordem
            for (ServicoItem si : os.getServicos()) {

                // Se o serviço estiver dentro do período de interesse
                if (!si.getCriadoEm().isBefore(dataInicio) && !si.getCriadoEm().isAfter(dataFinal)) {
                    servicosFeitos.add(si);                          // Adiciona à lista
                    totalServicos += si.getValorUnitario();         // Soma o valor do serviço
                }
            }

            // Verifica todas as peças vendidas da ordem
            for (PecaItem pi : os.getPecas()) {

                // Se a peça tiver sido vendida dentro do período
                if (!pi.getCriadoEm().isBefore(dataInicio) && !pi.getCriadoEm().isAfter(dataFinal)) {
                    pecasVendidas.add(pi);                          // Adiciona à lista
                    totalPecas += pi.getValorUnitario() * pi.getQuantidade(); // Soma valor * quantidade
                }
            }
        }

        // Constrói o relatório textual
        StringBuilder relatorio = new StringBuilder();

        relatorio.append("RELATÓRIO DE VENDAS E SERVIÇOS\n");
        relatorio.append("Período: ").append(dataInicio.toLocalDate())
                .append(" a ").append(dataFinal.toLocalDate()).append("\n\n");

        // Lista de serviços realizados
        relatorio.append("🛠️ Serviços realizados:\n");
        for (ServicoItem si : servicosFeitos) {
            String nomeServico = si.getServico() != null ? si.getServico().getTipo() : "Desconhecido";
            relatorio.append("- ").append(nomeServico)
                    .append(" – R$ ").append(String.format("%.2f", si.getValorUnitario()))
                    .append("\n");
        }

        // Lista de peças vendidas
        relatorio.append("\n🔩 Peças vendidas:\n");
        for (PecaItem pi : pecasVendidas) {
            String nomePeca = pi.getPeca() != null ? pi.getPeca().getNome() : "Desconhecida";
            relatorio.append("- ").append(nomePeca)
                    .append(" x").append(pi.getQuantidade())
                    .append(" – R$ ").append(String.format("%.2f", pi.getValorUnitario() * pi.getQuantidade()))
                    .append("\n");
        }

        // Totais
        relatorio.append("\n💰 Totais:\n");
        relatorio.append("- Total em serviços: R$ ").append(String.format("%.2f", totalServicos)).append("\n");
        relatorio.append("- Total em peças: R$ ").append(String.format("%.2f", totalPecas)).append("\n");
        relatorio.append("- TOTAL GERAL: R$ ").append(String.format("%.2f", totalServicos + totalPecas)).append("\n");

        return relatorio.toString();
    }
}
