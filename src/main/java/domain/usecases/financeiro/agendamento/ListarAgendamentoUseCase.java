package domain.usecases.financeiro.agendamento;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import domain.entities.financeiro.Agendamento;
import domain.entities.financeiro.StatusAgendamento;
import domain.entities.operacao.Veiculo;

public class ListarAgendamentoUseCase {

    public List<Agendamento> use(
            LocalDateTime dataInicio,
            LocalDateTime dataFinal,
            StatusAgendamento status,
            UUID funcionarioId,
            UUID clienteId
    ) {
        return Agendamento.instances.stream()
                .filter(a -> dataInicio == null || !a.getData().isBefore(dataInicio))
                .filter(a -> dataFinal == null || !a.getData().isAfter(dataFinal))
                .filter(a -> status == null || a.getStatus() == status)
                .filter(a -> funcionarioId == null || (a.getFuncionario() != null && a.getFuncionario().getId().equals(funcionarioId)))
                .filter(a -> {
                    if (clienteId == null) {
                        return true;
                    }
                    Veiculo v = a.getVeiculo();
                    return v != null && v.getCliente() != null && v.getCliente().getId().equals(clienteId);
                })
                .collect(Collectors.toList());
    }
}
