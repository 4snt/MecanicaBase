package domain.usecases.financeiro.relatorios;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import domain.entities.financeiro.OrdemDeServico;

public class GerarBalançoUseCase {

    public String use(LocalDateTime dataInicio, LocalDateTime dataFinal) {

        List<OrdemDeServico> ordemDeServicosFinalizados = OrdemDeServico.instances.stream()
                .filter(os -> os.getFinalizadoEm().isBefore(dataFinal) && os.getFinalizadoEm().isAfter(dataInicio))
                .collect(Collectors.toList());

        List<OrdemDeServico> ordemDeServicosCriados = OrdemDeServico.instances.stream()
                .filter(os -> os.getCriadoEm().isBefore(dataFinal) && os.getCriadoEm().isAfter(dataInicio))
                .collect(Collectors.toList());

        return "";
    }
}
