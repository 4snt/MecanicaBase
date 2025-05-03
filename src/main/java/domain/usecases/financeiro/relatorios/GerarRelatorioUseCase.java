package domain.usecases.financeiro.relatorios;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import domain.entities.financeiro.OrdemDeServico;
import domain.entities.financeiro.PecaItem;
import domain.entities.financeiro.ServicoItem;

public class GerarRelatorioUseCase {

    public String use(LocalDateTime dataInicio, LocalDateTime dataFinal) {

        List<ServicoItem> servicosFeitos = new ArrayList<>();
        List<PecaItem> pecasVendidas = new ArrayList<>();

        float totalServicos = 0;
        float totalPecas = 0;

        for (OrdemDeServico os : OrdemDeServico.instances) {
            for (ServicoItem si : os.getServicos()) {
                if (!si.getCriadoEm().isBefore(dataInicio) && !si.getCriadoEm().isAfter(dataFinal)) {
                    servicosFeitos.add(si);
                    totalServicos += si.getValorUnitario();
                }
            }

            for (PecaItem pi : os.getPecas()) {
                if (!pi.getCriadoEm().isBefore(dataInicio) && !pi.getCriadoEm().isAfter(dataFinal)) {
                    pecasVendidas.add(pi);
                    totalPecas += pi.getValorUnitario() * pi.getQuantidade();
                }
            }
        }

        StringBuilder relatorio = new StringBuilder();
        relatorio.append("RELATÓRIO DE VENDAS E SERVIÇOS\n");
        relatorio.append("Período: ").append(dataInicio.toLocalDate()).append(" a ").append(dataFinal.toLocalDate()).append("\n\n");

        relatorio.append("🛠️ Serviços realizados:\n");
        for (ServicoItem si : servicosFeitos) {
            String nomeServico = si.getServico() != null ? si.getServico().getTipo() : "Desconhecido";
            relatorio.append("- ").append(nomeServico)
                    .append(" – R$ ").append(String.format("%.2f", si.getValorUnitario()))
                    .append("\n");
        }

        relatorio.append("\n🔩 Peças vendidas:\n");
        for (PecaItem pi : pecasVendidas) {
            String nomePeca = pi.getPeca() != null ? pi.getPeca().getNome() : "Desconhecida";
            relatorio.append("- ").append(nomePeca)
                    .append(" x").append(pi.getQuantidade())
                    .append(" – R$ ").append(String.format("%.2f", pi.getValorUnitario() * pi.getQuantidade()))
                    .append("\n");
        }

        relatorio.append("\n💰 Totais:\n");
        relatorio.append("- Total em serviços: R$ ").append(String.format("%.2f", totalServicos)).append("\n");
        relatorio.append("- Total em peças: R$ ").append(String.format("%.2f", totalPecas)).append("\n");
        relatorio.append("- TOTAL GERAL: R$ ").append(String.format("%.2f", totalServicos + totalPecas)).append("\n");

        return relatorio.toString();
    }
}
