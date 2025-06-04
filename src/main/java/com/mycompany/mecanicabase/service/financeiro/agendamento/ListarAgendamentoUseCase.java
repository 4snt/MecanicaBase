package com.mycompany.mecanicabase.service.financeiro.agendamento;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import com.mycompany.mecanicabase.model.financeiro.Agendamento;
import com.mycompany.mecanicabase.model.financeiro.StatusAgendamento;
import com.mycompany.mecanicabase.model.operacao.Veiculo;

/**
 * Caso de uso responsável por listar agendamentos com base em filtros
 * opcionais.
 */
public class ListarAgendamentoUseCase {

    /**
     * Lista agendamentos filtrando por data de início, data final, status,
     * funcionário ou cliente.
     *
     * @param dataInicio Data mínima do agendamento (pode ser nula).
     * @param dataFinal Data máxima do agendamento (pode ser nula).
     * @param status Status do agendamento (pode ser nulo).
     * @param funcionarioId ID do funcionário responsável (pode ser nulo).
     * @param clienteId ID do cliente associado ao veículo (pode ser nulo).
     * @return Lista de agendamentos que satisfazem os critérios fornecidos.
     */
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
